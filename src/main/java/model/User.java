package model;

import java.util.ArrayList;


/**
 *	ユーザの情報を保有するクラス 
 */

public class User{
	
	//	ユーザ登録時に必要な情報
	
	/**	ユーザＩＤ*/
	private String id;			
	
	/**	ログインパスワード*/
	private String pass;
	
	/**	店舗リスト*/
	private ArrayList<StoreList> list;
	/**	年齢*/
	private int age;
	/**	住所（都道府県）*/
	private String address;
	
	/**	ログインを要しない（リスト検索など）場合のコンストラクタ*/
	public User(String id) {
		this.id = id;
		list = new ArrayList<>();
	}
	
	/**	ユーザログイン用のコンストラクタ*/
	public User(String id,String pass) {
		this.id = id;
		this.pass = pass;
		list = new ArrayList<>();
	}

	/**	ユーザIDのゲッター*/
	public String getId() {
		return id;
	}

	/**	パスワードのゲッター*/
	public String getPass() {
		return pass;
	}

	/**	ユーザの作成しているリストのゲッター*/
	public ArrayList<StoreList> getList() {
		return list;
	}

	/**	ユーザのリストのセッター*/
	public void setList(ArrayList<StoreList> list) {
		this.list = list;
	}

	/**	住所（都道府県）のゲッター*/
	public String getAddress() {
		return address;
	}

	/**	住所（都道府県）のセッター*/
	public void setAddress(String address) {
		this.address = address;
	}

	/**	年齢のゲッター*/
	public int getAge() {
		return age;
	}

	/**	年齢のセッター*/
	public void setAge(int age) {
		this.age = age;
	}
	
	/**	リストの名前からそのリスト番号を返す*/
	public int getListNum(String listName) {
		int listNum = -1;
		for(int i = 0;i < list.size();i++) {
			if(list.get(i).getListName().equals(listName)) {
				listNum = i ;
			}
		}
		return listNum;
	}

}
