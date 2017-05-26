package app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringFormat {
	public static void main(String[] args) throws ParseException {
		
		String myDate = "02/02/2016 03:50";
		
		DateFormat oldFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		DateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = oldFormat.parse(myDate);
		
		System.out.println(newFormat.format(date));
		System.out.println(date);
		
		
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//		cal.setTime(sdf.parse(myDate));
//		System.out.println(cal);
		
	}
	
}
