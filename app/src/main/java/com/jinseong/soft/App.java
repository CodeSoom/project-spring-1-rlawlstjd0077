/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.jinseong.soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
