package ru.clevertec.ecl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.clevertec.ecl.util")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
