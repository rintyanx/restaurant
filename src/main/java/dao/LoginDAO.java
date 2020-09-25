package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.LoginLogic;
import model.User;

public class LoginDAO{

	/**JDBCドライバの相対パス*/
	private String driverName = "com.mysql.cj.jdbc.Driver";

	//**	接続先のデータベース*/
	//ローカル環境用
	//private String jdbcUrl = "jdbc:mysql://localhost/restaurant?characterEncoding=UTF-8&serverTimezone=JST&allowPublicKeyRetrieval=true&useSSL=false";

	//本番環境用
	private String jdbcUrl = "jdbc:mysql://b76126908d3ed4:019e5385@us-cdbr-east-02.cleardb.com/heroku_50ea85f5a607dcd?characterEncoding=UTF-8&serverTimezone=JST&allowPublicKeyRetrieval=true&useSSL=false";

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

	/**	データベースにアクセスし、ログインIDとPASSが一致するユーザが存在しているか返す*/
	public boolean login(User user) {

		//	返り値用のUserリスト
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

				//	登録されているユーザを持ってくる
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT * FROM user;");//SQL文の記述


				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//--------------------------------
				//	ユーザーIDとパスワードが一致しているかチェック
				//--------------------------------
				LoginLogic loginLogic = new LoginLogic();

				if(loginLogic.isLogin(user,rs)) {
					try {
						user.setAge(Integer.parseInt(rs.getString("AGE")));
					}catch(NumberFormatException e) {
						//	年齢を選択していない場合、処理を行わない
					}
					try {
						user.setAddress(rs.getString("ADDRESS"));
					}catch(NumberFormatException e) {
						//	都道府県を選択していない場合、処理を行わない
					}

					flag = true;
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

		return flag;
	}

}
