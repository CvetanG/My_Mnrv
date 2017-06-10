package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkPOI {

	public static void main(String[] args) throws IOException {
		//Read the spreadsheet that needs to be updated
		String path = "D:\\Tavex.xlsx";
		File myFile = new File(path);
		FileInputStream fsIP= new FileInputStream(myFile);
		
		//Access the workbook                  
		Workbook wb = new XSSFWorkbook(fsIP);
		//Access the worksheet, so that we can update / modify it. 
		Sheet worksheet = wb.getSheet("Daily");
		
		// declare a Cell object
		Cell cell = null; 
		// Access the second cell in second row to update the value
		int lastRow = worksheet.getLastRowNum();
		int newRow = lastRow ++;
		cell = worksheet.getRow(newRow).getCell(0);   
		// Get current cell value value and overwrite the value
		Date date = new Date();
				
//		cell.setCellValue(date.toString());
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

}
