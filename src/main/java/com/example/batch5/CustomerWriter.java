package com.example.batch5;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class CustomerWriter implements ItemWriter<String> {
    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        for (String data : chunk){
            System.out.println("writer data - " + data);
        }
        System.out.println("completed writing data.");
    }
}
