package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;

public class ClockWidget extends TextWidget {

    private static int lastWidgetId = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClockWidget(Context context){
        super(WidgetType.CLOCK, context.getString(R.string.pre_widget_clock) + (lastWidgetId + 1), context);
        lastWidgetId++;
        icon = R.drawable.hour;
        text = getText();
    }
    public ClockWidget(Widget.WidgetType type, String name, Context context){
        super(type, name, context);
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
