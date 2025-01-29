package com.pdfGeneration.Controller;

import com.pdfGeneration.Entity.Employee;
import com.pdfGeneration.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
@PostMapping
public ResponseEntity<Employee> createPost(@RequestBody Employee emp){
    Employee post = employeeService.createPost(emp);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
}

@GetMapping
@Test
public String demoJenkin(){
    System.out.println("demo jenkin");
    return "works";
}
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateEmployeePdfReport() {
        ByteArrayInputStream bis = employeeService.generatePdfReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateEmployeePdfReport(@PathVariable Long id) {
        ByteArrayInputStream bis = employeeService.generatePdfForEmployee(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employee_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping(value = "/all/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateAllEmployeesExcelReport() throws IOException {
        ByteArrayInputStream bis = employeeService.generateExcelReportForAllEmployees();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employees_report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bis.readAllBytes());
    }
}
