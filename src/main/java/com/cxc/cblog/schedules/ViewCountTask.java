package com.cxc.cblog.schedules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.service.PostService;
import com.cxc.cblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cxc Cotter on 2020/5/29.
 */
@Component
public class ViewCountTask {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PostService postService;

    @Scheduled(cron = "0/5 * * * * *") //每分钟同步
    public void task() {
        Set<String> keys = redisTemplate.keys("rank:post:*");

        List<String> ids = new ArrayList<>();
        for (String key : keys) {
            if (redisUtil.hHasKey(key, "post:viewCount")) {
                ids.add(key.substring("rank:post:".length()));
            }
        }
        if (ids.isEmpty()) {
            return;
        }

        List<Post> posts = postService.list(new QueryWrapper<Post>().in("id", ids));
        posts.forEach(post -> {
            Integer viewCount = (Integer) redisUtil.hget("rank:post:" + post.getId(), "post:viewCount");
            post.setViewCount(viewCount);
        });
        if (posts.isEmpty()) {
            return;
        }
        boolean isSuccess = postService.updateBatchById(posts);

        if (isSuccess) {
            ids.forEach(id->{
                redisUtil.hdel("rank:post:"+id, "post:viewCount");
            });
        }
    }
}
