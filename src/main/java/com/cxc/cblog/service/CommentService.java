package com.cxc.cblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxc.cblog.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
public interface CommentService extends IService<Comment> {
    IPage<CommentVo> paging(Page page, Long postId, Long userId, String order);
}
