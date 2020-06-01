package com.cxc.cblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.entity.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxc.cblog.vo.UserCollectionVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
public interface UserCollectionService extends IService<UserCollection> {
    IPage<UserCollectionVo> selectPosts(Page page, @Param(Constants.WRAPPER) QueryWrapper wrapper);

}
