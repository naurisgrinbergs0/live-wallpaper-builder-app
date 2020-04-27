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
    protected float x;
    protected float y;
    protected Context context;
    protected String name;
    protected int icon;

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


    public abstract void scale(float scaleFactor);

    // returns ratio of widget width to parent width
    public abstract float getWidth();
    // returns ratio of widget height to parent height
    public abstract float getHeight();

    public void init(){
        if(canvasBox != null)
            centerWidget(this,
                    canvasBox.right - canvasBox.left,
                    canvasBox.bottom - canvasBox.top);
    }

    public abstract void draw(Canvas canvas);
}
