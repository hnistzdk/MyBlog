package com.zdk.MyBlog.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 网站配置表
 * </p>
 *
 * @author zdk
 * @since 2021-08-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("blog_options")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableId(value = "name", type = IdType.AUTO)
    private String name;

    /**
     * 内容
     */
    private String value;

    /**
     * 备注
     */
    private String description;


}
