package com.zdk.MyBlog.model.auth;

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
 *
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blog_role")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 父级角色ID
     */
    private Integer pid;
}
