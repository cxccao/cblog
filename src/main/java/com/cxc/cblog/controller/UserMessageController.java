package com.cxc.cblog.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.UserMessage;
import com.cxc.cblog.vo.UserMessageVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Controller
public class UserMessageController extends BaseController {
    @GetMapping("/user/message")
    public String message() {
        IPage<UserMessageVo> page = userMessageService.paging(getPage(), new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .orderByDesc("created"));
        List<Long> ids = new ArrayList<>();
        for (UserMessageVo messageVo : page.getRecords()) {
            if (messageVo.getStatus() != null && messageVo.getStatus() == 0) {
                ids.add(messageVo.getId());
            }
        }
        userMessageService.updateToReaded(ids);
        request.setAttribute("pageData", page);

        return "/user/message";
    }

    @ResponseBody
    @PostMapping("/user/message/remove")
    public Result mesRemove(Long id,
                            @RequestParam(defaultValue = "false") Boolean all) {
        int count = userMessageService.count(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id));
        if (count == 0) {
            return Result.fail("没有消息可以删除");
        }
        boolean remove = userMessageService.remove(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id));
        return remove ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @ResponseBody
    @RequestMapping("/message/nums")
    public Map msgNum() {
        int count = userMessageService.count(new QueryWrapper<UserMessage>().eq("to_user_id", getProfileId()).eq("status", 0));
        return MapUtil.builder("status", 0).put("count", count).build();
    }
}
