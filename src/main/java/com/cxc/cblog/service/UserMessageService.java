package com.cxc.cblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.entity.UserMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
public interface UserMessageService extends IService<UserMessage> {
    IPage paging(Page page, QueryWrapper<UserMessage> wrapper);

    void updateToReaded(List<Long> ids);

}
