package com.wallpaper.livewallpaper;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Math {
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
}
