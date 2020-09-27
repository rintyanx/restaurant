package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.RegisterLogic;
import model.Gnavi;
import model.User;



public class RegisterDAO {

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

	/**	データベースにアクセスし、入力されたデータでユーザ登録できるか判定する*/
	public boolean registerUser(String id,String pass,String age,String address) {

		/**
		 * 	返り値用変数
		 * 	未登録：true 登録済み：false
		 */
		boolean flag = false;

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

				//	IDを持ってくる
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT ID FROM user;");//SQL文の記述


				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//--------------------------------
				//	重複するユーザーIDがないかチェック
				//--------------------------------
				RegisterLogic registerLogic = new RegisterLogic();

				if(registerLogic.isNotRegisterID(id,rs)) {
					//	重複IDがない場合
					//	INSERT文を送信し、データベースにユーザーを登録
					buf = new StringBuffer();	//SQL文の生成
					buf.append("INSERT INTO user(ID,PASS,AGE,ADDRESS) VALUE('" + id +"','" + pass + "','" + age +"','" + address +"');");

					//SQL発行用オブジェクトに上記のSQL文をセット
					ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

					ps.executeUpdate();

					//	リスト登録用テーブルを作成
					buf = new StringBuffer();	//SQL文の生成
					buf.append("CREATE TABLE "+ id + "(USERID char(6) not null ,LISTNAME char(20) not null,LISTID char(10) not null primary key);");
					ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					ps.executeUpdate();

					flag = true;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}catch(ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
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
		return flag;

	}

	/**	リストに登録したい店舗が登録可能か判定する*/
	public Boolean registerStore(User user,Gnavi gnavi,int listNum,int storeNum) {

		/**
		 * 	返り値用変数
		 * 	未登録：true 登録済み：false
		 */
		Boolean flag = null;

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

				//	登録したいリスト内の店舗名を持ってくる
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT STORENAME FROM "+ user.getId() + user.getList().get(listNum).getListID() +";");//SQL文の記述


				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//--------------------------------
				//	重複する店舗名がないかチェック
				//--------------------------------
				RegisterLogic registerLogic = new RegisterLogic();

				if(registerLogic.isNotRegisterStore(gnavi.getNameList().get(storeNum),rs)) {
					//	重複店舗名がない場合
					//	INSERT文を送信し、データベースに店舗を登録
					buf = new StringBuffer();	//SQL文の生成
					buf.append("INSERT INTO "+ user.getId() + user.getList().get(listNum).getListID() +"(STORENAME,STOREURL) VALUE('" + gnavi.getNameList().get(storeNum) +"','" + gnavi.getUrlList().get(storeNum) +"');");

					//SQL発行用オブジェクトに上記のSQL文をセット
					ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

					ps.executeUpdate();

					flag = new Boolean(true);

				}

			} catch (SQLException e) {
				e.printStackTrace();

			}

		}catch(ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
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
		return flag;

	}

}
