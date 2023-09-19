package Wailwil.SupportFiles;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ExcelDataReader {
    private String fileName = null;
	private FileInputStream excelFileInputStream;
	private Map<String, Integer> columnNameIndexMap = null;
	private Map<Integer, List<String>> columnNumberWiseData = null;

	public ExcelDataReader(String fileName) {
		// super();
		System.out.println(fileName);
		if (fileName == null || fileName.isEmpty()) {
			throw new NullPointerException("File Name can not be null or empty");
		}
		this.fileName = fileName + ".xlsx";
		this.columnNameIndexMap = new HashMap<>();
		this.columnNumberWiseData = new HashMap<>();
	}

	/**
	 * +
	 *
	 * @return
	 */
	public boolean isFilePresent() {
		return new File(fileName).exists() ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * +
	 *
	 * @return
	 * @throws FileNotFoundException
	 */
	public ExcelDataReader loadFile() throws FileNotFoundException {
		if (!isFilePresent()) {
			throw new FileNotFoundException("File Not Fount on Path :" + fileName);

		}
		excelFileInputStream = new FileInputStream(new File(fileName));
		return this;
	}

	/**
	 *
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public ExcelDataReader loadExcel(String sheetName) throws IOException {
		XSSFWorkbook myExcelBook = new XSSFWorkbook(excelFileInputStream);
		XSSFSheet myExcelSheet = myExcelBook.getSheet(sheetName);
		myExcelSheet.addIgnoredErrors(new CellRangeAddress(0, 9999, 0, 9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT);

		XSSFRow headerRow = myExcelSheet.getRow(0);
		int counter = 0;
		for (Cell cell : headerRow) {
			if (cell.getCellType() == CellType.STRING && cell.getStringCellValue() != null
					&& !cell.getStringCellValue().isEmpty()) {
				columnNameIndexMap.put(cell.getStringCellValue(), counter++);
			}
		}
		System.out.println(" Found " + (counter) + " Column in excel sheet :" + sheetName);

		String value = "";
		for (Row row : myExcelSheet) {
			if (counter > 0) {
				counter = 0;
				continue;
			}
			int columnNumber = 0;
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.STRING) {
					value = cell.getStringCellValue();
					System.out.println(value);
				}
				if (cell.getCellType() == CellType.NUMERIC) {
					// cell.setCellType(CellType.STRING);
					value = cell.getStringCellValue();
					// value = String.valueOf(row.getCell(1).getStringCellValue());
				}

				if (value.equals("NA")) {
					value = "";
				}

				if (columnNumberWiseData.containsKey(columnNumber)) {

					columnNumberWiseData.get(columnNumber).add(value.trim());
				} else {
					List<String> columnData = new ArrayList<>();
					columnData.add(value.trim());
					columnNumberWiseData.put(columnNumber, columnData);
				}
				columnNumber++;
			}

		}
		myExcelBook.close();
		return this;
	}

	public Map<String, String> getKeyValuePairData() {

		Map<String, String> keyValueDataMap = null;
		if (columnNumberWiseData.isEmpty()) {
			System.out.println("Data Not Found in Excel");
		}
		List<String> columnData1 = columnNumberWiseData.get(0);
		List<String> columnData2 = columnNumberWiseData.get(1);
		if (columnData1.size() == columnData2.size()) {
			int counter = 0;
			keyValueDataMap = new HashMap<>();
			for (String key : columnData1) {
				keyValueDataMap.put(key, columnData2.get(counter++));
			}

		} else {
			System.out.println(" column Data Mismatch");
			Assert.fail("Please check Column Data");
		}

		return keyValueDataMap;
	}

	/**
	 * @author Sana Ansari
	 *         New methods added here for reading data from the excel file
	 *
	 */
	
	/**
	 * This method will take default path as Smoke\TestData and read the excel and
	 * return key value pair for a row key which will be the first column on the
	 * excel row
	 * 
	 * @param FileName
	 * @param Sheet
	 * @param rowkey
	 * @return
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public static Map<String, String> readExcel(String FileName, String Sheet, String rowkey)
			throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		String path = System.getProperty("user.dir") + "\\TestData";
		// System.out.println(path);
		return readExcel(path, FileName, Sheet, rowkey);

	}

	/**
	 * This method will read the excel file for and return data for given row key
	 * 
	 * @param path
	 * @param FileName
	 * @param Sheet
	 * @param rowkey
	 * @return
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public static Map<String, String> readExcel(String path, String FileName, String Sheet, String rowkey)
			throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {

		Workbook workbook;
		Map<String, String> data = new LinkedHashMap<String, String>();
		try {
			File file = new File(path + "\\" + FileName);
			if (!file.exists()) {
				throw new FileNotFoundException("File Not Fount on Path :" + FileName);
			}
			if (FileName.contains(".xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else {
				workbook = WorkbookFactory.create(file);
			}
			DataFormatter dataFormatter = new DataFormatter();
			Sheet sheet = workbook.getSheet(Sheet);
			for (Row row : sheet) {
				if (!(row.getRowNum() == 0)) {
					if (dataFormatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowkey))
						for (int i = 0; i < row.getLastCellNum(); i++) {
							data.put(dataFormatter.formatCellValue(sheet.getRow(0).getCell(i)),
									dataFormatter.formatCellValue(row.getCell(i)));
						}
				}
			}
			workbook.close();
			return data;

		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * This method reads all the rows from the specified Excel file and sheet and
	 * returns a list of maps,
	 * where each map represents a row in the Excel file.
	 * 
	 * @param FileName
	 * @param Sheet
	 * @param rowkey
	 * @return
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public static List<Map<String, String>> readExcelAllRow(String FileName, String Sheet, String rowkey)
			throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		String path = System.getProperty("user.dir") + "\\TestData";
		System.out.println(path);
		return readExcelAllRow(path, FileName, Sheet, rowkey);

	}

	/**
	 * This method will return all the rows for given row key
	 * 
	 * @param path
	 * @param FileName
	 * @param Sheet
	 * @param rowkey
	 * @return
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public static List<Map<String, String>> readExcelAllRow(String path, String FileName, String Sheet, String rowkey)
			throws org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		Workbook workbook;
		Map<String, String> data;
		List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
		try {
			File file = new File(path + "\\" + FileName);
			if (!file.exists()) {
				throw new FileNotFoundException("File Not Fount on Path :" + FileName);
			}
			if (FileName.contains(".xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else {
				workbook = WorkbookFactory.create(file);
			}
			DataFormatter dataFormatter = new DataFormatter();
			Sheet sheet = workbook.getSheet(Sheet);
			for (Row row : sheet) {
				if (!(row.getRowNum() == 0)) {
					if (dataFormatter.formatCellValue(row.getCell(0)).equalsIgnoreCase(rowkey)) {
						data = new LinkedHashMap<String, String>();
						for (int i = 0; i < row.getLastCellNum(); i++) {
							data.put(dataFormatter.formatCellValue(sheet.getRow(0).getCell(i)),
									dataFormatter.formatCellValue(row.getCell(i)));
						}

						lst.add(data);
					}
				}
			}
			workbook.close();
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			return lst;
		}
	}
    
}
