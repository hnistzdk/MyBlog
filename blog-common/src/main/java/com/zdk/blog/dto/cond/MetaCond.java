package com.zdk.blog.dto.cond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * meta项目查询条件
 * @author zdk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MetaCond {
    private Integer id;
    /**
     * meta Name
     */
    private String name;
    /**
     * 类型
     */
    private String type;
}
