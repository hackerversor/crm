package com.common.crm.util;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
	public final static String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd";
	public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			DATE_OUTPUT_PATTERNS);
	/**
	 * 	读取excel
	 * @param file_in 文件流
	 * @param sheet_index sheet页位置 从0开始
	 * @param start_row 开始行号 从0开始
	 * @param column_count 列数量 
	 * @return
	 */
	public static List<List<String>> readExcel(InputStream file_in,int sheet_index,int start_row,int column_count){
		Workbook wb = null;

		ZipSecureFile.setMinInflateRatio(-1.0d);
		try {
			wb = new XSSFWorkbook(file_in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Sheet sheet =  wb.getSheetAt(sheet_index);
		List<List<String>> list = readExcelSheet(sheet, start_row, column_count);
		
		return list;
	}
	
	public static List<List<String>> readExcelSheet(Sheet sheet, int start_row, int column_count){
		List<List<String>> list = new ArrayList<List<String>>();
		int row_num = sheet.getLastRowNum();
		String sheet_name = sheet.getSheetName();
		//System.out.println(sheet_name);
		for (int row_index = start_row; row_index <= row_num; row_index++) {//遍历行
			Row row = sheet.getRow(row_index);
			List<String> col_list = new ArrayList<String>();
			for (int col_index = 0; col_index < column_count ; col_index++) {
				Cell cell = row.getCell(col_index);
				if(cell != null) {
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
					String value = cell.getStringCellValue();
					col_list.add(value);
				}else {
					col_list.add(null);
				}
			}
			list.add(col_list);
		}
		
		return list;
	}
	
	public static List<List<String>> autoReadExcelSheet(String path) throws Exception{
		List<List<String>> list = new ArrayList<List<String>>();
		InputStream file_in = new FileInputStream(path);
		Workbook wb = null;
		ZipSecureFile.setMinInflateRatio(-1.0d);
		try {
			wb = new XSSFWorkbook(file_in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Sheet sheet =  wb.getSheetAt(0);
		int row_num = sheet.getLastRowNum();
		String sheet_name = sheet.getSheetName();
		//System.out.println(sheet_name);
		Row row0 = sheet.getRow(0);
		int column_count = row0.getLastCellNum();
		int start_row = 1;
		for (int row_index = start_row; row_index <= row_num; row_index++) {//遍历行
			Row row = sheet.getRow(row_index);
			List<String> col_list = new ArrayList<String>();
			for (int col_index = 0; col_index < column_count ; col_index++) {
				Cell cell = row.getCell(col_index);
				if(cell != null) {
					cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
					String value = cell.getStringCellValue();
					col_list.add(value);
				}else {
					col_list.add(null);
				}
			}
			list.add(col_list);
		}
		
		return list;
	}
	
	public static List<List<String>> autoReadExcelSheet(InputStream file_in) throws Exception{
		List<List<String>> list = new ArrayList<List<String>>();
		Workbook wb = null;
		ZipSecureFile.setMinInflateRatio(-1.0d);
		try {
			wb = new XSSFWorkbook(file_in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Sheet sheet =  wb.getSheetAt(0);
		int row_num = sheet.getLastRowNum();
		String sheet_name = sheet.getSheetName();
		//System.out.println(sheet_name);
		Row row0 = sheet.getRow(0);
		int column_count = row0.getLastCellNum();
		int start_row = 1;
		for (int row_index = start_row; row_index <= row_num; row_index++) {//遍历行
			Row row = sheet.getRow(row_index);
			List<String> col_list = new ArrayList<String>();
			for (int col_index = 0; col_index < column_count ; col_index++) {
				if(row != null){
				Cell cell = row.getCell(col_index);
				if(cell != null) {

						col_list.add(getCellValue(cell));

				}else {
					col_list.add(null);
				}}
			}
			list.add(col_list);
		}
		return list;
	}
	public static String getCellValue(Cell cell) {
		String ret = "";
		if (cell == null) return ret;
		CellType type = cell.getCellTypeEnum();
		switch (type) {
			case BLANK:
				ret = "";
				break;
			case BOOLEAN:
				ret = String.valueOf(cell.getBooleanCellValue());
				break;
			case ERROR:
				ret = null;
				break;
			case FORMULA:
				Workbook wb = cell.getSheet().getWorkbook();
				CreationHelper crateHelper = wb.getCreationHelper();
				FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
				ret = getCellValue(evaluator.evaluateInCell(cell));
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date theDate = cell.getDateCellValue();
					ret = simpleDateFormat.format(theDate);
				} else {
					ret = NumberToTextConverter.toText(cell.getNumericCellValue());
				}
				break;
			case STRING:
				ret = cell.getRichStringCellValue().getString();
				break;
			default:
				ret = "";
		}

		return ret.trim(); // 有必要自行trim
	}
}
