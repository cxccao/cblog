package com.cxc.cblog.shiro;

import com.cxc.cblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by cxc Cotter on 2020/5/19.
 */
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile accountProfile = (AccountProfile) principalCollection.getPrimaryPrincipal();
        // 给id为1的用户赋予管理员权限
        if (accountProfile.getId() == 1) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        AccountProfile accountProfile = userService.login(token.getUsername(), String.valueOf(token.getPassword()));
        SecurityUtils.getSubject().getSession().setAttribute("profile", accountProfile);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(accountProfile, authenticationToken.getCredentials(), getName());
        return info;
    }
}
