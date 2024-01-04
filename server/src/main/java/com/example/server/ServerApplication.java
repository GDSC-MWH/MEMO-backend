package com.example.server;

import com.example.server.firebase.FireBaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		FireBaseInitializer.initialize();
		SpringApplication.run(ServerApplication.class, args);
	}
}
