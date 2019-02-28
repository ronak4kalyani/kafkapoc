package com.paytm.poc.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(SenderConfig.class)
public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

	@Autowired
	SenderConfig senderConfig;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String payload) {

		LOGGER.info("sending payload to topic={}: payload={}", topic, payload);
		kafkaTemplate.send(topic, payload);

	}

	public void send(String topic, String key, String payload) {

		LOGGER.info("sending payload to topic='{}: payload={}'", topic, payload);

		kafkaTemplate.send(topic, key, payload);

	}

}
