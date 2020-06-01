package com.cxc.cblog.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.entity.UserCollection;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Controller
public class UserCollectionController extends BaseController {
    @ResponseBody
    @PostMapping("/collection/find")
    public Result collectionFind(Long cid) {
        int count = userCollectionService.count(new QueryWrapper<UserCollection>().eq("user_id", getProfileId()).eq("post_id", cid));
        return Result.success(MapUtil.of("collection", count > 0));
    }

    @ResponseBody
    @PostMapping("/collection/add")
    public Result collectionAdd(Long cid) {
        Post post = postService.getById(cid);
        Assert.notNull(post, "文章不存在");

        int count = userCollectionService.count(new QueryWrapper<UserCollection>().eq("user_id", getProfileId()).eq("post_id", cid));
        if (count > 0) {
            return Result.fail("您已经收藏");
        }

        UserCollection userCollection = new UserCollection();
        userCollection.setPostId(cid);
        userCollection.setUserId(getProfileId());
        userCollection.setPostUserId(post.getUserId());
        userCollection.setCreated(new Date());
        userCollection.setModified(new Date());

        userCollectionService.save(userCollection);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/collection/remove")
    public Result collectionRemove(Long cid) {
        Post post = postService.getById(cid);
        Assert.notNull(post, "文章不存在");

        userCollectionService.remove(new QueryWrapper<UserCollection>().eq("user_id", getProfileId()).eq("post_id", cid));
        return Result.success();
    }
}
