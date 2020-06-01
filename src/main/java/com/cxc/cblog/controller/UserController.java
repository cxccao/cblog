package com.cxc.cblog.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.Comment;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.entity.User;
import com.cxc.cblog.shiro.AccountProfile;
import com.cxc.cblog.vo.UserCollectionVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Controller
public class UserController extends BaseController {
    @GetMapping("/user/index")
    public String index() {
        Page page = getPage();
//        QueryWrapper<UserCollectionVo> queryWrapper1 = new QueryWrapper<UserCollectionVo>().eq("p.user_id", getProfileId()).orderByDesc("p.created");
//        QueryWrapper<Post> queryWrapper1 = new QueryWrapper<Post>().eq("user_id", getProfileId()).orderByDesc("created");
//        QueryWrapper<UserCollectionVo> queryWrapper2 = new QueryWrapper<UserCollectionVo>().eq("u.user_id", getProfileId()).orderByDesc("u.created");
//        IPage posts = postService.page(page, queryWrapper1);
        List<Post> posts = postService.list(new QueryWrapper<Post>().eq("user_id", getProfileId()).orderByDesc("created"));

        QueryWrapper<UserCollectionVo> queryWrapper2 = new QueryWrapper<UserCollectionVo>().eq("u.user_id", getProfileId()).orderByDesc("u.created");

        IPage<UserCollectionVo> collections = userCollectionService.selectPosts(page, queryWrapper2);

        request.setAttribute("posts", posts);
        request.setAttribute("collections",collections);

        return "/user/index";
    }

    @GetMapping("/user/{id:\\d*}")
    public String userHome(@PathVariable(name = "id") Long id) {
        User user = userService.getById(id);
        Assert.notNull(user, "用户不存在");

        List<Post> posts = postService.list(new QueryWrapper<Post>().eq("user_id", id).orderByDesc("created"));
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("user_id", id).orderByDesc("created"));
        request.setAttribute("comments", comments);
        request.setAttribute("user", user);
        request.setAttribute("posts", posts);
        return "/user/home";
    }

    @GetMapping("/user/name/{username}")
    public String userHomeByName(@PathVariable(name = "username") String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Assert.notNull(user, "用户不存在");

        List<Post> posts = postService.list(new QueryWrapper<Post>().eq("user_id", user.getId()).orderByDesc("created"));
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("user_id", user.getId()).orderByDesc("created"));
        request.setAttribute("comments", comments);
        request.setAttribute("user", user);
        request.setAttribute("posts", posts);
        return "/user/home";
    }
    @GetMapping("/user/home")
    public String home() {
        User user = userService.getById(getProfileId());
        List<Post> posts = postService.list(new QueryWrapper<Post>().eq("user_id", getProfileId()).orderByDesc("created"));
        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("user_id", getProfileId()).orderByDesc("created"));
        request.setAttribute("comments", comments);
        request.setAttribute("user", user);
        request.setAttribute("posts", posts);
        return "/user/home";
    }

    @GetMapping("/user/set")
    public String set() {
        User user = userService.getById(getProfileId());
        request.setAttribute("user", user);
        return "/user/set";
    }

    @ResponseBody
    @PostMapping("/user/set")
    public Result doSet(User user) {
        if (StrUtil.isNotBlank(user.getAvatar())) {
            User t = userService.getById(getProfileId());
            t.setAvatar(user.getAvatar());
            userService.updateById(t);

            AccountProfile profile = getProfile();
            profile.setAvatar(user.getAvatar());

            SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

            return Result.success().action("/user/set#avatar");
        }

        if (StrUtil.isBlank(user.getUsername())) {
            return Result.fail("昵称不能为空");
        }

        int count = userService.count(new QueryWrapper<User>().eq("username", getProfile().getUsername()).ne("id", getProfileId()));
        if (count > 0) {
            return Result.fail("昵称已被占用");
        }

        User t = userService.getById(getProfileId());
        t.setEmail(user.getEmail());
        t.setUsername(user.getUsername());
        t.setGender(user.getGender());
        t.setSign(user.getSign());
        userService.updateById(t);

        AccountProfile profile = getProfile();
        profile.setUsername(t.getUsername());
        profile.setSign(t.getSign());
        SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

        return Result.success().action("/user/set#info");
    }

    @ResponseBody
    @PostMapping("/user/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if (!pass.equals(repass)) {
            return Result.fail("两次密码不同");
        }

        User t = userService.getById(getProfileId());
        if (!t.getPassword().equals(SecureUtil.md5(nowpass))) {
            return Result.fail("密码不正确");
        }
        t.setPassword(SecureUtil.md5(pass));
        userService.updateById(t);

        return Result.success().action("/user/set#pass");
    }

    @ResponseBody
    @PostMapping("/user/upload")
    public Result upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        User t = userService.getById(getProfileId());

        if (file.isEmpty()) {
            return Result.fail("上传失败");
        }
        String fileName = file.getOriginalFilename();

        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String filePath = consts.getUploadDir();

        AccountProfile accountProfile = getProfile();
        fileName = "/avatar/avatar_" + accountProfile.getId() + suffixName;

        File dest = new File(filePath + fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            String path = filePath + fileName;
            String url = consts.getUploadUrl() + fileName;
            t.setAvatar(url);
            userService.updateById(t);
            accountProfile.setAvatar(t.getAvatar());
            SecurityUtils.getSubject().getSession().setAttribute("profile", accountProfile);

            return Result.success(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return Result.success();
    }



}
