package com.cxc.cblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.entity.UserCollection;
import com.cxc.cblog.vo.UserCollectionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxc
 * @since 2020-04-28
 */
@Component
public interface UserCollectionMapper extends BaseMapper<UserCollection> {
    IPage<UserCollectionVo> selectPosts(Page page, @Param(Constants.WRAPPER) QueryWrapper wrapper);
}
