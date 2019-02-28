package com.paytm.poc.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.poc.kafka.utility.PauseConsumerUtil;
import com.paytm.poc.kafka.utility.SwitchUtil;

@RestController
public class Controller {

	public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SwitchUtil utility;

	@Autowired
	private PauseConsumerUtil pauseConsumerUtil;

	@Value("${kafka.poc.topic.group.id}")
	private String kafkaPocTopicGroupId;

	@GetMapping("/stop-consumer")
	public String stopAcknowledgement() {

		LOGGER.info("Hit recieved stop consumer.");
		utility.setConsumerSwitchOn(false);
		return "Consumer stopped";
	}

	@GetMapping("/start-consumer")
	public String startAcknowledgement() {

		LOGGER.info("Hit recieved start consumer.");
		utility.setConsumerSwitchOn(true);
		pauseConsumerUtil.resumeConsumers(kafkaPocTopicGroupId);
		return "Consumer started";
	}

	@GetMapping("/start-producer")
	public String start() {

		LOGGER.info("Hit recieved start producer.");
		utility.setProducerSwitchOn(true);
		return "Produer started";
	}

	@GetMapping("/stop-producer")
	public String stop() {

		LOGGER.info("Hit recieved stop producer.");
		utility.setProducerSwitchOn(false);
		return "Produer stopped";

	}

}
