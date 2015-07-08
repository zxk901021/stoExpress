package com.zhy_9.stoexpress.model;
/**
 * 员工信息实体类
 * @author ZHY_9
 *
 */
public class StoInfo {

	public String getBelongPost() {
		return belongPost;
	}

	public void setBelongPost(String belongPost) {
		this.belongPost = belongPost;
	}

	private String courierId;//EMPLOYEENO
	private String courierName;//EMPLOYEE
	private String courierPhone;
	private String equipId;
	private String companyName;//BELONGSITE
	private String staffNumber;//员工编号
	private String branchNumber;//网点编号  AREACODE
	private String password;//登陆密码
	private String lastUpdate;//最后更新时间
	private String operflag;//flag标识
	private String belongPost;//身份

	public String getOperflag() {
		return operflag;
	}

	public void setOperflag(String operflag) {
		this.operflag = operflag;
	}

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

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
}
