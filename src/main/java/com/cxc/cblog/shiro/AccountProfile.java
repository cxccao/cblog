package com.cxc.cblog.shiro;

import lombok.Data;

/**
 * Created by cxc Cotter on 2020/5/19.
 */
@Data
public class AccountProfile {
    private Long id;
    private String username;
    private String password;
    private String sign;
    private String avatar;
    private String gender;
    private String created;
    private String vipLevel;

}
