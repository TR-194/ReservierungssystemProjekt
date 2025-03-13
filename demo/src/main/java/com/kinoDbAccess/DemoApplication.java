package com.kinoDbAccess;

import com.kinoDbAccess.kafka.KafkaRequestHandler;
import com.kinoDbAccess.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		DbAccess dbAccess = context.getBean(DbAccess.class);
		KafkaRequestHandler requestHandler = context.getBean(KafkaRequestHandler.class);
	}
}
