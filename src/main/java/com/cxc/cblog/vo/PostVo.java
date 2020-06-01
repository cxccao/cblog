package com.cxc.cblog.vo;

import com.cxc.cblog.entity.Post;
import lombok.Data;

/**
 * Created by cxc Cotter on 2020/5/16.
 */
@Data
public class PostVo extends Post {
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private int authorVip;
    private String categoryName;
}
