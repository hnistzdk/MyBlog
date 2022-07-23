package com.zdk.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 附件
 * @author zdk
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AttAchDTO extends BaseDTO {

    /** 主键编号 */
    private Integer id;
    /** 文件名称 */
    private String fname;
    /** 文件类型 */
    private String ftype;
    /** 文件的地址 */
    private String fkey;
    /** 创建人的id */
    private Integer authorId;
    /** 创建的时间戳 */
    private Integer created;
}
