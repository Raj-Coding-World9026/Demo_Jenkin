package com.pdfGeneration;

import com.pdfGeneration.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PdfApplicationTests {
@Autowired
private EmployeeService es;
	@Test
	void contextLoads() {
	}
	@Test
	public  void jenkin(){
		System.out.println("From jenkin test");
	}
	@Test
	public  void callst(){
		es.serviceTest("calling test");
	}
}
