package com.cxc.cblog.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.Comment;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.entity.User;
import com.cxc.cblog.entity.UserMessage;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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
public class CommentController extends BaseController {
    @ResponseBody
    @Transactional
    @PostMapping("/post/comment")
    public Result comment(Long id, String content) {
        Assert.notNull(id, "找不到对应文章");
        Assert.hasLength(content, "评论内容不能为空");
        Post post = postService.getById(id);
        Assert.isTrue(post != null, "文章不存在");
        Comment comment = new Comment();
        comment.setPostTitle(post.getTitle());
        comment.setPostId(id);
        comment.setContent(content);
        comment.setUserId(getProfileId());
        comment.setCreated(new Date());
        comment.setModified(new Date());
        comment.setVoteDown(0);
        comment.setVoteUp(0);
        comment.setLevel(0);
        commentService.save(comment);

        // 评论+1
        post.setCommentCount(post.getCommentCount() + 1);
        postService.updateById(post);

        postService.incrCommentCountAndUnionForWeekRank(post.getId(), true);

        // 通知作者
        if (comment.getUserId().longValue() != post.getUserId().longValue()) {
            UserMessage userMessage = new UserMessage();
            userMessage.setPostId(id);
            userMessage.setCommentId(comment.getId());
            userMessage.setFromUserId(getProfileId());
            userMessage.setToUserId(post.getUserId());
            userMessage.setType(1);
            userMessage.setContent(content);
            userMessage.setCreated(new Date());
            userMessage.setModified(new Date());
            userMessage.setStatus(0);
            userMessageService.save(userMessage);

            // websocket通知
            websocketService.sendMessageCountToUser(userMessage.getToUserId());

            if (content.startsWith("@")) {
                String username = content.substring(1, content.indexOf(" "));

                User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
                if (user != null) {
                    UserMessage message = new UserMessage();
                    message.setPostId(id);
                    message.setCommentId(comment.getId());
                    message.setFromUserId(getProfileId());
                    message.setToUserId(user.getId());
                    message.setType(2);
                    message.setContent(content);
                    message.setCreated(new Date());
                    message.setModified(new Date());
                    message.setStatus(0);
                    userMessageService.save(message);
                    // 通知被@的用户
                    websocketService.sendMessageCountToUser(message.getToUserId());
                }
            }
        }
        return Result.success().action("/post/" + post.getId());
    }

    @ResponseBody
    @Transactional
    @PostMapping("/post/deleteComment")
    public Result deleteComment(Long id) {
        Assert.notNull(id, "评论id不能为空");
        Comment comment = commentService.getById(id);
        Assert.notNull(comment, "找不到评论内容");
        if (comment.getUserId().longValue() != getProfileId().longValue()) {
            return Result.fail("不是你发表的评论");
        }
        Post post = postService.getById(comment.getPostId());
        post.setCommentCount(post.getCommentCount() - 1);

        postService.saveOrUpdate(post);
        userMessageService.removeByMap(MapUtil.of("comment_id", id));

        commentService.removeById(id);

        postService.incrCommentCountAndUnionForWeekRank(comment.getPostId(), false);

        return Result.success("删除评论成功");
    }
}
