package com.example.kakao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class KakaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoApplication.class, args);
	}

}
