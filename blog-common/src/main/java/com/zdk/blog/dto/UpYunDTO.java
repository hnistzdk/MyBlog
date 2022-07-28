package com.zdk.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zdk
 * @date 2021/8/24 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UpYunDTO implements Serializable {
    private Integer code;
    private String message;
    private Integer file_size;
    private String url;
    private String mimetype;
    private Integer time;
}
