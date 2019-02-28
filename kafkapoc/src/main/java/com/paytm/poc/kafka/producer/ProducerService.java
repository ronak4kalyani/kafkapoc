package com.paytm.poc.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytm.poc.kafka.message.Record;
import com.paytm.poc.kafka.utility.SwitchUtil;

@Component
public class ProducerService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SwitchUtil utility ;
	
	@Autowired
	private Sender sender;
	
	@Value("${kafka.poc.topic}")
	private String topic;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private long currentRecordNum = 1000l;
	
	@Scheduled(initialDelay= 5000, fixedDelay = 5000)
	public void init() throws JsonProcessingException {
		
		if (utility.isProducerSwitchOn()) {
			
			Record record = new Record(currentRecordNum++);
			LOGGER.info("Pushing into kafka record {}", record);
			sender.send(topic,objectMapper.writeValueAsString(record));
			
		} else {
			LOGGER.info("Producer Switch is off.");
		}
		
	}

}
