package com.zhy_9.stoexpress.model;

public class ListDialogModel {

	/**
	 * 1 Ϊѡ�У�0 Ϊδѡ��
	 */
	private int isChosen;

	private String listContent;

	/**
	 * 1Ϊ��ʾ��0Ϊ����ʾ
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
