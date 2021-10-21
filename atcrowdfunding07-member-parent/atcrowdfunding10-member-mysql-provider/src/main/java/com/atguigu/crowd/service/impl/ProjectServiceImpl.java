package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.mapper.*;
import com.atguigu.crowd.po.MemberConfirmInfoPO;
import com.atguigu.crowd.po.MemberLaunchInfoPO;
import com.atguigu.crowd.po.ProjectPO;
import com.atguigu.crowd.po.ReturnPO;
import com.atguigu.crowd.service.ProjectService;
import com.atguigu.crowd.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {

        ProjectPO projectPO = new ProjectPO();

        BeanUtils.copyProperties(projectVO, projectPO);
        projectPO.setMemberid(memberId);
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createDate);
        projectPO.setStatus(0);
        projectPOMapper.insertSelective(projectPO);

        Integer projectId = projectPO.getId();

        List<Integer> typeIdList = projectVO.getTypeIdList();

        projectPOMapper.insertRelationship(typeIdList, projectId);

        List<Integer> tagIdList = projectVO.getTagIdList();

        projectPOMapper.insertTagRelationship(tagIdList, projectId);

        List<String> picturePathList = projectVO.getDetailPicturePathList();

        projectItemPicPOMapper.insertPathList(projectId, picturePathList);

        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();

        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);

        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();

        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();

        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);

        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

        List<ReturnPO> returnPOList = new ArrayList<>();

        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertReturnPOBatch(returnPOList, projectId);


    }

    @Override
    public List<PortalTypeVO> getPortalTypeVO() {

        return projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {

        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);

        Integer status = detailProjectVO.getStatus();

        switch (status) {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }
        // 3.根据 deployeDate 计算 lastDay
        // 2020-10-15
        String deployDate = detailProjectVO.getDeployDate();
        // 获取当前日期
        Date currentDay = new Date();
        // 把众筹日期解析成 Date 类型
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date deployDay = format.parse(deployDate);

            // 获取当前当前日期的时间戳
            long currentTimeStamp = currentDay.getTime();
            // 获取众筹日期的时间戳
            long deployTimeStamp = deployDay.getTime();
            // 两个时间戳相减计算当前已经过去的时间
            long pastDays = (currentTimeStamp - deployTimeStamp) / 1000 / 60 / 60 / 24;
            // 获取总的众筹天数
            Integer totalDays = detailProjectVO.getDay();
            // 使用总的众筹天数减去已经过去的天数得到剩余天数
            Integer lastDay = (int) (totalDays - pastDays);
            detailProjectVO.setLastDay(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return detailProjectVO;
    }
}
