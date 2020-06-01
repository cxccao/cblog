package com.cxc.cblog.common.lang;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cxc Cotter on 2020/5/23.
 */
@Data
@Component
public class Consts {
    @Value("${file.upload.dir}")
    private String uploadDir;

    @Value("${file.upload.url}")
    private String uploadUrl;
}
