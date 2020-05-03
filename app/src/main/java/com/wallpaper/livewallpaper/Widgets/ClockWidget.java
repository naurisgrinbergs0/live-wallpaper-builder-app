package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
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
        icon = R.drawable.clock;
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
    public float getWidth() {
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), sizeBox);
        float width = sizeBox.width();
        return width * scale;
    }

    @Override
    public float getHeight() {
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), sizeBox);
        float height = sizeBox.height();
        return height * scale;
    }
}
