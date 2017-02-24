package com.example.sanji.bibiliproject.utils;

import java.text.DecimalFormat;

/**
 * Created by sanji on 2017/2/21.
 */

public class NumberUtil {
    public static String getNumWan(int num) {
        if (num < 10000) {
            return String.valueOf(num);
        } else {
            double n = (double) num / 10000;
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(n) + "万";
        }
    }
}
