package com.atguigu.crowd.api;

import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@FeignClient(value = "atguigu-crowd-mysql")
public interface MySQLRemoteService {

    @RequestMapping("/get/memberpo/by/loginacct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam(value = "loginacct") String loginacct);

    @RequestMapping("/save/member")
    ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    @RequestMapping("/get/portal/type/project/data/remote")
    ResultEntity<List<PortalTypeVO>> getPortalTypeVO();

    @RequestMapping("/get/project/detail/remote/{projectId}")
    ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId);

    @RequestMapping("/get/order/project/vo/remote")
    ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId")Integer returnId);

    @RequestMapping("/get/address/vo/remote")
    ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("memberId") Integer memberId);

    @RequestMapping("/save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOederRemote(@RequestBody OrderVO orderVO);
}
