package com.techi.projone.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.techi.projone.model.Customer;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Fname", "Lname", "Cellno", "Addrs" };
	/* static String SHEET = "Data"; */

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<Customer> excelToCustomer(InputStream is) throws Exception {

		List<Customer> custlist = new ArrayList<Customer>();

		try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			XSSFSheet sheet = workbook.getSheetAt(0);

			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Customer customer = new Customer();

				XSSFRow row = sheet.getRow(i);

				try {

					customer.setId((int) row.getCell(0).getNumericCellValue());
					customer.setFname(row.getCell(1).getStringCellValue());
					customer.setLname(row.getCell(2).getStringCellValue());
					customer.setPnum((Long.toString((long) row.getCell(3).getNumericCellValue())));
					customer.setAddrs(row.getCell(4).getStringCellValue());
					custlist.add(customer);
				} catch (Exception e) {
					throw new RuntimeException("Inavlid column values: " + e.getMessage());
				}
			}
			workbook.close();
			return custlist;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
