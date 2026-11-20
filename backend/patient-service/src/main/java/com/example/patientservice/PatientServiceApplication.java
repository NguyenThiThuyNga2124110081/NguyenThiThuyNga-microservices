package com.example.patientservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		loadDotenv();
		SpringApplication.run(PatientServiceApplication.class, args);
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
			Dotenv dotenvSub = Dotenv.configure().directory("backend/patient-service").ignoreIfMissing().load();
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
