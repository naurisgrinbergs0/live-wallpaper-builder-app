package com.wallpaper.livewallpaper.Widget;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widget.WidgetTransformation.*;

public abstract class Widget {

    public static ArrayList<Widget> ALL_ONSCREEN_WIDGETS;

    public enum WidgetType{
        CLOCK,
        TEXT
    }

    protected WidgetType type;
    protected float x;
    protected float y;
    protected Context context;

    Widget(WidgetType type, Context context){
        this.type = type;
        this.context = context;
    }

    void setX(float x){
        this.x = x;
    }
    void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    public abstract float getWidth(float canvasWidth);
    public abstract float getHeight(float canvasHeight);

    public void init(float canvasWidth, float canvasHeight){
        centerWidget(this, canvasWidth, canvasHeight);
    }

    public abstract void draw(Canvas canvas);
}
