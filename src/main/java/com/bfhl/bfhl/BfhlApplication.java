package com.bfhl.bfhl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Spring Boot application.
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *   - @Configuration: marks this as a source of bean definitions
 *   - @EnableAutoConfiguration: tells Spring Boot to auto-configure based on classpath
 *   - @ComponentScan: scans this package for all @Component, @Service, @Controller etc.
 */
@SpringBootApplication
public class BfhlApplication {

    public static void main(String[] args) {
        // This launches the embedded Tomcat server and wires everything together
        SpringApplication.run(BfhlApplication.class, args);
    }
}