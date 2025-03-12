package com.example.KinoFinances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class KinoFinancesApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(KinoFinancesApplication.class, args);
		DbAccess dbAccess = context.getBean(DbAccess.class);
		System.out.println(dbAccess.GetAllPayments().toString());
	}
}

