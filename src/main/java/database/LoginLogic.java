package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * 	ログインできるかを判断する
 */
public class LoginLogic {
	
	/** 
	 * 	与えられたIDとパスワードがuserのIDとパスワードと
	 * 	一致していれば true
	 * 	間違っていれば false
	 */
	public boolean isLogin(User user,ResultSet rs) {
		try {
//			与えられたすべてのデータベースから一致するIDがあるか判定
			while(rs.next())
				
				//	ユーザーIDとパスワードが正しいか判定
				if(user.getId().equals(rs.getString("ID")) && user.getPass().equals(rs.getString("PASS")))
					return true;		//	正しければtrue
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return false;
	}

}
