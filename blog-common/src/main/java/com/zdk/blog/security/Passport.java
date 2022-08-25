package com.zdk.blog.security;

import java.util.List;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 10:57
 */
public class Passport extends BasePassport {
    private String userType;
    private String avatar;
    private String phone;
    private String mobile;
    private String email;
    private String sessionId;
    private Long orgId;
    private String orgName;
    private Long deptId;
    private String deptName;
    private String deptCode;
    private List<String> roleIdList;
    private List<String> roleCodeList;
    private String roleType;
    private List<String> postIdList;
    private List<String> postCodeList;
    private List<String> organIdList;
    private List<String> organCodeList;
}
