package com.cxc.cblog.service;

import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxc.cblog.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountProfile login(String username, String password);
}
