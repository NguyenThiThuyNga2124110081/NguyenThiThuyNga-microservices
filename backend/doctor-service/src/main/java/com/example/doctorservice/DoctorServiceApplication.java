package com.example.doctorservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class DoctorServiceApplication {

    public static void main(String[] args) {
        loadDotenv();
        SpringApplication.run(DoctorServiceApplication.class, args);
    }

    private static void loadDotenv() {
        File curEnv = new File(".env");
        File parentEnv = new File("../.env");
        File rootEnv = new File("../../.env");

        String dir = null;
        if (curEnv.exists()) {
            dir = ".";
        } else if (parentEnv.exists()) {
            dir = "..";
        } else if (rootEnv.exists()) {
            dir = "../..";
        }

        if (dir != null) {
            try {
                Dotenv dotenv = Dotenv.configure()
                        .directory(dir)
                        .ignoreIfMissing()
                        .load();
                dotenv.entries().forEach(entry -> {
                    if (System.getProperty(entry.getKey()) == null) {
                        System.setProperty(entry.getKey(), entry.getValue());
                    }
                });
            } catch (Exception ignored) {
            }
        }
    }
}
