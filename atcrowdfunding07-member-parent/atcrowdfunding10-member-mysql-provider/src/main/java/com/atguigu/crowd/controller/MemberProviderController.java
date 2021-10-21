package com.atguigu.crowd.controller;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.service.MemberService;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ldy
 * @version 1.0
 */
@Slf4j
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberpo/by/loginacct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam(value = "loginacct") String loginacct){

        MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);

        if(memberPO == null){
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_NOT_REGIST);
        }

        return ResultEntity.successWithData(memberPO);
    }

    @RequestMapping("/save/member")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){

        try {

            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();

        } catch (Exception e) {

            if(e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }

            return  ResultEntity.failed(e.getMessage());
        }

    }
}
