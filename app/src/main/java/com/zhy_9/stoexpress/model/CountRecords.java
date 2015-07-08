package com.zhy_9.stoexpress.model;

import java.util.List;

public class CountRecords {
	private String year;
	
	private List<RecordCounts> list;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	public List<RecordCounts> getList() {
		return list;
	}

	public void setList(List<RecordCounts> list) {
		this.list = list;
	}

	public CountRecords() {
		super();
	}

}
