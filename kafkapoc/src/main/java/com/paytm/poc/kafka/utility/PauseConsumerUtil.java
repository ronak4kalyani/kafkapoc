package com.paytm.poc.kafka.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PauseConsumerUtil {

	private Map<String, Object> groupIdLocks = new HashMap<>();

	public void makeConsumerGroupPausable(String groupId) {

		if (groupIdLocks.get(groupId) == null) {
			
			groupIdLocks.put(groupId, new Object());
		}
	}

	public void pauseConsumer(String groupId, KafkaConsumer kafkaConsumer) throws InterruptedException {

		if (isConsumerGroupPausable(groupId)) {

			Object synchronizeUpon = groupIdLocks.get(groupId);

			Set<TopicPartition> partitions = kafkaConsumer.assignment();
			kafkaConsumer.pause(partitions);

			synchronized (synchronizeUpon) {
				synchronizeUpon.wait();
			}

			kafkaConsumer.resume(kafkaConsumer.paused());
			
			throw new RuntimeException();
		}
	}

	public void resumeConsumers(String groupId) {

		if (isConsumerGroupPausable(groupId)) {
			
			Object synchronizeUpon = groupIdLocks.get(groupId);
			
			synchronized (synchronizeUpon) {
				synchronizeUpon.notifyAll();
			}
		}
	}

	private boolean isConsumerGroupPausable(String groupId) {

		return groupIdLocks.get(groupId) != null;
	}

}
