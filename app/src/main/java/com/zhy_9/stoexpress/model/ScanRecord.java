package com.zhy_9.stoexpress.model;

public class ScanRecord {

	private String date;//ɨ�����ڣ������գ�
	
	private String expressId;//��Ʒ��
	
	private String expressStatus;//��Ʒ״̬
	
	private String courier;//���Ա����
	
	private String time;//ɨ��ʱ�䣨ʱ���֣�
	
	/**
	 * 0Ϊδѡ�У�1 Ϊѡ��
	 */
	private int isChosen;
	
	/**
	 * 0��ʾδɾ����1 Ϊɾ��
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
