package com.zdk.blog.constant;

/**
 * @author zdk
 * @date 2021/10/30 19:26
 */
public interface SuccessConstant {
    interface Common {
        String SUCCESS = "操作成功";
    }

    interface Article {
        String UPDATE_ARTICLE_SUCCESS = "更新文章成功";
        String ADD_NEW_ARTICLE_SUCCESS = "添加文章成功";
        String DELETE_ARTICLE_SUCCESS = "删除文章成功";
    }

    interface Att {
        String ADD_NEW_ATT_SUCCESS = "添加附件信息成功";
        String UPDATE_ATT_SUCCESS =  "更新附件信息成功";
        String DELETE_ATT_SUCCESS = "删除附件信息成功";
        String UPLOAD_FILE_SUCCESS = "上传附件成功";
    }

    interface Comment {
        String ADD_NEW_COMMENT_SUCCESS = "添加评论成功";
        String UPDATE_COMMENT_SUCCESS = "更新评论成功";
        String DELETE_COMMENT_SUCCESS = "删除评论成功";
    }

    interface Option {
        static final String DELETE_OPTION_SUCCESS = "删除配置成功";
        static final String UPDATE_OPTION_SUCCESS = "更新配置成功";
    }

    interface Meta {
        static final String ADD_META_SUCCESS = "添加项目信息成功";
        static final String UPDATE_META_SUCCESS = "更新项目信息成功";
        static final String DELETE_META_SUCCESS = "删除项目信息成功";
    }
}
