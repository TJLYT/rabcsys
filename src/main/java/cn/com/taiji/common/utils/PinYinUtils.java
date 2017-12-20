package cn.com.taiji.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinYinUtils {

	//全拼
		public static String toQuanPin(String source){
			char[] chars = new char[source.length()];
			//将每一个文字转为char保存在数组
			for(int i = 0 ; i < source.length() ; i++){
				String str = source.substring(i,i+1);
				chars[i] = str.toCharArray()[0];
			}
			
			StringBuffer fullpy = new StringBuffer();
			for(char c : chars){
				String pinyin = PinyinHelper.toHanyuPinyinStringArray(c)[0];
				pinyin = pinyin.substring(0,pinyin.length()-1);
				fullpy.append(pinyin);
			}
			return fullpy.toString();
			
		}
		//简拼
		public static String toJianPin(String source){
			char[] chars = new char[source.length()];
			//将每一个文字转为char保存在数组
			for(int i = 0 ; i < source.length() ; i++){
				String str = source.substring(i,i+1);
				chars[i] = str.toCharArray()[0];
			}
			
			StringBuffer jianpin = new StringBuffer();
			for(char c : chars){
				String pinyin = PinyinHelper.toHanyuPinyinStringArray(c)[0];
				pinyin = pinyin.substring(0,1);
				jianpin.append(pinyin);
			}
			return jianpin.toString();
			
		}

}
