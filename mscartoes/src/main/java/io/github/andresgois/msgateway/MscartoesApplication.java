package io.github.andresgois.msgateway;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class MscartoesApplication {

	public static void main(String[] args) {
		log.info("Informação {}"," Teste INFO");
		log.error("Error {}"," Teste Error");
		log.warn("Warnning {}"," Teste WARN");
		SpringApplication.run(MscartoesApplication.class, args);
	}

}
