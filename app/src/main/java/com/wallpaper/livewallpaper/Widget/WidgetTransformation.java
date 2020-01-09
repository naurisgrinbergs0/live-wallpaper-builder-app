package com.wallpaper.livewallpaper.Widget;

import android.view.View;

public class WidgetTransformation {

    public static void moveWidgetRelative(Widget widget, float x, float y){
        widget.setX(widget.getX() + x);
        widget.setY(widget.getY() + y);
    }

    public static void moveWidgetAbsolute(View view, float x, float y){
        view.setX(x);
        view.setY(y);/////////////////////////////////////////////////////////////////////////////////////////
    }

    public static float getCenteredX(float width){
        return .5f - (width / 2);
    }

    public static float getCenteredY(float height){
        return .5f - (height / 2);
    }
}
