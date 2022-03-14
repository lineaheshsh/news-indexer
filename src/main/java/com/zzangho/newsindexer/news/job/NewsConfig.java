package com.zzangho.newsindexer.news.job;

import com.zzangho.newsindexer.common.Constants;
import com.zzangho.newsindexer.news.listener.NewsIndexerJobExecutionListener;
import com.zzangho.newsindexer.news.vo.JsonNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;

@Slf4j
@Configuration
public class NewsConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TaskExecutor taskExecutor;

    private int chunkSize = 1000;

    public NewsConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, TaskExecutor taskExecutor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.taskExecutor = taskExecutor;
    }

    @Bean
    public Job newsIndexerJob() throws Exception {
        return jobBuilderFactory.get(Constants.NEWS_JOB + "Job")
                .incrementer(new RunIdIncrementer())
                .start(newsIndexerStep())
                .listener(new NewsIndexerJobExecutionListener())
                .build();
    }

    @Bean
    public Step newsIndexerStep() throws Exception {
        return stepBuilderFactory.get(Constants.NEWS_JOB + "Step")
                .<JsonNews, JsonNews>chunk(chunkSize)
                .reader(itemReader())
                .writer(itemWriter())
                .taskExecutor(taskExecutor)
                .throttleLimit(8)
                .build();
    }

    /**
     * Thread safe
     * @return
     * @throws Exception
     */
    private FlatFileItemReader<JsonNews> itemReader() throws Exception {
        FlatFileItemReader<JsonNews> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(Constants.BULK_FILE));
        itemReader.setLineMapper((line, lineNumber) -> new JsonNews(line));

        return itemReader;
    }
//    private SynchronizedItemStreamReader<JsonNews> itemReader() throws Exception {
//        SynchronizedItemStreamReader<JsonNews> itemStreamReader = new SynchronizedItemStreamReader<>();
//        itemStreamReader.setDelegate(new BulkFileReader());
//
//        itemStreamReader.afterPropertiesSet();
//        return itemStreamReader;
//    }

    private ItemWriter<JsonNews> itemWriter() {
        return items -> {
            for (JsonNews news : items) {
                log.info(String.valueOf(news.getContents_id()));
            }
        };
    }
}
