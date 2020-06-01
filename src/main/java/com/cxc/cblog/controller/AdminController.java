package com.cxc.cblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cxc Cotter on 2020/5/30.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    /**
     *
     * @param id
     * @param rank 0表示取消，1表示操作
     * @param field
     * @return
     */
    @ResponseBody
    @PostMapping("/jie-set")
    public Result jetSet(Long id, Integer rank, String field) {
        Post post = postService.getById(id);
        Assert.notNull(post, "该帖子已被删除");

        if("delete".equals(field)) {
            // 删除
            postService.removeById(id);
            return Result.success();

        } else if("status".equals(field)) {
            // 加精
            post.setRecommend(rank == 1);

        }  else if("stick".equals(field)) {
            // 置顶，注意数量不要超过3
            post.setLevel(rank);
        }
        postService.updateById(post);
        return Result.success();
    }
    @ResponseBody
    @PostMapping("/initEsData")
    public Result initEsData() {
        int size = 10000;
        Page page=new Page();
        page.setSize(size);

        long total = 0;
        for (int i = 1; i < 1000; i++) {
            page.setCurrent(i);
            IPage<PostVo> paging = postService.paging(page, null, null, null, null, null);
            int num = searchService.initEsData(paging.getRecords());
            total += num;

            if (paging.getRecords().size() < size) {
                break;
            }
        }
        return Result.success("es索引初始化成功，共" + total + "条记录");
    }
}
