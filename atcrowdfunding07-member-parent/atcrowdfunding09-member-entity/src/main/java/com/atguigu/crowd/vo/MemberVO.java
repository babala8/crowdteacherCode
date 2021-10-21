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
public class MemberVO implements Serializable {

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phoneNum;

    private String code;
}
