package com.zzangho.newsindexer.news.item;

import com.zzangho.newsindexer.common.Constants;
import com.zzangho.newsindexer.news.vo.JsonNews;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.batch.item.ItemWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@AllArgsConstructor
@Slf4j
public class ElasticSearchWriter implements ItemWriter<JsonNews> {

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void write(List<? extends JsonNews> items) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();

        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (JsonNews news : items) {
            IndexRequest indexRequest = new IndexRequest()
                    .index("news_" + now.format(dateTimeFormatter))
                    .id(news.getContents_id())
                    .source(XContentFactory.jsonBuilder()
                            .startObject()
                            .field(Constants.CONTENTS_ID, news.getContents_id())
                            .field(Constants.DOMAIN, news.getDomain())
                            .field(Constants.CATEGORY_NM, news.getCategory_nm())
                            .field(Constants.TITLE, news.getTitle())
                            .field(Constants.CONTENTS, news.getContents())
                            .field(Constants.WRITER, news.getWriter())
                            .field(Constants.DATE, news.getDate())
                            .field(Constants.TIME, news.getTime())
                            .field(Constants.AMPM, news.getAmpm())
                            .field(Constants.COMPANY, news.getCompany())
                            .field(Constants.URL, news.getUrl())
                            .field(Constants.UDT_DT, news.getUdt_dt())
                            .endObject());

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        if (bulkResponse.hasFailures()) {
            log.info("Full Indexing Fail! - " + bulkResponse.buildFailureMessage());
        } else {
            log.info("Full Indexing Success! - " + bulkResponse.getItems().toString());
        }
    }
}
