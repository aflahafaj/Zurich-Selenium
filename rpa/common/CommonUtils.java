package com.rds.rpa.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonUtils {

	public static boolean isNotEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String generateRandomCharacter(int length) {
		String str = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		Random random = new Random();

		String result = "";
		for (int i = 0; i < length; i++) {
			int randomNumber = random.nextInt(str.length());
			result = result + str.charAt(randomNumber);
		}

		return result;
	}

}
