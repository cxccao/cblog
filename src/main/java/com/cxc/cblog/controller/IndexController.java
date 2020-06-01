package com.cxc.cblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxc.cblog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cxc Cotter on 2020/4/29.
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    SearchService searchService;

    @RequestMapping({"","/","/index"})
    public String index() {
        IPage results = postService.paging(getPage(), null, null, null, null, "created");
        request.setAttribute("pageData", results);
        request.setAttribute("currentCategoryId", 0);

        return "/index";
    }

    @RequestMapping("/search")
    public String search(String q) {
        IPage pageData = searchService.search(getPage(), q);
        request.setAttribute("q", q);
        request.setAttribute("pageData", pageData);
        return "search";
    }
}
