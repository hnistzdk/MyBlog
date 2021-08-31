package com.zdk.MyBlog.model.dto;

import com.zdk.MyBlog.model.pojo.Metas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 标签、分类列表
 * @author zdk
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MetaDto extends Metas {
    private int count;
}
