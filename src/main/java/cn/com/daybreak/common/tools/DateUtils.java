package cn.com.daybreak.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 */
public class DateUtils {
	
	/**
	 * 将日期字符串转化为需要格式的日期
	 * @param date 日期字符串 
	 * @param format 格式字符串
	 * @return	转换后的日期对象
	 */
	public static Date strToFormatDate(String date, String format){
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date, new ParsePosition(0));
	}
	
	/**
	 * 将字符串转换为yyyy-MM-dd格式的日期
	 * @param date
	 * @return	转换后的日期对象
	 */
	public static Date strToDate(String date) {
		return DateUtils.strToFormatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 将字符串转换为yyyy-MM-dd HH:mm:ss格式的日期
	 * @param date
	 * @return	转换后的日期对象
	 */
	public static Date strToDateTime(String date) {
		return DateUtils.strToFormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将date型日期转换成特定格式的时间字符串
	 * @param date
	 * @param format
	 * @return	转换后的日期对象
	 */
	public static String dateToFormatStr(Date date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 将date型日期转换成yyyy-MM-dd HH:mm:ss格式的时间字符串
	 * @param date 日期
	 * @return 返回yyyy-MM-dd HH:mm格式的时间字符串
	 */
	public static String dateTimeToStr(Date date) {
		return DateUtils.dateToFormatStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将date型日期转换成yyyy-MM-dd格式的日期字符串
	 * @param date 日期
	 * @return 返回yyyy-MM-dd格式的日期字符串
	 */
	public static String dateToStr(Date date) {
		return  DateUtils.dateToFormatStr(date, "yyyy-MM-dd");
	}
	
	/**
	 * 计算出date day天之前或之后的日期
	 * @param date	日期
	 * @param days	int	天数，正数为向后几天，负数为向前几天
	 * @return	返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterDays(Date date, int days) {   
		Calendar now = Calendar.getInstance();   
		now.setTime(date);   
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);   
		return now.getTime();   
	}   
	/**
	 * 计算出date monthes月之前或之后的日期
	 * @param date 日期
	 * @param monthes 月数，正数为向后几月，负数为向前几月
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterMonthes(Date date, int monthes) {   
		Calendar now = Calendar.getInstance();   
		now.setTime(date);   
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + monthes);   
		return now.getTime();   
	} 
	
	/**
	 * 计算出date day天之前或之后的日期
	 * @param date 日期
	 * @param hours int	小时数，正数为向后几小时，负数为向前几小时
	 * @return	返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterHours(Date date, int hours) {   
		Calendar now = Calendar.getInstance();   
		now.setTime(date);   
		now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hours);   
		return now.getTime();   
	}
	/**
	 * 计算出date years年之前或之后的日期
	 * @param date 日期
	 * @param years 年数，正数为向后几年，负数为向前几年
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterYears(Date date, int years) {   
        Calendar now = Calendar.getInstance();   
        now.setTime(date);   
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) + years);   
        return now.getTime();   
    }   
	
	/**
	 * 计算出date minutes分钟之后的时间
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date getDateBeforeOrAfterMinutes(Date date, int minutes) {   
        Calendar now = Calendar.getInstance();   
        now.setTime(date);   
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minutes);   
        return now.getTime();   
    }   

	/**  
     * 计算两个日期之间的天数
     * @param beginDate  
     * @param endDate  
     * @return  如果beginDate 在 endDate之后返回负数 ，反之返回正数
     */  
    public static int daysOfTwoDate(Date beginDate,Date endDate){   
       
        Calendar beginCalendar = Calendar.getInstance();   
        Calendar endCalendar = Calendar.getInstance();   
          
        beginCalendar.setTime(beginDate);   
        endCalendar.setTime(endDate);   

        return daysOfTwoDate(beginCalendar,endCalendar);	
      
    } 
    
	/**  
     * 计算两个日期之间的天数
     * @param d1  
     * @param d2  
     * @return  如果d1 在 d2 之后返回负数 ，反之返回正数
     */ 
    public static int daysOfTwoDate(Calendar d1, Calendar d2) {
    	int days = 0;
    	int years = d1.get(Calendar.YEAR) - d2.get(Calendar.YEAR);
    	if(years == 0){//同一年中
    		 days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
    		 return days;
    	}else if(years > 0){//不同一年
		 	 for(int i=0; i<years ;i++){
		 		d2.add(Calendar.YEAR, 1);
		 		days += -d2.getActualMaximum(Calendar.DAY_OF_YEAR);
		 		if(d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)){
		 			days += d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		 			return days;
		 		}
		 	}
    	}else 
    		for(int i=0; i<-years ;i++){
	 		d1.add(Calendar.YEAR, 1);
	 		days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
	 		if(d1.get(Calendar.YEAR) == d2.get(Calendar.YEAR)){
	 			days += d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
	 			return days;
	 		}
    	}
    	return days;
    }

    
    /**
     * 获得当前时间当天的开始时间，即当前给出的时间那一天的00:00:00.0的时间
     * @param date 当前给出的时间
     * @return	当前给出的时间那一天的00:00:00.0的时间的日期对象
     */
    public static Date getDateBegin(Date date){
    	if(date!=null) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
        	cal.set(Calendar.HOUR_OF_DAY, 0);
        	cal.set(Calendar.MINUTE, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MILLISECOND, 0);
			return  cal.getTime(); 		
    	}

		return null;
    }
    /**
     * 获得当前时间当天的结束时间，即当前给出的时间那一天的23:59:59的时间
     * @param date 当前给出的时间
     * @return	当前给出的时间那一天的23:59:59的时间的日期对象
     */
    public static Date getDateEnd(Date date){
    	SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    	if(date!=null){
        	try {
        		date = getDateBeforeOrAfterDays(date,1);
    			date = DateFormat.getDateInstance(DateFormat.DEFAULT,Locale.CHINA).parse(ymdFormat.format(date));
    			Date endDate = new Date();
    			endDate.setTime(date.getTime()-1000);
    			return endDate;
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}    		
    	}
		return null;
    }
    
    /**
     * 获取时间的年份
     * @param date
     * @return
     */
    public static int getDateYear(java.util.Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.YEAR);
    }
    
    /**
     * 获取时间的月份
     * @param date
     * @return
     */
    public static int getDateMonth(java.util.Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 获取月份字符串 
     * @param date
     * @return
     */
    public static String getDateMonthStr(java.util.Date date){
    	int month = DateUtils.getDateMonth(date);
    	if(month < 10){
    		return "0" + month;
    	}else{
    		return Integer.toString(month);
    	}
    }
    
    /**
     * 获取时间日期
     * @param date
     * @return
     */
    public static int getDateDate(java.util.Date date){
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal.get(Calendar.DATE);
    }
    
    /**
     * 获取时间日期字符串
     * @param date
     * @return
     */
    public static String getDateDateStr(java.util.Date date){
    	int d = DateUtils.getDateDate(date);
    	if(d < 10){
    		return "0" + d;
    	}else{
    		return Integer.toString(d);
    	}
    }
    
    /**
     * 获取日期月份的第一天
     * yyyy-MM-dd 00:00:00。0
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(java.util.Date date) {
    	if(date!=null) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
        	cal.set(Calendar.DAY_OF_MONTH, 1);
        	cal.set(Calendar.HOUR_OF_DAY, 0);
        	cal.set(Calendar.MINUTE, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MILLISECOND, 0);
			return  cal.getTime(); 		
    	}

		return null;
    }
    
    /**
     * 获取日期月份的最后一天
     * yyyy-MM-dd 00:00:00.0
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(java.util.Date date) {
    	if(date!=null) {
    		Calendar cal = Calendar.getInstance();
        	cal.setTime(date);
        	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
        	cal.set(Calendar.DAY_OF_MONTH, 0);
        	cal.set(Calendar.HOUR_OF_DAY, 0);
        	cal.set(Calendar.MINUTE, 0);
        	cal.set(Calendar.SECOND, 0);
        	cal.set(Calendar.MILLISECOND, 0);
			return  cal.getTime(); 		
    	}

		return null;
    }
    
    /**
     * 获取日期的下一天
     * @param date
     * @return
     */
    public static Date getNextDate(java.util.Date date) {
    	if(date!=null) {
    		Calendar cal = Calendar.getInstance();
        	cal.setTime(date);
        	cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
			return  cal.getTime(); 		
    	}

		return null;
    }
    
    /**
     * 获取日期的前一天
     * @param date
     * @return
     */
    public static Date getPrevDate(java.util.Date date) {
    	if(date!=null) {
    		Calendar cal = Calendar.getInstance();
        	cal.setTime(date);
        	cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
			return  cal.getTime(); 		
    	}

		return null;
    }
    
    public static void main(String args[])
	{
//		System.out.println(DateUtils.getTodayDateStr());
//		System.out.println(DateUtils.getTodayDateTimeStr());
//		System.out.println(DateUtils.getYear());
//		System.out.println(DateUtils.getTodayDateTime());
//    	System.out.println(DateUtils.getTodayDateTime().getTime());
//    	System.out.println(DateUtils.getDateBeforeOrAfterHours(new Date(),1));
    	System.out.println(DateUtils.getFirstDateOfMonth(new Date()));
    	//System.out.println(DateUtils.getDateMonth(new Date()));
    	//System.out.println(DateUtils.getDateDate(new Date()));
	}
}
