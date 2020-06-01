package com.cxc.cblog.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.common.templates.DirectiveHandler;
import com.cxc.cblog.common.templates.TemplateDirective;
import com.cxc.cblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cxc Cotter on 2020/5/15.
 */
@Component
public class PostTemplate extends TemplateDirective {
    @Autowired
    PostService postService;

    @Override
    public String getName() {
        return "posts";
    }

    @Override
    public void execute(DirectiveHandler directiveHandler) throws Exception {
        Long categoryId = directiveHandler.getLong("categoryId");
        Integer pn = directiveHandler.getInteger("pn", 1);
        Integer size = directiveHandler.getInteger("size", 5);
        String order = directiveHandler.getString("order", "created");
        Integer level = directiveHandler.getInteger("level");

        Page page = new Page(pn, size);
        IPage results = postService.paging(page, categoryId, null, level, null, order);
        directiveHandler.put(RESULTS, results).render();
    }
}
