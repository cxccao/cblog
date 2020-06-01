package com.cxc.cblog.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.mapper.PostMapper;
import com.cxc.cblog.service.PostService;
import com.cxc.cblog.util.RedisUtil;
import com.cxc.cblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public IPage<PostVo> paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order) {
        if (level == null) {
            level = -1;
        }
        QueryWrapper wrapper = new QueryWrapper<Post>()
                .eq(categoryId != null, "category_id", categoryId)
                .eq(userId!=null, "user_id", userId)
                .eq(level > 0, "level", level)
                .eq(level == 0, "level", 0)
                .eq(recommend != null, "recommend", recommend)
                .orderByDesc(order!=null, order);
        return postMapper.selectPosts(page, wrapper);
    }


    @Override
    public IPage<PostVo> selectTopPosts(Page page) {
        QueryWrapper wrapper = new QueryWrapper<Post>()
                .gt("level", 0);
        return postMapper.selectTopPosts(page, wrapper);
    }

    @Override
    public PostVo selectOnePost(QueryWrapper<Post> wrapper) {
        return postMapper.selectOnePost(wrapper);
    }

    // 本周热议
    @Override
    public void initWeekRank() {
        // 获取7天内发表的文章
        List<Post> posts = this.list(new QueryWrapper<Post>()
                .ge("created", DateUtil.offsetDay(new Date(), -7))
                .gt("comment_count", 0)
                .select("id, title, user_id, comment_count, view_count, created")
        );
        // 初始化文章总评论量
        for (Post post : posts) {
            String key = "day:rank:" + DateUtil.format(post.getCreated(), DatePattern.PURE_DATE_FORMAT);

            redisUtil.zSet(key, post.getId(), post.getCommentCount());

            // 7天后自动过期
            long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
            // 有效时间
            long expireTime = (7 - between) * 24 * 60 * 60;

            redisUtil.expire(key, expireTime);

            this.hashCachePostIdAndTitle(post, expireTime);
        }
        this.zUnionAndStoreLast7DayForWeekRank();
    }

    // 缓存文章基本信息(hash结构)
    private void hashCachePostIdAndTitle(Post post, long expireTime) {
        String key = "rank:post:" + post.getId();
        boolean hasKey = redisUtil.hasKey(key);

        if (!hasKey) {
            redisUtil.hset(key, "post:id", post.getId(), expireTime);
            redisUtil.hset(key, "post:title", post.getTitle(), expireTime);
            redisUtil.hset(key, "post:commentCount", post.getCommentCount(), expireTime);
            redisUtil.hset(key, "post:viewCount", post.getViewCount(), expireTime);
        }
    }

    // 本周合并每日评论数量操作
    private void zUnionAndStoreLast7DayForWeekRank() {
        String currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
        boolean hasKey = redisUtil.hasKey(currentKey);

        String destKey = "week:rank";
        List<String> otherKeys = new ArrayList<>();
        for (int i = -6; i < 0; i++) {
            String tmp = "day:rank:" + DateUtil.format(DateUtil.offsetDay(new Date(), i), DatePattern.PURE_DATE_FORMAT);
            otherKeys.add(tmp);
        }
        redisUtil.zUnionAndStore(currentKey, otherKeys, destKey);
    }

    // 本周热评数量+1
    @Override
    public void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr) {
        String currentKey = "day:rank:" + DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);


        redisUtil.zIncrementScore(currentKey, postId, isIncr ? 1 : -1);
        if (redisUtil.getZsetScore(currentKey, postId)<0) {
            redisUtil.zIncrementScore(currentKey, postId, 1);
        }

        Post post = this.getById(postId);
        // 7天后自动过期
        long between = DateUtil.between(new Date(), post.getCreated(), DateUnit.DAY);
        // 有效时间
        long expireTime = (7 - between) * 24 * 60 * 60;

        this.hashCachePostIdAndTitle(post, expireTime);
        this.zUnionAndStoreLast7DayForWeekRank();
    }

    // 文章访问数量+1
    @Override
    public void putViewCount(PostVo postVo) {
        String key = "rank:post:" + postVo.getId();
        boolean hasKey = redisUtil.hasKey(key);

        if (!hasKey) {
            redisUtil.hset(key, "post:id", postVo.getId());
            redisUtil.hset(key, "post:title", postVo.getTitle());
            redisUtil.hset(key, "post:viewCount", postVo.getViewCount());
        }

        Integer viewCount = (Integer) redisUtil.hget(key, "post:viewCount");

        if (viewCount != null) {
            postVo.setViewCount(viewCount + 1);
        } else {
            postVo.setViewCount(postVo.getViewCount() + 1);
        }
        redisUtil.hset(key, "post:viewCount", postVo.getViewCount());
        }
}
