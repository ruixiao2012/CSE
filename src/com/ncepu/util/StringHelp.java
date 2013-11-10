package com.ncepu.util;

public class StringHelp {
	
	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str == "") {
			return true;
		}
		return false;
	}
}
