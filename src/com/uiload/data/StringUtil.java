package com.uiload.data;

public class StringUtil {

	public String convertTime(String time_info) {
		String time_trim = time_info.substring(0, time_info.length() - 2);
		time_trim = time_trim.replaceAll("\\s", "");
		Integer time_int = Integer.parseInt(time_trim);
		Integer x = time_int / 1000;
		Integer seconds = x % 60;
		String str_time = Integer.toString(seconds) + " Sec";
		return str_time;
	}

	public String convertSize(String size_info) {
		size_info = size_info.substring(0, size_info.length() - 3);
		size_info = size_info.replaceAll("\\s", ",");
		return size_info + "kB";
	}
}
