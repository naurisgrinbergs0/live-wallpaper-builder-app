package com.wallpaper.livewallpaper.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.Math;

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
    protected View view;
    protected View parent;

    public void Widget(WidgetType type, Context context, View parent){
        this.type = type;
        this.context = context;
        this.parent = parent;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Widget createWidget(WidgetType type, Context context, ConstraintLayout canvas){
        Widget widget = null;
        switch (type){
            case CLOCK:
                widget = new ClockWidget(context, canvas);
                break;
        }
        return widget;
    }

    public void placeView(){
        this.x = getCenteredX(getWidth());
        this.y = getCenteredY(getHeight());
        ((ConstraintLayout)parent).addView(this.view);
    }

    public void setParent(View parent){
        this.parent = parent;
    }

    public void setType(WidgetType type){
        this.type = type;
    }

    public WidgetType getType(){
        return type;
    }

    public View getView(){
        return this.view;
    }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }

    public abstract float getWidth();
    public abstract float getHeight();

    public void updateView(){
        if(parent == null)
            return;
    }
    public abstract void renderWidget(Canvas canvas);
}
