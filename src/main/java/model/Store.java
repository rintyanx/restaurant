package model;

/**
 *	店舗に関するクラス
 *	ぐるなびからの店舗に関するレスポンスを格納する 
 */

public class Store {
	
	/**	店舗名*/
	private String name;
	/**	店舗URL*/
	private String url;
	
	/**	コンストラクタ*/
	public Store(String name,String url) {
		this.name = name;
		this.url = url;
	}

	/**	店舗名のゲッター*/
	public String getName() {
		return name;
	}

	/**	店舗名のゲッター*/
	public String getUrl() {
		return url;
	}
}
