package com.zdk.MyBlog.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zdk
 * @date 2021/7/19 14:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String gender;
    private String trueName;
    private String loginDate;
    private Integer loginTimes;
    private String email;
    private String briefIntroduction;
}
