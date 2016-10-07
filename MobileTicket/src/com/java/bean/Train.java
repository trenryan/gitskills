package com.java.bean;

import java.io.Serializable;

public class Train implements Serializable{
	private int id;
	private String num,start_date,start_time,from_city,to_city;
	private String from_mycity,to_mycity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getFrom_mycity() {
		return from_mycity;
	}
	public void setFrom_mycity(String from_mycity) {
		this.from_mycity = from_mycity;
	}
	public String getTo_mycity() {
		return to_mycity;
	}
	public void setTo_mycity(String to_mycity) {
		this.to_mycity = to_mycity;
	}
	

}
