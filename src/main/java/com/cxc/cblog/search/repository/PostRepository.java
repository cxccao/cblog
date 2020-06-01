package com.cxc.cblog.search.repository;

import com.cxc.cblog.search.model.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cxc Cotter on 2020/5/30.
 */
@Repository
public interface PostRepository extends ElasticsearchRepository<PostDocument, Long> {

}
