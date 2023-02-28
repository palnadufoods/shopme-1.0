package com.shopme.admin.brand.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;


public class BrandExcelExporter extends AbstractExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Brands");
        XSSFRow row = sheet.createRow(0);
         
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "ID", style);      
        createCell(row, 1, "Brand Name", style);       
        createCell(row, 2, "Categories", style); 
         
    }
    
    private void createCell(XSSFRow row, int columnCount, Object value, XSSFCellStyle style) {
        sheet.autoSizeColumn(columnCount);
        XSSFCell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines(List<Brand> listAll) {
        int rowCount = 1;
 
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Brand brand: listAll) {
            XSSFRow row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, brand.getId(), style);
            createCell(row, columnCount++, brand.getName(), style);
            createCell(row, columnCount++, brand.getCategories().toString(), style);
             
        }
    }
	public void export(List<Brand> listAll, HttpServletResponse response) throws IOException {	
		super.setResponseHeader(response, "application/octet-stream", ".xlsx");
		workbook=new XSSFWorkbook();
		writeHeaderLine();
		writeDataLines(listAll);
		ServletOutputStream outputStream=response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();	
	}
}
