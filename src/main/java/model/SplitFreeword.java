package model;

public class SplitFreeword {
	
	/**	全角スペース区切りで入力されたフリーワードをリクエストURLに合う形に変換する*/
	public String split(String freeword) {
		//	全角スペースごとに単語に分ける
		String[] word = freeword.split("　",0);
		//	返り値用変数
		String str = "";
		//	配列の長さ文繰り返し、単語を挿入していく
		for(int i = 0;i < word.length;i++) {
			str += word[i];
			//	単語を「 , 」区切りにする
			if(i != word.length - 1) {
				str += ",";
			}
		}
		return str;
	}
}
