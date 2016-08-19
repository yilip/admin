package com.lip.admin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 创建excel文档，
	 * 
	 * @param list
	 *            数据
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 */
	public static Workbook createWorkBook(String sheetName, List<Map<String, Object>> list, String[] keys,
			String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new SXSSFWorkbook(1000);
		// Workbook wb = new HSSFWorkbook();
		if (StringUtils.isBlank(sheetName))
			sheetName = "sheet1";
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(sheetName);
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 10);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}

		// 设置每行每列的值
		for (int i = 0; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow(i + 1);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}

		return wb;
	}

	/**
	 * 读取Excel文件
	 * 
	 * @param in
	 * @param column
	 *            列名
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> readExcel(InputStream in, String[] column) throws IOException {
		List<Map<String, Object>> result = new ArrayList<>();
		XSSFWorkbook wb = new XSSFWorkbook(in);
		boolean insert = true;
		// excel 页
		int sheetNum = wb.getNumberOfSheets();
		try {
			for (int i = 0; i < sheetNum; i++) {
				XSSFSheet sheet = wb.getSheetAt(i);
				int rows = sheet.getLastRowNum();
				// 从第二行开始读取
				for (int j = 1; j <= rows; j++) {
					try {
						XSSFRow row = sheet.getRow(j);
						Map<String, Object> item = new HashMap<>();
						insert = true;
						for (int k = 0; k < column.length; k++) {
							try {
								String value = getValue(row.getCell(k));
								if (StringUtil.isEmpty(value)&&k==0) {
									insert = false;
									break;
								}
								item.put(column[k], getValue(row.getCell(k)));
							} catch (Exception e) {
								insert = false;
								logger.error(String.format("第%s行%s数据有问题", j, k), e);
							}
						}
						if (item.size() > 0 && insert)
							result.add(item);
					} catch (Exception e) {
						logger.error(String.format("第%s行数据有问题", j), e);
					}
				}
			}
		} catch (Exception e) {
			logger.error("excel数据异常", e);
		}
		return result;
	}

	/**
	 * 得到一个单元格的数据
	 * 
	 * @param cell
	 * @return
	 */
	public static String getValue(Cell cell) {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue() + "";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_ERROR:
			return "数据类型错误";
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() + "";
		case Cell.CELL_TYPE_BLANK:
			return null;
		default:
			return "未知数据类型";
		}
	}

	public static void main(String[] args) throws IOException {
		String[] column = { "userId", "userName", "mobile", "certificateNo", "agentLevel", "limit" };
		InputStream in = new FileInputStream(new File("C://Users//Administrator//Desktop//test.xlsx"));
		List<Map<String, Object>> list = readExcel(in, column);
		for (Map<String, Object> map : list)
			System.out.println(map);
	}
}