package com.zzangho.newsindexer.news.item;

import lombok.AllArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class CreateIndexTasklet implements Tasklet {

    private final RestHighLevelClient client;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        CreateIndexRequest request = new CreateIndexRequest("news_" + now.format(dateTimeFormatter));

        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .put("index.routing.allocation.include.machine", "indexing"));

        client.indices().create(request, RequestOptions.DEFAULT);

        return RepeatStatus.FINISHED;
    }
}
