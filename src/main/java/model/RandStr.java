package model;

import java.util.Random;

/**
 *	ランダムな文字列に関するクラス 
 */

public class RandStr {
	
	/**	ランダムに文字列を生成し、リストIDとして返り値とするメソッド*/
	public String makeListID() {
		//	アルファベットと数字を使用する文字列に設定
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        
        //	文字列の長さ x を指定しその分繰り返す(salt.length() < x)
        while (salt.length() < 10) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        
        //	生成したものをStringにして、返り値にする
        String saltStr = salt.toString();
        return saltStr;
    }
}
