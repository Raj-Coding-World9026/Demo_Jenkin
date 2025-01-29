package com.pdfGeneration;

import com.pdfGeneration.Entity.Employee;
import com.pdfGeneration.Repository.EmployeeRepository;
import com.pdfGeneration.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PdfApplicationTests {
@Autowired
private EmployeeService es;
@Autowired
private EmployeeRepository er;
Employee emp= new Employee();

	@Test
	void contextLoads() {
	}
	@Test
	public void createStu(){
		emp.setId(1L);
		emp.setName("ram");
		emp.setSalary(90900);
		emp.setDepartment("sd");
		Employee save = er.save(emp);
		System.out.println("........./////////......."+save);
	}
	@Test
	public void list(){
		List<Employee> all = er.findAll();
		for(Employee e:all){
			System.out.println(e.getId()+" "+e.getName()+" "+e.getSalary()+" "+e.getDepartment());
		}

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
