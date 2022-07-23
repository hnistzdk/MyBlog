package com.zdk.blog.common.model.auth;

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
@TableName("blog_permission")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private Integer pid;

    /**
     * 地址
     */
    private String url;

    /**
     * 图标
     */
    private String icons;

    /**
     * 排序
     */
    private Integer sortRank;

    /**
     * 层级
     */
    private Integer permissionLevel;

    /**
     * 权限资源KEY
     */
    private String permissionKey;

    /**
     * 是否是菜单
     */
    private Boolean isMenu;


}
