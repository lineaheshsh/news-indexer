package com.zzangho.newsindexer.news.item;

import com.zzangho.newsindexer.common.Constants;
import com.zzangho.newsindexer.news.vo.JsonNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class BulkFileReader extends SynchronizedItemStreamReader<JsonNews> implements ItemStreamReader<JsonNews> {
    private FileReader file;

    @Override
    public JsonNews read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        int ch;
        while ((ch = file.read()) != -1) {
            System.out.print((char) ch);
        }

        JsonNews jsonNews = new JsonNews();
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            file = new FileReader(Constants.BULK_FILE);
        } catch (IOException e) {
            log.error("File is not exist");
            e.printStackTrace();
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
