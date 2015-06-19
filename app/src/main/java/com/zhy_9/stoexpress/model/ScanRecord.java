package com.zhy_9.stoexpress.model;

public class ScanRecord {

	private String date;//扫描日期（年月日）
	
	private String expressId;//货品码
	
	private String expressStatus;//货品状态
	
	private String courier;//快递员姓名
	
	private String time;//扫描时间（时、分）
	
	/**
	 * 0为未选中，1 为选中
	 */
	private int isChosen;
	
	/**
	 * 0表示未删除，1 为删除
	 */
	private int delete;
	
	public ScanRecord(){
		
	}
	
	

	public ScanRecord(String expressId, String expressStatus, String courier) {
		super();
		this.expressId = expressId;
		this.expressStatus = expressStatus;
		this.courier = courier;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}

	public String getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(String expressStatus) {
		this.expressStatus = expressStatus;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}



	public int getIsChosen() {
		return isChosen;
	}



	public void setIsChosen(int isChosen) {
		this.isChosen = isChosen;
	}



	public int getDelete() {
		return delete;
	}



	public void setDelete(int delete) {
		this.delete = delete;
	}
	
	
	
}
