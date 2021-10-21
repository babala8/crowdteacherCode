package com.atguigu.crowd.service;

import com.atguigu.crowd.po.MemberPO;

/**
 * @author ldy
 * @version 1.0
 */

public interface MemberService {

    MemberPO getMemberPOByLoginAcct(String loginacct);

    void saveMember(MemberPO memberPO);
}
