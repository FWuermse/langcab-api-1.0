package com.flowcode.langcab;

import com.flowcode.langcab.services.LoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class LangCabApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(LangCabApplication.class, args);
		LoginService loginService = new LoginService();
		loginService.login();
	}
}
