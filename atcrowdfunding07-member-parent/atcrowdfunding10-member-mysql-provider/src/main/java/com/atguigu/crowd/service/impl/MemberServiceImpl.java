package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.po.MemberPO;
import com.atguigu.crowd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ldy
 * @version 1.0
 */
@Service
// 声明式事务注解，查询方式  readOnly = true
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {

        return memberPOMapper.selectMemberPOByLoginAcct(loginacct);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {

        memberPOMapper.insertSelective(memberPO);
    }
}
