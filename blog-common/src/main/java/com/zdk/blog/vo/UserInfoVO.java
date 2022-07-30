package com.zdk.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zdk
 * @date 2021/9/1 17:17
 * 更新用户信息Vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfoVO extends BaseVO{
    private Integer id;
    private String nickname;
    private String gender;
    private String trueName;
    private String email;
    private String role;
}
