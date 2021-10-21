package com.atguigu.crowd.controller;

import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.MemberLoginVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@Controller
public class OrderController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId")Integer projectId, @PathVariable("returnId")Integer returnId, HttpSession session){

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVORemote(projectId,returnId);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO orderProjectVO = resultEntity.getData();
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_ORDER_PROJECT,orderProjectVO);
        }

        return "confirm_return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,HttpSession session){

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_ORDER_PROJECT);
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_ORDER_PROJECT, orderProjectVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLoginVO.getId();
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberId);

        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            List<AddressVO> addressVOList = resultEntity.getData();
            session.setAttribute("addressVOList", addressVOList);
        }
        return "confirm_order";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session){

        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_ORDER_PROJECT);
        Integer returnCount = orderProjectVO.getReturnCount();


        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }
}
