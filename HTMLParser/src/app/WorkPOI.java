package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkPOI {
	
	public static void writeInExcel() throws IOException {
		
		//Read the spreadsheet that needs to be updated
		String path = "D:\\Tavex.xlsx";
		File myFile = new File(path);
		FileInputStream fsIP = new FileInputStream(myFile);
				
		//Access the workbook                  
		Workbook wb = new XSSFWorkbook(fsIP);
		//Access the worksheet, so that we can update / modify it. 
		Sheet worksheet = wb.getSheet("Daily");
		
		CellStyle cs = wb.createCellStyle();
		CellStyle csAcc = wb.createCellStyle();
		CellStyle csPerc = wb.createCellStyle();
		CellStyle csBottBord = wb.createCellStyle();
		CellStyle csUSD = wb.createCellStyle();
		DataFormat df = wb.createDataFormat();
		CreationHelper createHelper = wb.getCreationHelper();
		
		csUSD.setDataFormat(createHelper.createDataFormat().getFormat("#,#####0.00000"));
		

		cs.setAlignment(CellStyle.ALIGN_RIGHT);
//		cs.setDataFormat(
//			    createHelper.createDataFormat().getFormat("d.m.yyyy"));
		cs.setDataFormat((short)14);
		
		csAcc.setDataFormat((short)4);
		csPerc.setDataFormat((short)10);
		csBottBord.setBorderBottom(CellStyle.BORDER_THIN);
		
		// Access the second cell in second row to update the value
//		int lastRow = worksheet.getLastRowNum();
		int lastRow = worksheet.getPhysicalNumberOfRows();
		
		int newRow = lastRow + 1;
//		worksheet.shiftRows(2, newRow, 1, true, true);
				
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
		
		// Column A
		SimpleDateFormat myDate = new SimpleDateFormat("d.M.yyyy");
		System.out.println(myDate.format(calendar.getTime()));
		cellList.get(0).setCellStyle(cs);
		cellList.get(0).setCellValue(myDate.format(calendar.getTime()));
		
		// Column B
		SimpleDateFormat myTime = new SimpleDateFormat("HH:mm");
		System.out.println(myTime.format(calendar.getTime()));
		cellList.get(1).setCellValue(myTime.format(calendar.getTime()));
		
		// Column C
		int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(ExampleDate.myDayOfWeek(dayOfWeek));
		cellList.get(2).setCellValue(ExampleDate.myDayOfWeek(dayOfWeek));
		
		// Column D (3)
		cellList.get(3).setCellValue("Канадски кленов лист 1 унция");
		
		// Column E (4)
//		cellList.get(4).setCellStyle(csAcc);
		cellList.get(4).setCellStyle(csUSD);
		cellList.get(4).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(4).setCellFormula("$E$1934");
		
		// Column F (5)
		cellList.get(5).setCellStyle(csPerc);
		cellList.get(5).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(5).setCellFormula("E1932/$J1935-1");
		
		// Column G (6)
		cellList.get(6).setCellStyle(csAcc);
		cellList.get(6).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(6).setCellFormula("$G$1933");
		
		// Column H (7)
		cellList.get(7).setCellStyle(csPerc);
		cellList.get(7).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(7).setCellFormula("$G$1932/$J$1935-1");
		
		// Column I (8)
		cellList.get(8).setCellStyle(csPerc);
		cellList.get(8).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(8).setCellFormula("H1932-F1932");
		
		// Column J (9)
		cellList.get(9).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(9).setCellFormula("G1932-E1932");
		
		// Column K (10)
		cellList.get(10).setCellType(Cell.CELL_TYPE_FORMULA);
		cellList.get(10).setCellFormula("IF(G1932=G1923,\"Even\",IF(G1932>G1923,\"Up\",\"Down\"))");
		
		//Close the InputStream  
		fsIP.close(); 
		
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
		
		//Open FileOutputStream to write updates
		FileOutputStream output_file =new FileOutputStream(myFile);  
		 //write changes
		wb.write(output_file);
		//close the stream
		output_file.close();
		
		System.out.println("Done!!!");
	}
	
	public static void main(String[] args) throws IOException {
		writeInExcel();
	}

}
