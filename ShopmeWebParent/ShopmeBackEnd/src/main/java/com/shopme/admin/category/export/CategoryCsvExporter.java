package com.shopme.admin.category.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;

public class CategoryCsvExporter extends AbstractExporter {

	public void export(List<Category> listAll, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "text/csv", ".csv");

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "ID", "Category Name", "Alias", "Enabled" };
		csvWriter.writeHeader(csvHeader);

		String[] fieldMapping = { "id", "name", "alias", "enabled" };

		for (Category cat : listAll) {
			csvWriter.write(cat, fieldMapping);
		}

		csvWriter.close();
	}
}