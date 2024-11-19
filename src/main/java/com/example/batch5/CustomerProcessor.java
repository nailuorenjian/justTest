package com.example.batch5;

import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String data) throws Exception {
        System.out.println("processing data - " + data);
        data = data.toUpperCase();
        return data;
    }
}
