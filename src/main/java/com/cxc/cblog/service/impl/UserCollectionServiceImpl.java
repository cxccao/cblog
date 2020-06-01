package com.cxc.cblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxc.cblog.entity.UserCollection;
import com.cxc.cblog.mapper.UserCollectionMapper;
import com.cxc.cblog.service.UserCollectionService;
import com.cxc.cblog.vo.UserCollectionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements UserCollectionService {
    @Autowired
    UserCollectionMapper userCollectionMapper;
    @Override
    public IPage<UserCollectionVo> selectPosts(Page page, QueryWrapper wrapper) {
        return userCollectionMapper.selectPosts(page, wrapper);
    }
}
