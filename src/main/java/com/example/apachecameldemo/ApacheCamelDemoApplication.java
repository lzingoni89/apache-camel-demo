package com.example.apachecameldemo;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApacheCamelDemoApplication {

    @Autowired
    ProducerTemplate producerTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ApacheCamelDemoApplication.class, args);
    }

}
