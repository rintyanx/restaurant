package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.RegisterLogic;
import model.Gnavi;
import model.User;

public class UserDetailDAO {
	
	/**JDBCドライバの相対パス*/
	private String driverName = "com.mysql.cj.jdbc.Driver";
	
	//**	接続先のデータベース*/
	private String jdbcUrl = "jdbc:mysql://localhost/restaurant?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";
	
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
	
	
	public boolean registerStore(User user,Gnavi gnavi,int listNum,int storeNum) {

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

}
