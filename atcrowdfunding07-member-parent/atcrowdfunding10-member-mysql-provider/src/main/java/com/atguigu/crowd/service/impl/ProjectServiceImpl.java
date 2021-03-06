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
        projectPO.setDeploydate(createDate);
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
                detailProjectVO.setStatusText("?????????");
                break;
            case 1:
                detailProjectVO.setStatusText("?????????");
                break;
            case 2:
                detailProjectVO.setStatusText("????????????");
                break;
            case 3:
                detailProjectVO.setStatusText("?????????");
                break;
            default:
                break;
        }
        // 3.?????? deployeDate ?????? lastDay
        // 2020-10-15
        String deployDate = detailProjectVO.getDeployDate();
        // ??????????????????
        Date currentDay = new Date();
        // ???????????????????????? Date ??????
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date deployDay = format.parse(deployDate);

            // ????????????????????????????????????
            long currentTimeStamp = currentDay.getTime();
            // ??????????????????????????????
            long deployTimeStamp = deployDay.getTime();
            // ??????????????????????????????????????????????????????
            long pastDays = (currentTimeStamp - deployTimeStamp) / 1000 / 60 / 60 / 24;
            // ????????????????????????
            Integer totalDays = detailProjectVO.getDay();
            // ?????????????????????????????????????????????????????????????????????
            Integer lastDay = (int) (totalDays - pastDays);
            detailProjectVO.setLastDay(lastDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return detailProjectVO;
    }
}
