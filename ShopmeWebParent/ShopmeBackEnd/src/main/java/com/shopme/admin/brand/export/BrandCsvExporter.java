package com.shopme.admin.brand.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;

public class BrandCsvExporter  extends AbstractExporter{

	public void export(List<Brand> listAll, HttpServletResponse response) throws IOException {
		
		super.setResponseHeader(response, "text/csv",".csv");
		
		ICsvBeanWriter csvWriter=new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader= {"ID","Brand Name","Categories"};
		csvWriter.writeHeader(csvHeader);
		
		String[] fieldMapping= {"id","name","categories"};
		
		for(Brand brand:listAll)
		{
			csvWriter.write(brand, fieldMapping);
		}
		
		csvWriter.close();
	}
}