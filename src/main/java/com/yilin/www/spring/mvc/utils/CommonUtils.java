package com.yilin.www.spring.mvc.utils;

import java.text.NumberFormat;

public class CommonUtils {

	public static String timePeriod(long time) {
		NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3); 
        double r = (double)time/1000;
		return nf.format(r);
	}
}
