package com.zdk.MyBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.pojo.Metas;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:30
 */
@Repository
public interface MetasMapper extends BaseMapper<Metas> {
    /**
     * 获取分类、标签及关联的文章数量
     * @param type
     * @return
     */
    List<MetaDto> getMetaList(String type);
}
