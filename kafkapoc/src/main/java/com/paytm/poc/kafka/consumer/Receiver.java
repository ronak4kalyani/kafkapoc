package com.paytm.poc.kafka.consumer;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.paytm.poc.kafka.message.Record;
import com.paytm.poc.kafka.utility.PauseConsumerUtil;
import com.paytm.poc.kafka.utility.SwitchUtil;

@Component
public class Receiver {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SwitchUtil switchUtility;

	@Autowired
	private PauseConsumerUtil pauseConsumerUtil;

	@Value("${kafka.poc.topic.group.id}")
	private String groupId;

	@PostConstruct
	public void init() {
		pauseConsumerUtil.makeConsumerGroupPausable(groupId);
	}

	@KafkaListener(topics = "${kafka.poc.topic}", groupId = "${kafka.poc.topic.group.id}", containerFactory = "kafkaListenerContainerFactoryForOurReciever")
	public void consumeTransaction(ConsumerRecord<String, Record> record, Acknowledgment ack,
			KafkaConsumer<String, Record> kafkaConsumer) throws InterruptedException {

		LOGGER.info("Message recieved in consumer {}", record);

		if (switchUtility.isConsumerSwitchOn()) {

			ack.acknowledge();
			LOGGER.info("Record acknowledged Successfully.");

		} else {

			pauseConsumerUtil.pauseConsumer(groupId, kafkaConsumer);
		}

	}

}
