package com.cxc.cblog.vo;

import com.cxc.cblog.entity.Comment;
import lombok.Data;

/**
 * Created by cxc Cotter on 2020/5/18.
 */
@Data
public class CommentVo extends Comment {
    private Long authorId;
    private String authorName;
    private String authorAvatar;
    private int authorVip;
}
