package com.zzangho.newsindexer.news.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@Document(indexName = "news", createIndex = false)
public class News {
    @Id
    @Field(type = FieldType.Keyword)
    private String contents_id;

    @Field(type = FieldType.Keyword)
    private String domain;

    @Field(type = FieldType.Keyword)
    private String category_nm;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String contents;

    @Field(type = FieldType.Keyword)
    private String writer;

    @Field(type = FieldType.Keyword)
    private String date;

    @Field(type = FieldType.Keyword)
    private String ampm;

    @Field(type = FieldType.Keyword)
    private String time;

    @Field(type = FieldType.Keyword)
    private String company;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Keyword)
    private String udt_dt;
}
