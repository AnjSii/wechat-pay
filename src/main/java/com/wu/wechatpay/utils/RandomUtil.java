package com.wu.wechatpay.utils;

import java.util.Random;

/**
 * @author k42jc
 */
public class RandomUtil {
	/**
	 * 获取一定长度的随机字符串 微信支付默认随机数生成规则
	 *
	 * @param length 指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getRandomByLength(int n) {
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
		if (CommUtil.null2Int(randomString) != 0) {
			return getRandomByLength(n);
		}
		return randomString.toString();
	}
}
