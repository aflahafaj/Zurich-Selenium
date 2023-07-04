package com.rds.rpa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rds.rpa.common.CommonUtils;

@Controller
public class UploadFileController {

	@GetMapping("/upload-file")
	public String uploadFileView() {

		return "file-upload";
	}

	@PostMapping("/upload-file")
	public String processFile(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
		String folderPath = "D:/[FAJRINA]/[ECLIPSE]/";
		File folder = new File(folderPath);
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file2 : files) {
				if (file2.getName().endsWith(".xlsx")) {
					copyExcelFile(file2);
                }
			}
		}

		System.out.println("\nok");
		return "file-upload";
	}

	public void cellCopyValue(Cell source, Cell target) {
		switch (source.getCellType()) {
		case STRING:
			target.setCellValue(source.getStringCellValue());
			break;
		case NUMERIC:
			target.setCellValue(source.getNumericCellValue());
			break;
		default:
		}
	}

	public boolean isValidRow(Row row) {

		boolean isValid = true;

		for (int i = 0; i < 5; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				isValid = false;
				break;
			}

			String stringCellValue = null;
			switch (cell.getCellType()) {
			case STRING:
				// field that represents string cell type
				stringCellValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				// field that represents number cell type
				stringCellValue = String.valueOf(cell.getNumericCellValue());
				break;
			default:
			}

			isValid = isValid && CommonUtils.isNotEmpty(stringCellValue);
		}

		return isValid;
	}

	public void copyExcelFile(File file) throws IOException{
		try (XSSFWorkbook workbookCopy = new XSSFWorkbook()) {
			Sheet sheetCopy = workbookCopy.createSheet("result");
			int indexRowCopy = 0;
			InputStream inputStream = new FileInputStream(file);

			System.out.println("file: " + file);
			try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
				Sheet sheet = workbook.getSheetAt(0);
				System.out.println("=> " + sheet.getSheetName());

				// row
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					boolean isValidRow = isValidRow(row);
					if (isValidRow) {
						// row
						Row rowCopy = sheetCopy.createRow(indexRowCopy);
						int i;
						
						for (i = 0; i < 5; i++) {
							Cell target0 = rowCopy.createCell(i);
							Cell source0 = row.getCell(i);
							
							cellCopyValue(source0, target0);
							
							//setCellStyle target dari integer jadi date
							if (i==2 || i==3) {
								CellStyle cellStyle = workbookCopy.createCellStyle();
								CreationHelper createHelper = workbookCopy.getCreationHelper();
								cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
								target0.setCellStyle(cellStyle);
							}
						}

						indexRowCopy++;
					}
				}
			}
			try (FileOutputStream outputStream = new FileOutputStream("D:/[FAJRINA]/[ECLIPSE]/result/result-"
					+ CommonUtils.dateToString(new Date(), "yyyyMMdd-HHmmss") + ".xlsx")) {
				workbookCopy.write(outputStream);
			}
		}
	}
}
