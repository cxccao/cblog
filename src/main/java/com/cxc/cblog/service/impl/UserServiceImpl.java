package com.cxc.cblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.User;
import com.cxc.cblog.mapper.UserMapper;
import com.cxc.cblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxc.cblog.shiro.AccountProfile;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result register(User user) {
        int count = this.count(new QueryWrapper<User>().eq("email", user.getEmail()).or().eq("username", user.getUsername()));
        if (count > 0) {
            return Result.fail("用户名或邮箱已被注册");
        }

        User t = new User();
        t.setUsername(user.getUsername());
        t.setPassword(SecureUtil.md5(user.getPassword()));
        t.setEmail(user.getEmail());
        t.setAvatar("/res/images/avatar/0.jpg");
        t.setCreated(new Date());
        t.setPoint(0);
        t.setVipLevel(0);
        t.setCommentCount(0);
        t.setPostCount(0);
        t.setGender("0");
        this.save(t);

        return Result.success();
    }

    @Override
    public AccountProfile login(String email, String password) {
        User user = this.getOne(new QueryWrapper<User>().eq("email", email));
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (!user.getPassword().equals(password)) {
            throw new IncorrectCredentialsException();
        }

        user.setLasted(new Date());
        this.updateById(user);

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);

        return profile;
    }
}
