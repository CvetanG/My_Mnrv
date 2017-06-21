package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

public class RunMe {

	public static void main(String[] args) throws IOException, DbxApiException, DbxException {
		
		System.out.println("Start Program");
		long startTime = System.currentTimeMillis();
		
//		String path = "D:\\Tavex_01.xlsx";
//		String path = "/home/cvetan/Downloads/Tavex.xlsx";
		
		List<String> myCoinsStrings = new ArrayList<>();
		myCoinsStrings.add("1 унция златен американски бизон");
		myCoinsStrings.add("1 унция американски орел");
		myCoinsStrings.add("30 грама златна китайска панда от 2017");
		myCoinsStrings.add("1 унция златна австрийска филхармония");
		myCoinsStrings.add("1 унция златнo Австралийско Кенгуру");
		myCoinsStrings.add("1 унция златен канадски кленов лист");
		
//		int zeroRow = 0;
		
		/*
		
		if (path.startsWith("/home/")) {
			zeroRow	 = 0;
		} else {
			zeroRow	 = 1;
		}
		
		*/
		
		String localPath = "Tavex.xlsx";
		
		String dropboxPath = "/Finance/" + localPath;
		
    	String argAuthFileOutput = "authFile.app";
    	
    	MyDropbox myDropbox = new MyDropbox();
        
        DbxAuthInfo authInfo = myDropbox.createAuth(argAuthFileOutput);
        
        DbxClientV2 client = myDropbox.createClient(authInfo);
        
        File localFile = myDropbox.getFile(client, localPath, dropboxPath);
        
//		File myFile = new File(path);
		FileInputStream fsIP = new FileInputStream(localFile);
				
		//Access the workbook                  
		Workbook wb = new XSSFWorkbook(fsIP);
		
		List<RowEntry> myEntries = new ArrayList<RowEntry>();
		InvestmentParser myParser = new InvestmentParser();
		
		myEntries = myParser.getCoinsFromTavex(myCoinsStrings);
		RowEntry rowEtry_01 = myParser.getBGNUSD();
		RowEntry rowEtry_02 = myParser.getXAUBGN();
		RowEntry rowEtry_03 = myParser.getXAUUSD();
		RowEntry rowEtry_04 = myParser.getEthereumPrice();
		
		System.out.println("Done Parsing Websites!!!");
		
		myEntries.add(rowEtry_01);
		myEntries.add(rowEtry_02);
		myEntries.add(rowEtry_03);
		myEntries.add(rowEtry_04);
		
//		int zeroRow = myEntries.size();
		
		WorkPOI myPOI = new WorkPOI();
		
		myPOI.writeInExcel(wb, myEntries);
		
		System.out.println("Done Inserting Rows in Excel File!!!");
		
		//Close the InputStream  
		fsIP.close();
		
		//Open FileOutputStream to write updates
		FileOutputStream output_file =new FileOutputStream(localFile);  
		
		//write changes
		wb.write(output_file);
		
		//close the stream
		output_file.close();
		
		
		myDropbox.uploadFile(client, localFile, dropboxPath);
        
        long endTime   = System.currentTimeMillis();
		System.err.println(myParser.duration(startTime, endTime));
		System.out.println("All Done!!!");

	}

}
