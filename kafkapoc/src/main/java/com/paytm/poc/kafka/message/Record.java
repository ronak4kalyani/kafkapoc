package com.paytm.poc.kafka.message;

import java.util.Random;

import lombok.Data;

@Data
public class Record {

    private String key;

    private String value;
    
    public Record(long number) {
    	
    	this.setKey(String.valueOf(number));
    	this.setValue("Message-" + number);
    }
    
    
    public Record() {
    	
    	Random random = new Random();
    	int randNum = random.nextInt(10000);
    	this.setKey(String.valueOf(randNum));
    	this.setValue("Message-" + randNum);
    }

}
