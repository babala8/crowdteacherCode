package com.atguigu.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ldy
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO implements Serializable {

    private Integer id;
    private String name;
    private String remark;
    private List<PortalProjectVO> portalProjectVOList;

}
