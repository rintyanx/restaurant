package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.RegisterLogic;
import model.RandStr;
import model.Store;
import model.StoreList;
import model.User;

public class ListDAO {

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

	/**	データベースにアクセスし、ユーザが入力した名前のリストを作成する*/
	public Boolean create(User user, String listName) {

		//	返り値用のUserリスト
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
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT LISTNAME FROM "+ user.getId() +";");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//--------------------------------
				//	重複するリスト名とリストIDがないかチェック
				//--------------------------------
				RegisterLogic logic = new RegisterLogic();

				//	ランダム１０桁のID生成
				RandStr a = new RandStr();
				String listID = a.makeListID();

				if(logic.isNotRegisterList(listName,rs) && listName.length() !=0) {	//	重複リスト名がない、名前が入力されている場合は、新規リストを作成する

					//	ユーザのリストテーブルに作成したリストを追加
					buf = new StringBuffer();
					buf.append("INSERT INTO "+ user.getId() +"(USERID,LISTNAME,LISTID) VALUE('"+ user.getId() +"','"+listName +"','"+ listID +"');");//SQL文の記述

					//SQL発行用オブジェクトに上記のSQL文をセット
					ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

					//executeQueryでデータベースの情報を更新
					ps.executeUpdate();

					//	作成したリストに入れる店舗テーブルを作成
					buf = new StringBuffer();
					buf.append("CREATE TABLE "+ user.getId() + listID +"(STORENAME char(50) NOT NULL,STOREURL char(250) NOT NULL PRIMARY KEY);");//SQL文の記述

					//SQL発行用オブジェクトに上記のSQL文をセット
					ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

					//executeQueryでデータベースの情報を更新
					ps.executeUpdate();

					//	すべての動作がうまくいったとき返り値にtrueを設定
					flag = new Boolean(true);
				}

			} catch (SQLException e) {
				//SQLでエラーが出た場合は返り値にfalseを設定
				e.printStackTrace();
				flag = new Boolean(false);
			}


		}catch(ClassNotFoundException e) {
			//	SQL以外のエラーの場合は何もしない
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


	/**	データベースにアクセスし、ユーザの作成しているリストを持ってきて、Listとして返す*/
	public ArrayList<StoreList> getStoreList(User user) {

		//	返り値用のStoreListのリスト
		ArrayList<StoreList> list = new ArrayList<>();

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

				//	作成しているリストの情報をすべて受け取るSQL
				StringBuffer buf = new StringBuffer();
				buf.append("SELECT * FROM "+ user.getId() +";");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				while(rs.next())

					//	返り値用にStoreListを追加
					list.add(new StoreList(rs.getString("USERID"),rs.getString("LISTNAME"),rs.getString("LISTID")));


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

	/**	データベースにアクセスし、ユーザの作成しているリストの中から、リストIDが一致するもののリスト名を返す*/
	public String getListName(User user,String listID) {

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
				buf.append("SELECT LISTNAME FROM "+ user.getId() +" WHERE LISTID = '"+listID+"';");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				//	該当するリストが存在した場合
				if(rs.next()) {
					return rs.getString("LISTNAME");	//	リストIDが一致するリストの名前を返す
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

		return null;
	}

	/**	データベースにアクセスし、指定されたリストを削除する*/
	public boolean delete(User user, int listNum) {

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

				//	リストの中身の店舗リストを削除
				StringBuffer buf = new StringBuffer();
				buf.append("DROP TABLE "+ user.getId() + user.getList().get(listNum).getListID() +";");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を更新
				ps.executeUpdate();

				//	ユーザが作成したリスト群から削除
				buf = new StringBuffer();
				buf.append("DELETE FROM "+ user.getId() +" WHERE LISTID = '"+ user.getList().get(listNum).getListID() +"';");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を更新
				ps.executeUpdate();

				user.setList(getStoreList(user));

				flag = true;

			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
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


	/**	データベースにアクセスし、ユーザIDとリストIDから指定されたリストに入っている店舗名とそのURLをリストにして返す*/
	public ArrayList<Store> detail(User user, String listID) {

		//	返り値用のUserリスト
		ArrayList<Store> list = new ArrayList<>();

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
				buf.append("SELECT * FROM "+ user.getId() + listID +";");//SQL文の記述

				//SQL発行用オブジェクトに上記のSQL文をセット
				ps = con.prepareStatement(buf.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

				//executeQueryでデータベースの情報を格納
				rs = ps.executeQuery();

				while(rs.next()) {
					//	返り値用にStoreListを追加
					list.add(new Store(rs.getString("STORENAME"),rs.getString("STOREURL")));
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
