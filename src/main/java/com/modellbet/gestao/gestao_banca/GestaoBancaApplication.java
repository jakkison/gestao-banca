package com.modellbet.gestao.gestao_banca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestaoBancaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoBancaApplication.class, args);
		System.setProperty("debug", "true");
	}

}
