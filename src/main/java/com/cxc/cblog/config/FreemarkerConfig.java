package com.cxc.cblog.config;

import com.cxc.cblog.template.HotsTemplate;
import com.cxc.cblog.template.PostTemplate;
import com.cxc.cblog.template.PostTopTemplate;
import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by cxc Cotter on 2020/5/16.
 */
@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    PostTemplate postTemplate;

    @Autowired
    PostTopTemplate postTopTemplate;

    @Autowired
    HotsTemplate hotsTemplate;

    @PostConstruct
    public void setUp() {
        configuration.setSharedVariable("posts", postTemplate);
        configuration.setSharedVariable("postsTop", postTopTemplate );
        configuration.setSharedVariable("hots", hotsTemplate);

        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
