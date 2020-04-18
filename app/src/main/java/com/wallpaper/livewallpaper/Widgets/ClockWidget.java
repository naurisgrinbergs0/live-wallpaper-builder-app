package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;

import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.ServiceClass;

public class ClockWidget extends TextWidget {

    public ClockWidget(Context context){
        super(WidgetType.CLOCK, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getText(){
        text = ServiceClass.getTimeString();
        return text;
    }

    @Override
    public float getWidth(float parentWidth) {
        Rect bounds = new Rect();
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), bounds);
        float width = bounds.width();
        return width / parentWidth;
    }

    @Override
    public float getHeight(float parentHeight) {
        Rect bounds = new Rect();
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), bounds);
        float height = bounds.height();
        return height / parentHeight;
    }
}
