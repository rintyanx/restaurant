package model;

import java.util.ArrayList;

/**
 *	ユーザが作成した店舗リストのに関するクラス
 */

public class StoreList {
	
	/**	リストを管理しているユーザID*/
	private String id;
	
	/**	店舗を格納するリスト*/
	private ArrayList<Store> list;
	
	/**	リストの名前*/
	private String listName;
	
	/**	リストID*/
	private String listID;
	
	/**	コンストラクタ*/
	public StoreList(String id,String listName,String listID) {
		this.id = id;
		this.listName = listName;
		this.listID = listID;
		list = new ArrayList<>();
	}
	
	/**	店舗を格納しているリストのゲッター*/
	public ArrayList<Store> getList() {
		return list;
	}

	/**	店舗を格納しているリストのセッター*/
	public void setList(ArrayList<Store> list) {
		this.list = list;
	}

	/**	リスト名のゲッター*/
	public String getListName() {
		return listName;
	}

	/**	リスト名のセッター*/
	public void setListName(String listName) {
		this.listName = listName;
	}

	/**	リストIDのゲッター*/
	public String getListID() {
		return listID;
	}

	/**	リスト管理者のIDのゲッター*/
	public String getId() {
		return id;
	}
	
}
