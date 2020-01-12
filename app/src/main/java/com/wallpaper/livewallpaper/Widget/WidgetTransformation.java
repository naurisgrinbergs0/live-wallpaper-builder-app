package com.wallpaper.livewallpaper.Widget;

public class WidgetTransformation {

    public static void moveWidgetRelative(Widget widget, float x, float y){
        widget.setX(widget.getX() + x);
        widget.setY(widget.getY() + y);
    }

    public static void moveWidgetAbsolute(Widget widget, float x, float y){
        widget.setX(x);
        widget.setY(y);
    }

    public static void centerWidgetHorizontal(Widget widget, float parentWidth){
        widget.setX(.5f - (widget.getWidth(parentWidth) / 2));
    }

    public static void centerWidgetVertical(Widget widget, float parentHeight){
        widget.setY(.5f - (widget.getHeight(parentHeight) / 2));
    }

    public static void centerWidget(Widget widget, float parentWidth, float parentHeight){
        centerWidgetHorizontal(widget, parentWidth);
        centerWidgetVertical(widget, parentHeight);
    }
}
