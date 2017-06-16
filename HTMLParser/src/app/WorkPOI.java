package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkPOI {
	
	static List<RowEntry> myEntries = new ArrayList<RowEntry>();
	static int i = 0;
	
	public static Map<String, CellStyle> createCellStyles(Workbook wb, Boolean underline) {
		Map<String, CellStyle> myCellStyles = new HashMap<String, CellStyle>();
		
		CellStyle csDateRight = wb.createCellStyle();
		CellStyle csHour = wb.createCellStyle();
		CellStyle csAcc = wb.createCellStyle();
		CellStyle csPerc = wb.createCellStyle();
	//	CellStyle csBottBord = wb.createCellStyle();
		CellStyle csUSD = wb.createCellStyle();
		CellStyle csDef = wb.createCellStyle();
	//	DataFormat df = wb.createDataFormat();
		CreationHelper createHelper = wb.getCreationHelper();
		
		csDateRight.setAlignment(CellStyle.ALIGN_RIGHT);
		csDateRight.setDataFormat((short)14);
	//	cs.setDataFormat(
	//		    createHelper.createDataFormat().getFormat("d.m.yyyy"));
		
		csHour.setDataFormat((short)14);
		csHour.setAlignment(CellStyle.ALIGN_RIGHT);
		csHour.setDataFormat(
			    createHelper.createDataFormat().getFormat("HH:MM"));
		
		csAcc.setDataFormat((short)4);
		
		csPerc.setDataFormat((short)10);
		
	//	csBottBord.setBorderBottom(CellStyle.BORDER_THIN);
		
		csUSD.setDataFormat(createHelper.createDataFormat().getFormat("#,#####0.00000"));
		
		myCellStyles.put("csDateRight", csDateRight);
		myCellStyles.put("csHour", csHour);
		myCellStyles.put("csAcc", csAcc);
		myCellStyles.put("csPerc", csPerc);
		myCellStyles.put("csUSD", csUSD);
		myCellStyles.put("csDef", csDef);
		
		if (underline) {
			for (Entry<String, CellStyle> cs : myCellStyles.entrySet()) {
				cs.getValue().setBorderBottom(CellStyle.BORDER_THIN);
			}
		}
		return myCellStyles;
		
		
	}
		
	public static void writeInExcel(Workbook wb, RowEntry rowEntry, int zeroRow) throws IOException {
		
		Sheet worksheet = wb.getSheet("Daily");
		
		//Read the spreadsheet that needs to be updated
		
		//Access the worksheet, so that we can update / modify it. 
		
		Boolean underline = rowEntry.getUnderline();
		Map<String, CellStyle> myCellStyles = createCellStyles(wb, underline);
		
		// Access the second cell in second row to update the value
//		int lastRow = worksheet.getLastRowNum();
//		int lastRow = worksheet.getPhysicalNumberOfRows();
		
//		int newRow = lastRow + 1;
		
		// get the last cell not null
		
		int newRow = zeroRow;
		for (Row row : worksheet) {
		    for (Cell cell : row) {
		        if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
		            if (cell.getCellType() != Cell.CELL_TYPE_STRING ||
		                cell.getStringCellValue().length() > 0) {
		            	newRow++;
		                break;
		            }
		        }
		    }
		}
		
		    
		// declare a Cell object
		int size = 11;
		List<Cell> cellList = new ArrayList<Cell>(size);
		Row lRow = worksheet.createRow(newRow);
		for (int i = 0; i < size; i++) {
			cellList.add(lRow.createCell(i));
			
		}
//		Cell cell_01 = null; 
//		
//		cell_01 = worksheet.getRow(newRow).getCell(0);   
//		cell_01.setCellValue(myDate.format(calendar.getTime()));
		// Get current cell value value and overwrite the value
		Calendar calendar = Calendar.getInstance();
		
		// Column A (0)
		SimpleDateFormat myDate = new SimpleDateFormat("d.M.yyyy");
//		System.out.println(myDate.format(calendar.getTime()));
		cellList.get(0).setCellStyle(myCellStyles.get("csDateRight"));
		cellList.get(0).setCellValue(myDate.format(calendar.getTime()));
		
		// Column B (1)
		SimpleDateFormat myTime = new SimpleDateFormat("HH:mm");
//		System.out.println(myTime.format(calendar.getTime()));
		cellList.get(1).setCellStyle(myCellStyles.get("csHour"));
		cellList.get(1).setCellValue(myTime.format(calendar.getTime()));
		
		// Column C (2)
		int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
//		System.out.println(ExampleDate.myDayOfWeek(dayOfWeek));
		cellList.get(2).setCellStyle(myCellStyles.get("csDef"));
		cellList.get(2).setCellValue(ExampleDate.myDayOfWeek(dayOfWeek));
		
		// Column D (3)
		cellList.get(3).setCellStyle(myCellStyles.get("csDef"));
		cellList.get(3).setCellValue(rowEntry.getIndex());
		
		// Column E (4)
//		cellList.get(4).setCellStyle(csAcc);
//		cellList.get(4).setCellType(Cell.CELL_TYPE_FORMULA);
//		cellList.get(4).setCellValue(rowEntry.getBuy());
		if ("XAU".equals(rowEntry.getBuy())) {
			cellList.get(4).setCellValue(rowEntry.getBuy());
		} else if (rowEntry.getBuy() != null) {
			if (!"Щатски долар".equals(rowEntry.getIndex())) {
				cellList.get(4).setCellStyle(myCellStyles.get("csAcc"));
			} else {
				cellList.get(4).setCellStyle(myCellStyles.get("csUSD"));
			}
			cellList.get(4).setCellValue(Double.parseDouble(rowEntry.getBuy()));
		} else {
			cellList.get(4).setCellStyle(myCellStyles.get("csDef"));
		}
		
		// Column F (5)
		if ("comp".equals(rowEntry.getMyIndF())) {
			
			cellList.get(5).setCellStyle(myCellStyles.get("csPerc"));
			cellList.get(5).setCellType(Cell.CELL_TYPE_FORMULA);
			// 1940 + ((10 - 3) + 1) - i
			int j = newRow + ((myEntries.size() - 3) + 1) - i;
			cellList.get(5).setCellFormula("E" + (newRow + 1) + "/$J" + j + "-1");
		} else {
			cellList.get(5).setCellStyle(myCellStyles.get("csDef"));
		}
		
		// Column G (6)
//		cellList.get(6).setCellType(Cell.CELL_TYPE_FORMULA);
		if (rowEntry.getSell() != null) {
			if (!"Щатски долар".equals(rowEntry.getIndex())) {
				cellList.get(6).setCellStyle(myCellStyles.get("csAcc"));
			} else {
				cellList.get(6).setCellStyle(myCellStyles.get("csUSD"));
			}
			cellList.get(6).setCellValue(Double.parseDouble(rowEntry.getSell()));
		} else {
			cellList.get(6).setCellStyle(myCellStyles.get("csDef"));
		}
		
		// Column H (7)
		if ("comp".equals(rowEntry.getMyIndH())) {
			cellList.get(7).setCellStyle(myCellStyles.get("csPerc"));
			cellList.get(7).setCellType(Cell.CELL_TYPE_FORMULA);
			int j = newRow + ((myEntries.size() - 3) + 1) - i;
			cellList.get(7).setCellFormula("G" + (newRow + 1) + "/$J" + j + "-1");
			i++;
		} else {
			cellList.get(7).setCellStyle(myCellStyles.get("csDef"));
		}
		
		// Column I (8)
		cellList.get(8).setCellStyle(myCellStyles.get("csDef"));
		if ("comp".equals(rowEntry.getMyIndI())) {
			cellList.get(8).setCellStyle(myCellStyles.get("csPerc"));
			cellList.get(8).setCellType(Cell.CELL_TYPE_FORMULA);
			cellList.get(8).setCellFormula("H" + (newRow + 1) + "-F" + (newRow + 1));
		} else {
			cellList.get(8).setCellValue(rowEntry.getMyIndI());
		}
		
		// Column J (9)
		cellList.get(9).setCellStyle(myCellStyles.get("csDef"));
		if (rowEntry.getDiff() == null) {
			cellList.get(9).setCellType(Cell.CELL_TYPE_FORMULA);
			cellList.get(9).setCellFormula("G" + (newRow + 1) + "-E" + (newRow + 1));
		} else {
			cellList.get(9).setCellValue(Double.parseDouble(rowEntry.getDiff()));
		}
		
		// Column K (10)
		cellList.get(10).setCellStyle(myCellStyles.get("csDef"));
		cellList.get(10).setCellType(Cell.CELL_TYPE_FORMULA);
		
		// Злато (в трой унции) lenght = 20
		if (rowEntry.getIndex().length() > 21) {
			cellList.get(10).setCellFormula("IF(G" + (newRow + 1) + "=G" + ((newRow + 1) - myEntries.size()) + ",\"Even\",IF(G" + (newRow + 1) + ">G" + ((newRow + 1) - myEntries.size()) + ",\"Up\",\"Down\"))");
		} else {
			cellList.get(10).setCellFormula("IF(J" + (newRow + 1) + "=J" + ((newRow + 1) - myEntries.size()) + ",\"Even\",IF(J" + (newRow + 1) + ">J" + ((newRow + 1) - myEntries.size()) + ",\"Up\",\"Down\"))");
		}
		
		
		/*
		// create 2 fonts objects
		Font f = wb.createFont();
		
		// Set font 1 to 12 point type, blue and bold
		f.setFontHeightInPoints((short) 12);
		f.setColor( HSSFColor.RED.index );
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		DataFormat df = wb.createDataFormat();
		CellStyle cs = wb.createCellStyle();
		// Set cell style and formatting
		cs.setFont(f);
		cs.setDataFormat(df.getFormat("#,##0.0"));
		
		*/
		
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
//		String path = "D:\\Tavex_01.xlsx";
		String path = "/home/cvetan/Downloads/Tavex.xlsx";
		
		List<String> myCoinsStrings = new ArrayList<>();
		myCoinsStrings.add("1 унция златен американски бизон");
		myCoinsStrings.add("1 унция американски орел");
		myCoinsStrings.add("30 грама златна китайска панда от 2017");
		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("1 унция златнo австралийско Кенгуру");
		myCoinsStrings.add("1 унция златен канадски кленов лист");
		
		int zeroRow;
		
		if (path.startsWith("/home/")) {
			zeroRow	 = 0;
		} else {
			zeroRow	 = 1;
		}
			
		File myFile = new File(path);
		FileInputStream fsIP = new FileInputStream(myFile);
				
		//Access the workbook                  
		Workbook wb = new XSSFWorkbook(fsIP);
		
		myEntries = InvestmentParser.getCoinsFromTavex(myCoinsStrings);
		RowEntry rowEtry_01 = InvestmentParser.getBGNUSD();
		RowEntry rowEtry_02 = InvestmentParser.getXAUBGN();
		RowEntry rowEtry_03 = InvestmentParser.getXAUUSD();
		RowEntry rowEtry_04 = InvestmentParser.getEthereumPrice();
		
		myEntries.add(rowEtry_01);
		myEntries.add(rowEtry_02);
		myEntries.add(rowEtry_03);
		myEntries.add(rowEtry_04);
		
		for (RowEntry rowEntry : myEntries) {
			writeInExcel(wb, rowEntry, zeroRow);
		}
		
		/*
		RowEntry rowEntry_01 = new RowEntry(
				"Канадски кленов лист 1 унция",
				"2,120.00",
				"2,279.00",
				null,
				null,
				true);
		
		RowEntry rowEntry_02 = new RowEntry(
				"Канадски кленов лист 1 унция",
				"2,120.00",
				"2,279.00",
				null,
				null,
				false);
		
		writeInExcel(wb, rowEntry_01, zeroRow);
		writeInExcel(wb, rowEntry_02, zeroRow);
		
		writeInExcel(wb, index, buy, sell, null, diff);
		writeInExcel(wb, index, "XAU","1.00", null, sell);
		writeInExcel(wb, index, null, null, "open", sell);
		writeInExcel(wb, index, null, null, null, sell);
		*/
		
		//Close the InputStream  
		fsIP.close();
		
		//Open FileOutputStream to write updates
		FileOutputStream output_file =new FileOutputStream(myFile);  
		
		//write changes
		wb.write(output_file);
		
		//close the stream
		output_file.close();
		
//		System.out.println();
		long endTime   = System.currentTimeMillis();
		System.err.println(InvestmentParser.duration(startTime, endTime));
		System.out.println("Done!!!");
	}

}
