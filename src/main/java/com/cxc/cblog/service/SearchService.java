package com.cxc.cblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.search.mq.PostMqIndexMessage;
import com.cxc.cblog.vo.PostVo;

import java.util.List;


/**
 * Created by cxc Cotter on 2020/5/30.
 */
public interface SearchService {
    IPage search(Page page, String keyword);

    int initEsData(List<PostVo> records);

    void createOrUpdateIndex(PostMqIndexMessage message);

    void removeIndex(PostMqIndexMessage message);
}
