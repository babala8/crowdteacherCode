package com.atguigu.crowd.controller;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@Controller
@Slf4j
public class ProjectConsumerController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 项目及发起人信息提交
     * @param projectVO
     * @param headerPicture
     * @param detailPictures
     * @param session
     * @return
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictures, HttpSession session){

        boolean empty = headerPicture.isEmpty();
        projectVO.setHeaderPicturePath("headerPicture");
        boolean detailPicturesEmpty = detailPictures.isEmpty();
        List<String> list = new ArrayList<>();
        list.add("picture1");
        list.add("picture2");
        list.add("picture3");
        projectVO.setDetailPicturePathList(list);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        log.info("projectVO="+projectVO);
        return "redirect:http://localhost/project/return/info/page";
    }

    /**
     * 回报信息页面图片上传
     * @param multipartFile
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile multipartFile){

        log.info("returnPicture="+multipartFile.toString());
        return ResultEntity.successWithoutData();
    }

    /**
     * 回报信息页面信息保存
     * @param returnVO
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturnVO(ReturnVO returnVO,HttpSession session){

        log.info(returnVO.toString());
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            if(projectVO == null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            if(returnVOList == null || returnVOList.size() == 0){
                returnVOList = new ArrayList<>();
                projectVO.setReturnVOList(returnVOList);
            }

            returnVOList.add(returnVO);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 确认信息页面提交
     * @param memberConfirmInfoVO
     * @param session
     * @param modelMap
     * @return
     */
    @RequestMapping("/create/confirm")
    public String saveConfirm(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session, ModelMap modelMap){
        log.info(memberConfirmInfoVO.toString());

        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        if(projectVO == null){
            throw new RuntimeException();
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        ResultEntity<String> resultEntity = mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
        if(ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "project-confirm";
        }
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost/project/create/success";
    }

    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer projectId,ModelMap modelMap){

        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            DetailProjectVO detailProjectVO = resultEntity.getData();
            modelMap.addAttribute("detailProjectVO",detailProjectVO);
        }
        return "project-show-detail";
    }
}
