package com.wu.wechatpay.utils;

import java.util.Random;

public class WechatUtil {

	public static String random(int n) {
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();
		//随机生成，大写，小写字母，数字组成的八位用户名
		for (int i = 0; i < n; i++) {
			//求余，如果余数为零那么就是输出字符串，余数不为零输出数字。
			boolean charOrNum = random.nextInt(2) % 2 == 0;
			//输出字母还是数字
			if (charOrNum) {
				//输出是大写字母还是小写字母,65～90号为26个大写英文字母，97～122号为26个小写英文字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				randomString.append((char) (random.nextInt(26) + temp));
			} else {
				randomString.append(random.nextInt(10));
			}
		}
		return randomString.toString();
	}


}
