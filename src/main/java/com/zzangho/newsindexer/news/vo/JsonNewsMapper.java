package com.zzangho.newsindexer.news.vo;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class JsonNewsMapper implements FieldSetMapper<JsonNews> {
    public JsonNews mapFieldSet(FieldSet fieldSet) {
        JsonNews news = new JsonNews();

        news.setContents_id(fieldSet.readString(0));
        news.setDomain(fieldSet.readString(1));
        news.setCategory_nm(fieldSet.readString(2));
        news.setTitle(fieldSet.readString(3));
        news.setContents(fieldSet.readString(4));
        news.setWriter(fieldSet.readString(5));
        news.setDate(fieldSet.readString(6));
        news.setAmpm(fieldSet.readString(7));
        news.setTime(fieldSet.readString(8));
        news.setCompany(fieldSet.readString(9));
        news.setUrl(fieldSet.readString(10));
        news.setUdt_dt(fieldSet.readString(11));

        return news;
    }
}
