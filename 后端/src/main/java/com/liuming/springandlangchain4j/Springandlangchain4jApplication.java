package com.liuming.springandlangchain4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)

@MapperScan("com.liuming.springandlangchain4j.generator.mapper")
public class Springandlangchain4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springandlangchain4jApplication.class, args);
	}

}
