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

    protected int x;
    protected int y;
    protected float scale; // [0.5;10] scale factor
    protected float rotation; // [0;360] rotation

    public Widget(WidgetType type, String name, Context context){
        this.type = type;
        this.name = name;
        this.context = context;
        this.icon = context.getResources().getInteger(R.integer.def_widget_icon);
    }

    public void setCanvasBox(Rect canvasBox){
        this.canvasBox = canvasBox;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setScale(float scale){
        this.scale = scale;
    }
    public void setRotation(float rotation){
        this.rotation = rotation;
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

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
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


    public int getLeft() {
        return getExtrema((byte) 2);
    }

    public int getTop() {
        return getExtrema((byte) 1);
    }

    public int getRight() {
        return getExtrema((byte) 0);
    }

    public int getBottom() {
        return getExtrema((byte) 3);
    }

    // Gets one of 4 extremas
    // index: 0 - right, 1 - top, 2 - left, 3 - bottom
    private int getExtrema(byte index){
        int extrema = 0;
        if(index == 0 || index == 2){
            float alpha = (float) Math.toDegrees(Math.atan2(getHeight(), getWidth()));
            int dist = (int) (Math.sqrt(Math.pow(getHeight(), 2) + Math.pow(getWidth(), 2)) / 2f);
            int xBottomRight = (int) (Math.cos(Math.toRadians(getRotation() - alpha)) * dist);
            int xTopRight = (int) (Math.cos(Math.toRadians(getRotation() + alpha)) * dist);
            int xTopLeft = (int) (Math.cos(Math.toRadians(getRotation() + 180f + alpha)) * dist);
            int xBottomLeft = (int) (Math.cos(Math.toRadians(getRotation() + 180f - alpha)) * dist);
            if(index == 0)
                extrema = (int) (x + Math.max(xBottomRight, Math.max(xTopRight, Math.max(xTopLeft, xBottomLeft))));
            else
                extrema = (int) (x + Math.min(xBottomRight, Math.min(xTopRight, Math.min(xTopLeft, xBottomLeft))));
        }
        else if(index == 1 || index == 3){
            float alpha = (float) Math.toDegrees(Math.atan2(getHeight(), getWidth()));
            int dist = (int) (Math.sqrt(Math.pow(getHeight(), 2) + Math.pow(getWidth(), 2)) / 2f);
            int yBottomRight = (int) (Math.sin(Math.toRadians(getRotation() - alpha)) * dist);
            int yTopRight = (int) (Math.sin(Math.toRadians(getRotation() + alpha)) * dist);
            int yTopLeft = (int) (Math.sin(Math.toRadians(getRotation() + 180f + alpha)) * dist);
            int yBottomLeft = (int) (Math.sin(Math.toRadians(getRotation() + 180f - alpha)) * dist);
            if(index == 1)
                extrema = (int) (y - Math.max(yBottomRight, Math.max(yTopRight, Math.max(yTopLeft, yBottomLeft))));
            else
                extrema = (int) (y - Math.min(yBottomRight, Math.min(yTopRight, Math.min(yTopLeft, yBottomLeft))));
        }
        return  extrema;
    }


    public abstract void draw(Canvas canvas);
}
