package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 	登録できるかを判定するクラス
 */
public class RegisterLogic {
	
	/** 
	 * 	入力されたIDが
	 * 	登録されていなければ true
	 * 	登録されていれば	   false
	 */
	public boolean isNotRegisterID(String newId,ResultSet rs) {
		
		try {
			//	与えられたすべてのデータベースから一致するIDがあるか判定
			while(rs.next()) {
				if(newId.equals(rs.getString("ID"))) {	//	重複ID がある場合
					return false;						//	falseを返す
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/** 
	 * 	入力されたリスト名が
	 * 	登録されていなければ true
	 * 	登録されていれば	   false
	 */
	public boolean isNotRegisterList(String namelist,ResultSet rs) {
		
		try {
			//	与えられたすべてのデータベースから一致するIDがあるか判定
			while(rs.next()) {
				if(namelist.equals(rs.getString("LISTNAME"))) {	//	重複リスト名 がある場合
					return false;						//	falseを返す
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/** 
	 * 	入力された店舗名が
	 * 	登録されていなければ true
	 * 	登録されていれば	   false
	 */
	public boolean isNotRegisterStore(String storeName,ResultSet rs) {
		
		try {
			//	与えられたすべてのデータベースから一致するIDがあるか判定
			while(rs.next()) {
				if(storeName.equals(rs.getString("STORENAME"))) {	//	重複ID がある場合
					return false;						//	falseを返す
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
