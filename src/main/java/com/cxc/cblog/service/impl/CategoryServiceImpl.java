package com.cxc.cblog.service.impl;

import com.cxc.cblog.entity.Category;
import com.cxc.cblog.mapper.CategoryMapper;
import com.cxc.cblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
