package com.cxc.cblog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cxc Cotter on 2020/5/30.
 */
@Configuration
public class CommonBean {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
