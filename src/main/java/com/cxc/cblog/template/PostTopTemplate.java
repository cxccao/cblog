package com.cxc.cblog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.common.templates.DirectiveHandler;
import com.cxc.cblog.common.templates.TemplateDirective;
import com.cxc.cblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cxc Cotter on 2020/5/17.
 */
@Component
public class PostTopTemplate extends TemplateDirective {
    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "postsTop";
    }

    @Override
    public void execute(DirectiveHandler directiveHandler) throws Exception {
        Integer pn = directiveHandler.getInteger("pn", 1);
        Integer size = directiveHandler.getInteger("size", 3);

        Page page = new Page(pn, size);
        IPage results = postService.selectTopPosts(page);
        directiveHandler.put(RESULTS, results).render();
    }
}