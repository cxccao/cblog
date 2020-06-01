package com.cxc.cblog.config;

import com.cxc.cblog.service.CategoryService;
import com.cxc.cblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by cxc Cotter on 2020/5/16.
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

    private ServletContext servletContext;

    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        servletContext.setAttribute("base", servletContext.getContextPath());
        // 初始化每周热榜
        postService.initWeekRank();
        // 初始化目录
//        List<Category> categories = categoryService.list(new QueryWrapper<Category>().eq("status", 0));
        servletContext.setAttribute("categorys", categoryService.list());
        servletContext.setAttribute("currentCategoryId", 0);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
