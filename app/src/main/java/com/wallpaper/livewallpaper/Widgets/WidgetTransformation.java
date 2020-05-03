package com.wallpaper.livewallpaper.Widgets;

public class WidgetTransformation {

    public static void moveWidgetRelative(Widget widget, int x, int y){
        widget.setX(widget.getX() + x);
        widget.setY(widget.getY() + y);
    }

    public static void moveWidgetAbsolute(Widget widget, int x, int y){
        widget.setX(x);
        widget.setY(y);
    }

    public static void centerWidgetHorizontal(Widget widget, int parentWidth){
        widget.setX((int) ((parentWidth / 2f) - (widget.getWidth() / 2f)));
    }

    public static void centerWidgetVertical(Widget widget, int parentHeight){
        widget.setY((int) ((parentHeight / 2f) - (widget.getHeight() / 2)));
    }

    public static void centerWidget(Widget widget, int parentWidth, int parentHeight){
        centerWidgetHorizontal(widget, parentWidth);
        centerWidgetVertical(widget, parentHeight);
    }
}
