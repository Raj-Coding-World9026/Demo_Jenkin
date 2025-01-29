package com.pdfGeneration.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdfGeneration.Entity.Employee;
import com.pdfGeneration.Repository.EmployeeRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ByteArrayInputStream generatePdfReport() {
        List<Employee> employees = employeeRepository.findAll();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("Employee List");
            document.add(title);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3});

            PdfPCell hcell;
            hcell = new PdfPCell(new Paragraph("ID"));
            table.addCell(hcell);

            hcell = new PdfPCell(new Paragraph("Name"));
            table.addCell(hcell);

            hcell = new PdfPCell(new Paragraph("Department"));
            table.addCell(hcell);

            for (Employee employee : employees) {
                PdfPCell cell;

                cell = new PdfPCell(new Paragraph(employee.getId().toString()));
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(employee.getName()));
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(employee.getDepartment()));
                table.addCell(cell);
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public ByteArrayInputStream generatePdfForEmployee(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (!employeeOptional.isPresent()) {
            throw new RuntimeException("Employee not found with id: " + employeeId);
        }

        Employee employee = employeeOptional.get();

        // Creating a document and writing to the PDF
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Format the PDF content
            String content = String.format("Hi %s,\n\n" +
                            "Thank you for visiting our company. We are happy to tell that your salary of $%.2f " +
                            "has been credited to your account for the department %s.",
                    employee.getName(), employee.getSalary(), employee.getDepartment());

            document.add(new Paragraph(content));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    public ByteArrayInputStream generateExcelReportForAllEmployees() throws IOException {
        List<Employee> employees = employeeRepository.findAll(); // Fetch all employees

        // Create a new Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees Report");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Name", "Department", "Salary", "Image"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Fill data rows for each employee
        int rowIdx = 1;
        for (Employee employee : employees) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getDepartment());
            row.createCell(3).setCellValue(employee.getSalary());

            // Optionally handle employee image
            if (employee.getImage() != null) {
                int pictureIdx = workbook.addPicture(employee.getImage(), Workbook.PICTURE_TYPE_PNG);
                CreationHelper helper = workbook.getCreationHelper();
                Drawing<?> drawing = sheet.createDrawingPatriarch();

                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(4);  // Image in the 5th column
                anchor.setRow1(rowIdx - 1);  // Image in the corresponding row

                Picture picture = drawing.createPicture(anchor, pictureIdx);
                picture.resize(1, 1);  // Resize image to fit within the cell
            }
        }

        // Auto-size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a ByteArrayOutputStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
    public Employee createPost(Employee emp) {
        Employee e= new Employee();
        e.setName(emp.getName());
        e.setDepartment(emp.getDepartment());
        e.setSalary(emp.getSalary());
        Employee save = employeeRepository.save(e);
        return save;
    }
    public void serviceTest(String s){
        System.out.println(s);
    }
}
