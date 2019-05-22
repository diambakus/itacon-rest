package com.kikia.itacon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ItaconApplication {

	public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ItaconApplication.class);
        logger.trace("STARTING THE ITACON APP");
		SpringApplication.run(ItaconApplication.class, args);
	}
}