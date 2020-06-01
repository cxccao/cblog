package com.cxc.cblog.vo;

import com.cxc.cblog.entity.UserCollection;
import lombok.Data;

/**
 * Created by cxc Cotter on 2020/5/23.
 */
@Data
public class UserCollectionVo extends UserCollection {
    private String title;

    private Integer viewCount;

    private Integer commentCount;

}
