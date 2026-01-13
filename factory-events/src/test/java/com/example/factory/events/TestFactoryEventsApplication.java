package com.example.factory.events;

import org.springframework.boot.SpringApplication;

public class TestFactoryEventsApplication {

	public static void main(String[] args) {
		SpringApplication.from(FactoryEventsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
