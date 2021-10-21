package com.atguigu.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ldy
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVO implements Serializable {

    private Integer projectId;
    private String projectName;
    private String headerPicturePath;
    private Integer money;
    private String deployDate;
    private Integer percentage;
    private Integer supporter;
}
