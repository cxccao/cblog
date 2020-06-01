package com.cxc.cblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxc.cblog.entity.Post;
import com.cxc.cblog.search.model.PostDocument;
import com.cxc.cblog.search.mq.PostMqIndexMessage;
import com.cxc.cblog.search.repository.PostRepository;
import com.cxc.cblog.service.PostService;
import com.cxc.cblog.service.SearchService;
import com.cxc.cblog.vo.PostVo;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxc Cotter on 2020/5/30.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostService postService;

    @Override
    public IPage search(Page page, String keyword) {
        // 分页信息 mybatis plus的page 转成 jpa的page
        Long current = page.getCurrent() - 1;
        Long size = page.getSize();
        Pageable pageable = PageRequest.of(current.intValue(), size.intValue());

        // 搜索es得到pageData
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "authorName");
        org.springframework.data.domain.Page<PostDocument> documents = postRepository.search(multiMatchQueryBuilder, pageable);

        // 结果信息 jpa的pageData转成mybatis plus的pageData
        IPage pageData = new Page(page.getCurrent(), page.getSize(), documents.getTotalElements());
        pageData.setRecords(documents.getContent());
        return pageData;
    }

    @Override
    public int initEsData(List<PostVo> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        List<PostDocument> documents = new ArrayList<>();
        for (PostVo vo : records) {
            PostDocument postDocument = modelMapper.map(vo, PostDocument.class);
            documents.add(postDocument);
        }
        postRepository.saveAll(documents);
        return documents.size();
    }

    @Override
    public void createOrUpdateIndex(PostMqIndexMessage message) {
        Long postId = message.getPostID();
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id", postId));
        PostDocument document = modelMapper.map(vo, PostDocument.class);
        postRepository.save(document);
    }

    @Override
    public void removeIndex(PostMqIndexMessage message) {
        Long postId = message.getPostID();
        postRepository.deleteById(postId);
    }
}
