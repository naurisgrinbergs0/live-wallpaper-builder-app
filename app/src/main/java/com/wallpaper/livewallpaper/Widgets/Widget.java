package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.wallpaper.livewallpaper.R;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.centerWidget;

public abstract class Widget {

    public static ArrayList<Widget> allOnscreenWidgets;

    public enum WidgetType{
        NONE,
        CLOCK,
        TEXT
    }

    protected Rect canvasBox;

    protected WidgetType type;
    protected Context context;
    protected String name;
    protected int icon;

    protected float x;
    protected float y;
    protected float scale; // [0.5;10] scale factor
    protected float rotation; // [0;360] rotation

    public Widget(WidgetType type, String name, Context context){
        this.type = type;
        this.name = name;
        this.context = context;
        this.icon = context.getResources().getInteger(R.integer.def_widget_icon);
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setCanvasBox(Rect canvasBox){
        this.canvasBox = canvasBox;
    }
    public void setScale(float scale){
        this.scale = scale;
    }
    public void setRotation(float rotation){
        this.rotation = rotation;
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
    public Rect getCanvasBox(){
        return canvasBox;
    }
    public float getScale(){
        return scale;
    }
    public float getRotation(){
        return rotation;
    }

    public abstract float getWidth();
    public abstract float getHeight();

    public void init(){
        if(canvasBox != null) {
            centerWidget(this,
                    canvasBox.right - canvasBox.left,
                    canvasBox.bottom - canvasBox.top);
            scale = 1;
            rotation = 0;
        }
    }

    public abstract void draw(Canvas canvas);
}
