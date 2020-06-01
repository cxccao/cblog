package com.cxc.cblog.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.shiro.AccountRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by cxc Cotter on 2020/5/19.
 */
@Configuration
public class ShiroConfig {
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);

        return manager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 不设置这行的话会在使用某些注解时shiro报错
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/user/center");
        // 配置未授权跳转页面
        filterFactoryBean.setUnauthorizedUrl("/error/403");

        filterFactoryBean.setFilters(MapUtil.of("authc", new AuthFilter()));

        Map<String, String> hashMap = new LinkedHashMap<>();

        hashMap.put("/res/**", "anon");

        hashMap.put("/user/home", "authc");
        hashMap.put("/user/set", "authc");
        hashMap.put("/user/upload", "authc");
        hashMap.put("/user/index", "authc");
        hashMap.put("/user/public", "authc");
        hashMap.put("/user/collection", "authc");
        hashMap.put("/user/message", "authc");
        hashMap.put("/msg/remove/", "authc");
        hashMap.put("/message/nums", "authc");

        hashMap.put("/collection/remove", "authc");
        hashMap.put("/collection/find", "authc");
        hashMap.put("/collection/add", "authc");

        hashMap.put("/post/edit", "authc");
        hashMap.put("/post/submit", "authc");
        hashMap.put("/post/delete", "authc");
        hashMap.put("/post/comment", "authc");
        hashMap.put("/post/deleteComment", "authc");

        hashMap.put("/websocket", "anon");
        hashMap.put("/login", "anon");
        filterFactoryBean.setFilterChainDefinitionMap(hashMap);

        return filterFactoryBean;
    }

    class AuthFilter extends UserFilter {
        @Override
        protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;

            String header = httpServletRequest.getHeader("X-Requested-With");
            if (header != null && "XMLHttpRequest".equals(header)) {
                boolean authenticated = SecurityUtils.getSubject().isAuthenticated();
                if (!authenticated) {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(JSONUtil.toJsonStr(Result.fail("请先登录")));
                }
            } else {
                super.redirectToLogin(request, response);
            }
        }
    }
}
