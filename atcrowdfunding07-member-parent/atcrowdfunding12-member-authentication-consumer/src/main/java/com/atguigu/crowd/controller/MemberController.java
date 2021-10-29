package com.atguigu.crowd.controller;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.api.RedisRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.MemberLoginVO;
import com.atguigu.crowd.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ldy
 * @version 1.0
 */
@Controller
@Slf4j
public class MemberController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @Autowired
    private RedisRemoteService redisRemoteService;

    /**
     * 获取验证码，并存入到redis中
     * @param phoneNum
     * @return
     */
    @ResponseBody
    @RequestMapping("auth/member/send/short/message.json")
    public ResultEntity<String> sengMessage(@RequestParam("phoneNum") String phoneNum){

        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        //String code = UUID.randomUUID().toString().replace("-", "");

        String code = "123456";

        log.info(code);

        ResultEntity<String> resultEntity = redisRemoteService.setRedisKeyValueWithTimeoutRemote(key, code, 15, TimeUnit.MINUTES);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            return ResultEntity.successWithoutData();
        }

        return resultEntity;
    }

    /**
     * 注册并删除redis中的验证码
     * @param memberVO
     * @param modelMap
     * @return
     */
    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap){

        String phoneNum = memberVO.getPhoneNum();

        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        ResultEntity<String> resultEntity = redisRemoteService.getStringValueByKeyRemote(key);

        if(ResultEntity.FAILED.equals(resultEntity)){

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());

            return "member-reg";
        }

        String redisCode = resultEntity.getData();

        if(redisCode == null){

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);

            return "member-reg";
        }

        String code = memberVO.getCode();

        if(!redisCode.equals(code)){

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALIED);

            return "member-reg";
        }
        // 删除验证码
        redisRemoteService.removeRedisKeyRemote(key);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String userpswdBeforeEncode = memberVO.getUserpswd();

        String userpswdAfterEncode = passwordEncoder.encode(userpswdBeforeEncode);

        memberVO.setUserpswd(userpswdAfterEncode);

        MemberPO memberPO = new MemberPO();

        BeanUtils.copyProperties(memberVO, memberPO);

        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);

        if(ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())){

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveMemberResultEntity.getMessage());

            return "member-reg";
        }

        return "redirect:http://localhost/auth/member/to/login/page.html";
    }

    /**
     * 用户登录并将用户对象放到session域中
     * @param loginacct
     * @param userpswd
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("auth/member/do/login/page.html")
    public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd") String userpswd, ModelMap modelMap, HttpSession session){

        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if(ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());

            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if(memberPO == null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        String userpswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 因为是随机盐值，所以调用他的方法比较，否则加密出来的不一定相等
        boolean result = passwordEncoder.matches(userpswd, userpswdDataBase);
        if(!result){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);

        return "redirect:http://localhost/auth/member/to/center/page.html";

    }

    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost";
    }

}
