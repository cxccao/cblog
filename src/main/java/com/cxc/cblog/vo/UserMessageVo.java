package com.cxc.cblog.vo;

import com.cxc.cblog.entity.UserMessage;
import lombok.Data;

/**
 * Created by cxc Cotter on 2020/5/24.
 */
@Data
public class UserMessageVo extends UserMessage {
    private String toUserName;
    private String fromUserName;
    private String postTitle;
}
