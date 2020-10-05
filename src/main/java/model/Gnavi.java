package model;

import java.util.ArrayList;

/**
 *	ぐるなびＡＰＩから受け取った情報を格納するクラス
 */

public class Gnavi {
	/**	検索した名前のリスト*/
	private ArrayList<String> nameList;

	/**	検索したURLリスト*/
	private ArrayList<String> urlList;

	/**	検索ヒット数*/
	private int total_hit_count;

	/**	路線名のリスト*/
	private ArrayList<String> lineList;

	/**	駅名のリスト*/
	private ArrayList<String> stationList;

	/**	かかる時間*/
	private ArrayList<String> timeList;

	/**	コンストラクタ*/
	public Gnavi() {
		this.nameList = new ArrayList<>();
		this.urlList = new ArrayList<>();
		this.lineList = new ArrayList<>();
		this.stationList = new ArrayList<>();
		this.timeList = new ArrayList<>();
	}

	/**	検索ヒット数のゲッター*/
	public int getTotal_hit_count() {
		return total_hit_count;
	}

	/**	検索ヒット数のセッター*/
	public void setTotal_hit_count(int total_hit_count) {
		this.total_hit_count = total_hit_count;
	}

	/**	URLのリストゲッター*/
	public ArrayList<String> getUrlList() {
		return urlList;
	}

	/**	店舗名リストのゲッター*/
	public ArrayList<String> getNameList() {
		return nameList;
	}

	/**	路線名リストのゲッター*/
	public ArrayList<String> getLineList() {
		return lineList;
	}

	/**	駅名のリストのゲッター*/
	public ArrayList<String> getStationList() {
		return stationList;
	}

	/**	かかる時間のゲッター*/
	public ArrayList<String> getTimeList() {
		return timeList;
	}


}
