package com.zhy_9.stoexpress.model;
/**
 * 扫描数据实体类
 * 
 * @author ZHY_9
 *
 */
public class ScanRecord {

	private String date;// 扫描日期（年月日）

	private String expressId;// 货品码

	private String expressStatus;// 货品状态

	private String courier;// 快递员姓名

	private String time;// 扫描时间（时、分）

	private String problemType;// 问题件类型

	private String scanType;// 扫描件类型
	
	private int id;
	private String year;

	/**
	 * 0为未上传，1为已上传
	 */
	private String isUpload;// 是否上传

	/**
	 * 0为未选中，1 为选中
	 */
	private int isChosen;

	/**
	 * 0表示未删除，1 为删除
	 */
	private int delete;

	public ScanRecord() {

	}

	public ScanRecord(String expressId, String expressStatus, String courier,
			String date, String time, String isUpload, int id) {
		super();
		this.expressId = expressId;
		this.expressStatus = expressStatus;
		this.courier = courier;
		this.date = date;
		this.time = time;
		this.isUpload = isUpload;
		this.id = id;
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

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
	
}
