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
		applyDotenv(tryLoad(() -> Dotenv.configure().ignoreIfMissing().load()));
		applyDotenv(tryLoad(() -> Dotenv.configure().directory("backend/patient-service").ignoreIfMissing().load()));
		applyDotenv(tryLoad(() -> Dotenv.configure().directory("../").ignoreIfMissing().load()));
	}

	private static Dotenv tryLoad(java.util.function.Supplier<Dotenv> supplier) {
		try {
			return supplier.get();
		} catch (Exception ignored) {
			return null;
		}
	}

	private static void applyDotenv(Dotenv dotenv) {
		if (dotenv != null) {
			dotenv.entries().forEach(e -> {
				String val = e.getValue();
				if (val != null && !val.trim().isEmpty()) {
					System.setProperty(e.getKey(), val);
				} else if (System.getProperty(e.getKey()) == null) {
					System.setProperty(e.getKey(), "");
				}
			});
		}
	}

}
