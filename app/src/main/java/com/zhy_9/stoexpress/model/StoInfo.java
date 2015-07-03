package com.zhy_9.stoexpress.model;

public class StoInfo {

	private String courierId;
	private String courierName;
	private String courierPhone;
	private String equipId;
	private String companyName;

	public StoInfo() {

	}

	public StoInfo(String courierId, String courierName, String courierPhone,
			String equipId, String companyName) {
		super();
		this.courierId = courierId;
		this.courierName = courierName;
		this.courierPhone = courierPhone;
		this.equipId = equipId;
		this.companyName = companyName;
	}

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierPhone() {
		return courierPhone;
	}

	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}

	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
