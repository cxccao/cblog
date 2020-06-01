package com.cxc.cblog.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.config.RabbitMqConfig;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.search.mq.PostMqIndexMessage;
import com.cxc.cblog.util.ValidationUtil;
import com.cxc.cblog.vo.CommentVo;
import com.cxc.cblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Controller
public class PostController extends BaseController {


    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id) {
        QueryWrapper queryWrapper=new QueryWrapper<Post>().eq(id!=null, "p.id", id);
        PostVo postVo = postService.selectOnePost(queryWrapper);
        Assert.notNull(postVo, "文章不存在");

        postService.putViewCount(postVo);

        IPage<CommentVo> commentPage =commentService.paging(getPage(), postVo.getId(), null, "created");
        request.setAttribute("currentCategoryId", postVo.getCategoryId());
        request.setAttribute("post", postVo);
        request.setAttribute("pageData", commentPage);
        return "/post/detail";
    }

    @GetMapping("/post/edit")
    public String edit() {
        String id = request.getParameter("id");
        if (!StrUtil.isEmpty(id)) {
            Post post = postService.getById(id);
            Assert.notNull(post, "帖子已被删除");
            Assert.isTrue(post.getUserId()==getProfileId(), "无权限编辑此文章");
            request.setAttribute("post", post);
        }
        request.setAttribute("categories", categoryService.list());
        return "/post/edit";
    }

    @ResponseBody
    @PostMapping("/post/submit")
    public Result submit(Post post) {
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(post);
        if (validResult.hasErrors()) {
            return Result.fail(validResult.getErrors());
        }
        if (post.getUserId()==null) {
            post.setUserId(getProfileId());
            post.setCategoryId(post.getCategoryId());
            post.setTitle(post.getTitle());
            post.setContent(post.getContent());
            post.setCreated(new Date());
            post.setModified(new Date());
            post.setCommentCount(0);
            post.setViewCount(0);
            post.setLevel(0);
            post.setRecommend(false);
            post.setVoteDown(0);
            post.setVoteUp(0);

            postService.save(post);
        } else {
            Post tmp = postService.getById(post.getId());
            Assert.isTrue(tmp.getUserId().longValue() == getProfileId().longValue(), "无权限编辑此文章");
            post.setTitle(post.getTitle());
            post.setCategoryId(post.getCategoryId());
            post.setContent(post.getContent());
            postService.updateById(tmp);
        }
        amqpTemplate.convertAndSend(RabbitMqConfig.ES_EXCHANGE, RabbitMqConfig.ES_BIND_KEY,
                new PostMqIndexMessage(post.getId(), PostMqIndexMessage.CREATE_OR_UPDATE));
        return Result.success("发布成功").action("/post/" + post.getId());
    }

    @ResponseBody
    @Transactional
    @PostMapping("/post/delete")
    public Result delete(Long id) {
        Post post = postService.getById(id);
        Assert.notNull(post, "文章不存在");
        Assert.isTrue(post.getUserId().longValue() == getProfile().getId().longValue(), "无权限删除");

        postService.removeById(id);
        userMessageService.removeByMap(MapUtil.of("post_id", id));
        userCollectionService.removeByMap(MapUtil.of("post_id", id));

        amqpTemplate.convertAndSend(RabbitMqConfig.ES_EXCHANGE, RabbitMqConfig.ES_BIND_KEY,
                new PostMqIndexMessage(post.getId(), PostMqIndexMessage.REMOVE));
        return Result.success().action("/user/index");
    }



}
