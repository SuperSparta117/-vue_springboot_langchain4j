package com.liuming.springandlangchain4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class Springandlangchain4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springandlangchain4jApplication.class, args);
	}

}
