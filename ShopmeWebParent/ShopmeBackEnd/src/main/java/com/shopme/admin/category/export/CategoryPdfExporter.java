package com.shopme.admin.category.export;

import java.io.IOException;
import java.util.List;
import java.awt.Color;
import javax.servlet.http.HttpServletResponse;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;

public class CategoryPdfExporter extends AbstractExporter {
	
	
	 private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Alias", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Enabled", font));
	        table.addCell(cell);       
	    }
	     
	    private void writeTableData(PdfPTable table,List<Category> listUsers) {
	        for (Category cat: listUsers) {
	            table.addCell(String.valueOf(cat.getId()));
	            table.addCell(cat.getName());
	            table.addCell(cat.getAlias());
	            table.addCell(String.valueOf(cat.isEnabled()));
	        }
	    }
	
	public void export(List<Category> listAll ,HttpServletResponse response) throws IOException {
		
		super.setResponseHeader(response,"application/pdf",".pdf");
		
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Categories", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);         
        document.add(p);
        
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[] {1.5f, 3.5f, 3.5f, 1.2f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table,listAll);
         
        document.add(table);
        document.close();		
    }
}
