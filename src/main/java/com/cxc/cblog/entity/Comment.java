package com.cxc.cblog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 回复评论的id
     */
    private Long parentId;

    /**
     * 所在文章标题
     */
    private String postTitle;

    /**
     * 所在文章id
     */
    private Long postId;

    /**
     * 评论的用户id
     */
    private Long userId;

    /**
     * 顶的数量
     */
    private Integer voteUp;

    /**
     * 踩的数量
     */
    private Integer voteDown;

    /**
     * 置顶等级
     */
    private Integer level;


}
