package util;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;


public class Util {
	public Util(){
		
	}
	public static String dateToString(LocalDate localdate) {

		//		String pattern = "yyyy-MM-dd HH:mm:ss";
//	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//	    String d = formatter.format(date);
//		return d;
	//	 LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		  String text = localdate.format(formatter);
		  System.out.println(text);
		  return text;
	}
	
//	public Date stringToDate(String stringDate) {
//		String pattern = "yyyy-MM-dd HH:mm:ss";
//	    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//	    Date d = null;
//		try {
//			d = formatter.parse(stringDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return d;
//	}
//	
//	public long substractDate(Date d1, Date d2) {
//		long diff = Math.abs(d1.getTime() - d2.getTime());
//    	long diffDays = diff / (24 * 60 * 60 * 1000);
//    	return diffDays;
//	}
}
