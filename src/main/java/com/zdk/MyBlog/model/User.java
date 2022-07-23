package com.zdk.MyBlog.model;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("blog_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String gender;
    private String trueName;
    @TableField(fill = FieldFill.DEFAULT)
    private String loginDate;
    @TableField(fill = FieldFill.DEFAULT)
    private Integer loginTimes;
    private String email;
    private String briefIntroduction;
    @TableLogic
    private Boolean deleted;
    private String role;
}
