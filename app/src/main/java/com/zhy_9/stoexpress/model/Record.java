package com.zhy_9.stoexpress.model;

import java.util.List;

public class Record {

	private String date;
	private List<ScanRecord> info;

	public Record() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ScanRecord> getInfo() {
		return info;
	}

	public void setInfo(List<ScanRecord> info) {
		this.info = info;
	}

}
