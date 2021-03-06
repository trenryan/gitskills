package com.java.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateTime {
    public static String getDate(){
    	Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    public static int getYear(){
    	Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
    	String year= sdf.format(date);
    	return Integer.parseInt(year);
    }
    public static int getMonth(){
    	Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("MM");
    	String month= sdf.format(date);
    	return Integer.parseInt(month)-1;
    }
    public static int getDay(){
    	Date date=new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("dd");
    	String day= sdf.format(date);
    	return Integer.parseInt(day);
    }
    
    public static void main(String[] args){
    	System.out.println(MyDateTime.getDate());
    }
}
