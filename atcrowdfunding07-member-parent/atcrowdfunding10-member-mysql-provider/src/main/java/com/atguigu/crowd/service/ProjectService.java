package com.atguigu.crowd.service;

import com.atguigu.crowd.vo.DetailProjectVO;
import com.atguigu.crowd.vo.PortalTypeVO;
import com.atguigu.crowd.vo.ProjectVO;

import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
public interface ProjectService {

    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
