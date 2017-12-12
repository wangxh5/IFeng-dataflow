package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CalTools {
	public static String getDate(long timeStamp) {
		String date = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date(timeStamp));
		return date;
	}

	public static String getRecommendDate(long timeStamp) {
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timeStamp));
		return date;
	}

	public static long getTimeStamp(String date) throws ParseException {
		Date epoch = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(date);
		long timeStamp = epoch.getTime() / 1000;
		return timeStamp;
	}
	
	public static long getTimeStamp2(String date) throws ParseException {
		Date epoch = new java.text.SimpleDateFormat("yyyyMMdd").parse(date);
		long timeStamp = epoch.getTime() / 1000;
		return timeStamp;
	}

	public static long getRecommendTimeStamp(String date) throws ParseException {
		Date epoch = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		long timeStamp = epoch.getTime() / 1000;
		return timeStamp;
	}

	public static String getFormatDate(long timeStamp) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp));
		return date;
	}

	public static void main(String[] args) throws ParseException {
		// CalTools.getTimeStamp("20161023 23:00:00");
		// System.out.println(System.currentTimeMillis());
		// System.out.println(CalTools.getDate(getTimeStamp("20161020
		// 23:00:00")*1000));
		// System.out.println(CalTools.getDate(1476975600 * 1000L));
		// long terminalTime = 0;
		// try {
		// terminalTime =
		// CalTools.getTimeStamp(CalTools.getDate(System.currentTimeMillis() -
		// 24 * 60 * 60 * 1000)+" 23:00:00");
		// } catch (ParseException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }SD

		String date = "20170117 11:11:11";
		date = CalTools.getDate(CalTools.getTimeStamp(date) * 1000 - 24 * 60 * 60 * 1000);
		// long sTime = terminalTime - 24 * 60 * 60 * 3;
		// System.out.println(terminalTime);
		System.out.println(date);

	}
}
