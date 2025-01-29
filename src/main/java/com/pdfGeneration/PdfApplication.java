package com.pdfGeneration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfApplication {
	@Test
	public static void jenkin(){
System.out.println("From jenkin");
	}

	public static void main(String[] args) {
		System.out.println("staaart");
		SpringApplication.run(PdfApplication.class, args);
	}

}
