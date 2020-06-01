package com.cxc.cblog.template;

import com.cxc.cblog.common.templates.DirectiveHandler;
import com.cxc.cblog.common.templates.TemplateDirective;
import com.cxc.cblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by cxc Cotter on 2020/5/16.
 */
@Component
public class HotsTemplate extends TemplateDirective {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler directiveHandler) throws Exception {
        String weekRankKey = "week:rank";

        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(weekRankKey, 0, 6);

        List<Map> hotPosts = new ArrayList<>();

        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            Map<String, Object> map = new HashMap<>();
            Object value = typedTuple.getValue();
            String postKey="rank:post:" + value;

            if (typedTuple.getScore() > 0) {
                map.put("id", value);
                map.put("title", redisUtil.hget(postKey, "post:title"));
                map.put("commentCount", typedTuple.getScore());
                hotPosts.add(map);
            }
        }
        directiveHandler.put(RESULTS, hotPosts).render();
    }
}
