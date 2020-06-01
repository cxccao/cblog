package com.cxc.cblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.vo.PostVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
public interface PostService extends IService<Post> {
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);
    PostVo selectOnePost(QueryWrapper<Post> wrapper);
    IPage selectTopPosts(Page page);
    // 本周热议
    void initWeekRank();

    void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr);

    void putViewCount(PostVo postVo);

}
