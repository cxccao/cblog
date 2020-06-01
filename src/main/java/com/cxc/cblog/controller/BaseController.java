package com.cxc.cblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.common.lang.Consts;
import com.cxc.cblog.service.*;
import com.cxc.cblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cxc Cotter on 2020/4/27.
 */
public class BaseController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    Consts consts;

    @Autowired
    UserService userService;

    @Autowired
    SearchService searchService;

    @Lazy
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserCollectionService userCollectionService;

    @Autowired
    UserMessageService userMessageService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    WebsocketService websocketService;

    @Autowired
    AmqpTemplate amqpTemplate;

    public Page getPage() {
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 5);
        Page page = new Page(pn, size);
        return page;
    }

    protected AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }

}
