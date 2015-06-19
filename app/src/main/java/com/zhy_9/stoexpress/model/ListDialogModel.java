package com.zhy_9.stoexpress.model;

public class ListDialogModel {

	/**
	 * 1 为选中，0 为未选中
	 */
	private int isChosen;

	private String listContent;

	/**
	 * 1为显示，0为不显示
	 */
	private int isVisiable;

	public ListDialogModel() {
		super();
	}

	public int getIsChosen() {
		return isChosen;
	}

	public void setIsChosen(int isChosen) {
		this.isChosen = isChosen;
	}

	public String getListContent() {
		return listContent;
	}

	public void setListContent(String listContent) {
		this.listContent = listContent;
	}

	public int getIsVisiable() {
		return isVisiable;
	}

	public void setIsVisiable(int isVisiable) {
		this.isVisiable = isVisiable;
	}

}
