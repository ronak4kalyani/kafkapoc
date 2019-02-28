package com.paytm.poc.kafka.utility;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class SwitchUtil {

	private boolean producerSwitchOn = false; 
	
	private boolean consumerSwitchOn = true; 
	
}
