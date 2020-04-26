package com.wallpaper.livewallpaper;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ServiceClass {
    /**
     * Gets current time formatted
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeString(String format){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Gets time string in default format
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeString(){
        return getTimeString("hh:mm:ss");
    }


    /**
     * Gets current date formatted
     * @return
     */
    public static String getDateString(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Gets date formatted in default format
     * @return
     */
    public static String getDateString(){
        return getDateString("EEEE");
    }


    public static String zeroFill(int num){
        String text = String.valueOf(num);
        if(num == 0)
            text = "00";
        else if(num < 10)
            text = "0" + text;
        return text;
    }

    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets value in percentage
     * @return
     */
    public static float getPercent(float value, float maxValue){
        return value / maxValue;
    }


    /**
     * Gets value from percent
     * @return
     */
    public static float getValue(float percent, float maxValue){
        return percent * maxValue;
    }


    public static float REAL_WIDTH;
    public static float REAL_HEIGHT;

    public static void setScreenSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        REAL_HEIGHT = displayMetrics.heightPixels;
        REAL_WIDTH = displayMetrics.widthPixels;
    }
}
