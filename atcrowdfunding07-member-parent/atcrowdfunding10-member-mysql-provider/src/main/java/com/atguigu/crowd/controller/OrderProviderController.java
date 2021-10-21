package com.atguigu.crowd.controller;

import com.atguigu.crowd.po.AddressPO;
import com.atguigu.crowd.service.OrderService;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.AddressVO;
import com.atguigu.crowd.vo.OrderProjectVO;
import com.atguigu.crowd.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@RestController
@Slf4j
public class OrderProviderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/get/order/project/vo/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId")Integer returnId){

        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId,returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/address/vo/remote")
    public ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId){

        try {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);
            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    @RequestMapping("/save/address/remote")
    public ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){

        try {
            orderService.saveAddress(addressVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("save/order/remote")
    public ResultEntity<String> saveOederRemote(@RequestBody OrderVO orderVO){

        try {
            orderService.saveOrder(orderVO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
