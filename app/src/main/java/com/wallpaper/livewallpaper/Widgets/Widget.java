package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.*;

public abstract class Widget {

    public static ArrayList<Widget> allOnscreenWidgets;

    public enum WidgetType{
        NONE,
        CLOCK,
        TEXT
    }


    protected WidgetType type;
    protected float x;
    protected float y;
    protected Context context;
    protected String name;
    protected int icon;

    public Widget(WidgetType type, String name, Context context){
        this.type = type;
        this.name = name;
        this.context = context;
        this.icon = 0;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setIcon(int icon){
        this.icon = icon;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public String getName(){
        return name;
    }
    public int getIcon(){
        return icon;
    }


    // returns ratio of widget width to parent width
    public abstract float getWidth(float canvasWidth);
    // returns ratio of widget height to parent height
    public abstract float getHeight(float canvasHeight);

    public void init(float canvasWidth, float canvasHeight){
        centerWidget(this, canvasWidth, canvasHeight);
    }

    public abstract void draw(Canvas canvas);
}
