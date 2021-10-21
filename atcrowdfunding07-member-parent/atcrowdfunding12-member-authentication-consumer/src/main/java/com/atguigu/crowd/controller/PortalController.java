package com.atguigu.crowd.controller;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.PortalTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@Controller
@Slf4j
public class PortalController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Model model) {

        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeVO();
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            return "portal";
        }

        List<PortalTypeVO> portalTypeVOList = resultEntity.getData();
        model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA, portalTypeVOList);
        return "portal";
    }

}
