package com.cxc.cblog.search.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by cxc Cotter on 2020/5/31.
 */
@Data
@AllArgsConstructor
public class PostMqIndexMessage implements Serializable {
    public final static String CREATE_OR_UPDATE = "create_update";
    public final static String REMOVE = "remove";

    private Long postID;
    private String type;
}
