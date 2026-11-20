package com.example.appointmentservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointmentServiceApplication {

	public static void main(String[] args) {
		loadDotenv();
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

	private static void loadDotenv() {
		try {
			Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
			dotenv.entries().forEach(e -> {
				if (System.getProperty(e.getKey()) == null) {
					System.setProperty(e.getKey(), e.getValue());
				}
			});
		} catch (Exception ignored) {
		}

		try {
			Dotenv dotenvSub = Dotenv.configure().directory("backend/appointment-service").ignoreIfMissing().load();
			dotenvSub.entries().forEach(e -> {
				if (System.getProperty(e.getKey()) == null) {
					System.setProperty(e.getKey(), e.getValue());
				}
			});
		} catch (Exception ignored) {
		}

		try {
			Dotenv dotenvParent = Dotenv.configure().directory("../").ignoreIfMissing().load();
			dotenvParent.entries().forEach(e -> {
				if (System.getProperty(e.getKey()) == null) {
					System.setProperty(e.getKey(), e.getValue());
				}
			});
		} catch (Exception ignored) {
		}
	}

}
