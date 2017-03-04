package com.example.sanji.bibiliproject.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.sanji.bibiliproject.ui.BaseActivity;
import com.google.android.exoplayer.C;

import java.text.DecimalFormat;

/**
 * Created by sanji on 2017/2/21.
 */

public class Utils {

    private static Toast toast;

    //数字格式化 10000 显示1万
    public static String getNumWan(int num) {
        if (num < 10000) {
            return String.valueOf(num);
        } else {
            double n = (double) num / 10000;
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(n) + "万";
        }
    }


    public static void showToast(Context context, String msg, int lengthShort) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 传入总条数，显示总页数
     * @param totalPage 总条数
     * @param pageNum 每页显示几条
     * @return 总页数
     */
    public static int getTotalRecord(int totalPage, int pageNum) {
        int totalRecord = 1;
        //总页数
        if (totalPage >= pageNum) {
            if (totalPage % pageNum == 0) {
                totalRecord = totalPage / pageNum;
            } else {
                totalRecord = totalPage / pageNum + 1;
            }
        }
        return totalRecord;
    }
}
