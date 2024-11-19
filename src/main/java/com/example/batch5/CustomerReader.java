package com.example.batch5;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomerReader implements org.springframework.batch.item.ItemReader<String> {

    private String[] tokens = {"java","spring"};
    private int index = 0;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (index >= tokens.length){
            return null;
        }
        String data = index + " " + tokens[index];
        index++;
        System.out.println("reading data - " + data);
        return data;
    }
}
