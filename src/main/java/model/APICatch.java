package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 	ぐるなびAPIに関するクラス
 * */
public class APICatch {

	JsonNode node;
	String json;

	public APICatch(String name,String freeword,String offset_page, String[] select) {
		json = APIconnect(name,freeword,offset_page,select);
	}

	/**
	 *	 ぐるなびにリクエストURLを送信し、JSON形式でレスポンスを受け取る
	 *	返り値は、JSONの内容が入った文字列
	 */
	public String APIconnect(String name,String freeword,String offset_page, String[] select) {

					//	リクエストURL	keyidしか追加されていない場合はエラー
					String strUrl = "https://api.gnavi.co.jp/RestSearchAPI/v3/?keyid=b51a3a6f1e3de52b0b1126b9e1977411";

					//	フリーワードがある場合
					if(!freeword.equals("") || !name.equals("")) {
							strUrl += "&freeword="+freeword;	//URLにフリーワードを追加
							strUrl += "&name="+name;	//URLに店舗名を追加
							strUrl += "&offset_page="+offset_page;	//URLに検索ページを追加
							//	絞り込み内容をリクエストURLに追加する
							for(int i = 0;select != null && i < select.length;i++) {
								strUrl += "&" + select[i];	////URLに絞り込み内容を追加
							}
					}else {	//	フリーワードがない場合
						//	freeword_condition以外の絞り込み内容をリクエストURLに追加する
						for(int i = 1;select != null && i < select.length;i++) {
							strUrl += "&" + select[i];
						}
					}

					HttpURLConnection  urlConn = null;
					InputStream in = null;
					BufferedReader reader = null;
					String line = "";

					try {
						//	接続するURLを指定する
						URL url = new URL(strUrl);

						//	コネクションを取得する
						urlConn = (HttpURLConnection) url.openConnection();

						urlConn.setRequestMethod("GET");

						urlConn.connect();

						int status = urlConn.getResponseCode();

					    if (status == HttpURLConnection.HTTP_OK) {

							in = urlConn.getInputStream();

					    	reader = new BufferedReader(new InputStreamReader(in));

							StringBuilder output = new StringBuilder();

							while ((line = reader.readLine()) != null) {
								output.append(line);
							}
							line = output.toString();
					      }
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							if (reader != null) {
								reader.close();
							}
							if (urlConn != null) {
								urlConn.disconnect();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					return line;
				}


	/**
	 * 	ぐるなびからレスポンスを受け取り、レストラン情報（名前とＵＲＬ）を受け取る
	 */
	public Gnavi getResult() {
		//JSONを受け取る準備
		ObjectMapper mapper = new ObjectMapper();
		//	ぐるなびから取得した情報を格納するクラス
		Gnavi gnavi = new Gnavi();

	        try {
	        	 //	ぐるなびにリクエストする
	             node = mapper.readTree(json);
	             //	検索ヒット数をセット
	             gnavi.setTotal_hit_count(node.get("total_hit_count").asInt());

	             //	取得レストラン情報がなくなるまで繰り返し
	             for(int i = 0;i < node.get("rest").size();i++) {
	            	 //	店舗名をセット
	            	 gnavi.getNameList().add(node.get("rest").get(i).get("name").asText());
	            	 //	店舗URLをセット
	            	 gnavi.getUrlList().add(node.get("rest").get(i).get("url").asText());
	            	 //路線名をセット
	            	 gnavi.getLineList().add(node.get("rest").get(i).get("access").get("line").asText());
	            	 //駅名をセット
	            	 gnavi.getStationList().add(node.get("rest").get(i).get("access").get("station").asText());
	            	 //かかる時間をセット
	            	 gnavi.getTimeList().add(node.get("rest").get(i).get("access").get("walk").asText());

	             }

	             return gnavi;

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch(NullPointerException e) {	//	レスポンスがない場合の例外処理
	        	 e.printStackTrace();
	        }
	        //	エラーが出た場合はnullを返す
	        return null;
	}

}
