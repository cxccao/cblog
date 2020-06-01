package com.cxc.cblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxc Cotter on 2020/4/27.
 */
@Data
public class BaseEntity implements Serializable {
    private static long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Date created;
    private Date modified;
    private Integer status;
}
