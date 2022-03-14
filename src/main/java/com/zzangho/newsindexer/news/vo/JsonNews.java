package com.zzangho.newsindexer.news.vo;

import com.fasterxml.jackson.core.JsonParser;
import com.zzangho.newsindexer.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonNews {
    private String contents_id;
    private String domain;
    private String category_nm;
    private String title;
    private String contents;
    private String writer;
    private String date;
    private String ampm;
    private String time;
    private String company;
    private String url;
    private String udt_dt;

    public JsonNews(String text) {
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonParser.parse(text);
            JSONObject jsonObject = (JSONObject) obj;

            this.contents_id = (String) jsonObject.get(Constants.CONTENTS_ID);
            this.domain = (String) jsonObject.get(Constants.DOMAIN);
            this.category_nm = (String) jsonObject.get(Constants.CATEGORY_NM);
            this.title = (String) jsonObject.get(Constants.TITLE);
            this.contents = (String) jsonObject.get(Constants.CONTENTS);
            this.writer = (String) jsonObject.get(Constants.WRITER);
            this.date = (String) jsonObject.get(Constants.DATE);
            this.ampm = (String) jsonObject.get(Constants.AMPM);
            this.time = (String) jsonObject.get(Constants.TIME);
            this.company = (String) jsonObject.get(Constants.COMPANY);
            this.url = (String) jsonObject.get(Constants.URL);
            this.udt_dt = (String) jsonObject.get(Constants.UDT_DT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
