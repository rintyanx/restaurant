package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.User;

public class SearchDAO {

	/**JDBCドライバの相対パス*/
	private String driverName = "com.mysql.cj.jdbc.Driver";

	//**	接続先のデータベース*/
	//ローカル環境用
	//private String jdbcUrl = "jdbc:mysql://localhost/restaurant?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";

	//本番環境用
	private String jdbcUrl = "jdbc:mysql://b556fc6e1dce68:0db7be8c@us-cdbr-east-02.cleardb.com/heroku_cf7e12ba5ffd86a?characterEncoding=UTF-8&serverTimezone=JST&allowPublicKeyRetrieval=true&useSSL=false";


	/**	接続するユーザー名*/
	private String userId = "root";

	/**	接続するユーザーのパスワード*/
	private String userPass = "root";

	/**Connection(データベース接続情報）格納用変数*/
	protected Connection 			con = null;

	/**PreparedStatement(SQL発行用オブジェクト)格納用変数*/
	protected PreparedStatement 	ps 	= null;

	/**ResultSet(SL抽出結果)格納用変数*/
	protected ResultSet 			rs 	= null;

	/**	データベースにアクセスし、指定された情報に一致するユーザを返す*/
	public ArrayList<User> getUserList(String age,String address) {

		//	返り値用のStoreListのリスト
		ArrayList<User> list = new ArrayList<>();

		try {

			//--------------------------------
			//	ドライバのロードを行う
			//--------------------------------
			Class.forName(driverName);

			try {

				//----------------------------
				//	接続の確立（Connectionオブジェクトの取得）
				//----------------------------

				con = DriverManager.getConnection(jdbcUrl, userId, userPass);

				//----------------------------
				//	SQLの送信
				//----------------------------

				//	絞り込み内容に一致するユーザIDを持ってくる
				StringBuffer buf = new StringBuffer();

				//	絞り込みによってSQL文を変える
				if(address.isEmpty())	//	都道府県未指定
					buf.append("SELECT ID FROM user WHERE AGE = "+ age +";");//SQL文の記述
				else if(age.isEmpty())	//	年齢未指定
					buf.append("SELECT ID FROM user WHERE ADDRESS = '"+ address +"';");//SQL文の記述
				else	//	両方指定
					buf.append("SELECT ID FROM user WHERE AGE = "+ age +" AND ADDRESS = '"+ address +"';");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//	該当するユーザをリストに追加する
				while(rs.next()) {
					//	返り値用にStoreListを追加
					list.add(new User(rs.getString("ID")));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}catch(ClassNotFoundException e) {
			e.printStackTrace();

		} finally {

			//----------------------
			//	接続の解除
			//----------------------
			if(rs != null) {	//	接続を確認できたときのみ実行
				try {
					rs.close();

				} catch (SQLException e) {
					e.printStackTrace();

				}
			}

			if(ps != null) {	//	接続を確認できたときのみ実行
				try {
					ps.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return list;
	}


}
