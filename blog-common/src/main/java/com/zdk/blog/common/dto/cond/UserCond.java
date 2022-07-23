package com.zdk.blog.common.dto.cond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 用户查找条件
 * @author zdk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserCond {
    private String username;
    private String password;
}
