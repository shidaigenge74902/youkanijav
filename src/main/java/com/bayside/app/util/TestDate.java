package com.bayside.app.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class TestDate {
	private static final Logger log = Logger.getLogger(TestDate.class);
	/*//获取当前月第一天：
	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
   Calendar c = Calendar.getInstance();    
   c.add(Calendar.MONTH, 0);
   c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
   String first = format.format(c.getTime());
   System.out.println("===============first:"+first);*/
   
   /*//获取当前月最后一天
   Calendar ca = Calendar.getInstance();    
   ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
   String last = format.format(ca.getTime());*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("当前时间："+ new Date().toLocaleString());
		System.out.println("当天0点时间："+ getTimesmorning().toLocaleString());
		System.out.println("当天24点时间："+ getTimesnight().toLocaleString());
		System.out.println("本周周一0点时间："+ getTimesWeekmorning().toLocaleString());
		System.out.println("本周周日24点时间："+ getTimesWeeknight().toLocaleString());
		System.out.println("本月初0点时间："+ getTimesMonthmorning().toLocaleString());
		System.out.println("本月未24点时间："+ getTimesMonthnight().toLocaleString());
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        try {
				Date d1=sdf.parse("2012-09-08 10:10:10");
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
				e.printStackTrace();
			}  
	        try {
				Date d2=sdf.parse("2012-09-15 00:00:00");
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
				e.printStackTrace();
			}  
	     //   System.out.println(daysBetween(d1,d2));  
	  
	      //  System.out.println(daysBetween("2012-09-08 10:10:10","2012-09-15 00:00:00")); 
		}
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)   
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}  
        try {
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
/** 
*字符串的日期格式的计算 
*/  
    public static int daysBetween(String smdate,String bdate){  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        try {
			cal.setTime(sdf.parse(smdate));
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}    
        long time1 = cal.getTimeInMillis();                 
        try {
			cal.setTime(sdf.parse(bdate));
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
  
		// 获得当天0点时间
		public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
		}

		// 获得当天24点时间
		public static Date getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
		}

		// 获得本周一0点时间
		public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
		}

		// 获得本周日24点时间
		public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
		}

		// 获得本月第一天0点时间
		public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
		}
		// 获得本月最后一天24点时间
		public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
		}
		//获取当前月第一天：
		public static String getMonthFirstDay(){
			//获取当前月第一天：
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		   Calendar c = Calendar.getInstance();    
		   c.add(Calendar.MONTH, 0);
		   c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		   String first = format.format(c.getTime());
		   System.out.println("===============first:"+first);
			return first;
		}
		//获取当前月最后一天：
		public static String getMonthEndDay(){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			 //获取当前月最后一天
			   Calendar ca = Calendar.getInstance();    
			   ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
			   String last = format.format(ca.getTime());
			   return last;
		}
	    //获得本年有多少天  
	    private int getMaxYear(){  
	        Calendar cd = Calendar.getInstance();  
	        cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天  
	        cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。  
	        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);   
	        return MaxYear;  
	    }  
	      
	    private static int getYearPlus(){  
	        Calendar cd = Calendar.getInstance();  
	        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天  
	        cd.set(Calendar.DAY_OF_YEAR,1);//把日期设为当年第一天  
	        cd.roll(Calendar.DAY_OF_YEAR,-1);//把日期回滚一天。  
	        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);  
	        if(yearOfNumber == 1){  
	            return -MaxYear;  
	        }else{  
	            return 1-yearOfNumber;  
	        }  
	    }  
	  //获得本年第一天的日期  
	    public static String getCurrentYearFirst(){  
	        int yearPlus = getYearPlus();  
	        GregorianCalendar currentDate = new GregorianCalendar();  
	        currentDate.add(GregorianCalendar.DATE,yearPlus);  
	        Date yearDay = currentDate.getTime();  
	        DateFormat df = DateFormat.getDateInstance();  
	        String preYearDay = df.format(yearDay);  
	        return preYearDay;  
	    }  
	      
	      
	   //获得本年最后一天的日期 *  
	    public static String getCurrentYearEnd(){  
	        Date date = new Date();  
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式     
	        String years = dateFormat.format(date);     
	        return years+"-12-31";  
	    }  
	    //获得当前时间的月份，月份从0开始所以结果要加1
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
        //获得本季度
	    public static String getThisSeasonTime(int month){  
	        int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};  
	        int season = 1;  
	        if(month>=1&&month<=3){  
	            season = 1;  
	        }  
	        if(month>=4&&month<=6){  
	            season = 2;  
	        }  
	        if(month>=7&&month<=9){  
	            season = 3;  
	        }  
	        if(month>=10&&month<=12){  
	            season = 4;  
	        }  
	        int start_month = array[season-1][0];  
	        int end_month = array[season-1][2];  
	          
	        Date date = new Date();  
	        SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy");//可以方便地修改日期格式     
	        String  years  = dateFormat.format(date);     
	        int years_value = Integer.parseInt(years);  
	          
	        int start_days =01;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);  
	        int end_days = getLastDayOfMonth(years_value,end_month);  
	        String seasonDate = years_value+"-"+start_month+"-"+start_days+";"+years_value+"-"+end_month+"-"+end_days;  
	        return seasonDate;  
	          
	    }  
	    /** 
	     * 获取某年某月的最后一天 
	     * @param year 年 
	     * @param month 月 
	     * @return 最后一天 
	     */  
	   private static int getLastDayOfMonth(int year, int month) {  
	         if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
	                   || month == 10 || month == 12) {  
	               return 31;  
	         }  
	         if (month == 4 || month == 6 || month == 9 || month == 11) {  
	               return 30;  
	         }  
	         if (month == 2) {  
	               if (isLeapYear(year)) {  
	                   return 29;  
	               } else {  
	                   return 28;  
	               }  
	         }  
	         return 0;  
	   }  
	   /** 
	    * 是否闰年 
	    * @param year 年 
	    * @return  
	    */  
	  public static boolean isLeapYear(int year) {  
	        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);  
	  }  
}
