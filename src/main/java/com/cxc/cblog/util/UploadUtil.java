package com.cxc.cblog.util;

import cn.hutool.core.util.StrUtil;
import com.cxc.cblog.common.lang.Consts;
import com.cxc.cblog.common.lang.Result;
import com.cxc.cblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by cxc Cotter on 2020/5/23.
 */
@Component
public class UploadUtil {
    @Autowired
    Consts consts;

    public final static String TYPE_AVATAR = "avatar";

    public Result upload(String type, MultipartFile file)throws IOException {
        if (StrUtil.isBlank(type) || file.isEmpty()) {
            return Result.fail("上传失败");
        }
        String fileName = file.getOriginalFilename();

        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        String filePath = consts.getUploadDir();

        if ("avatar".equalsIgnoreCase(type)) {
            AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
            fileName = "/avatar/avatar_" + accountProfile.getId() + suffixName;
        } else if ("post".equalsIgnoreCase(type)) {
        }
        File dest = new File(filePath + fileName);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            String path = filePath + fileName;
            String url = consts.getUploadUrl() + fileName;

            return Result.success(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

}
