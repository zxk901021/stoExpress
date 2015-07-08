package com.zhy_9.stoexpress.model;
/**
 * ɨ������ʵ����
 * 
 * @author ZHY_9
 *
 */
public class ScanRecord {

	private String date;// ɨ�����ڣ������գ�

	private String expressId;// ��Ʒ��

	private String expressStatus;// ��Ʒ״̬

	private String courier;// ���Ա����

	private String time;// ɨ��ʱ�䣨ʱ���֣�

	private String problemType;// ���������

	private String scanType;// ɨ�������
	
	private int id;
	private String year;

	/**
	 * 0Ϊδ�ϴ���1Ϊ���ϴ�
	 */
	private String isUpload;// �Ƿ��ϴ�

	/**
	 * 0Ϊδѡ�У�1 Ϊѡ��
	 */
	private int isChosen;

	/**
	 * 0��ʾδɾ����1 Ϊɾ��
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
