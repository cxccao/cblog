package com.cxc.cblog.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxc Cotter on 2020/5/30.
 */
@Data
@Document(indexName = "post", type = "post")
public class PostDocument implements Serializable {
    @Id
    private Long id;

    @Field(type = FieldType.Text, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Long)
    private Long authorId;

    @Field(type = FieldType.Keyword)
    private String authorName;

    private String authorAvatar;

    private Long categoryId;

    private String categoryName;

    private Integer level;

    private boolean recommend;

    private Integer viewCount;

    private Integer commentCount;

    @Field(type = FieldType.Date)
    private Date created;


}
