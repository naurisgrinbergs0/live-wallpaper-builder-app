package com.wallpaper.livewallpaper.Guides;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.wallpaper.livewallpaper.R;

import java.util.Hashtable;

public class GuideLine extends Guide{
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    private Paint paint;

    public GuideLine(int color, int strokeWidth, int strokeDash, int strokeGap){
        super();
        paint = new Paint();

        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setPathEffect(new DashPathEffect(new float[]{strokeDash, strokeGap}, 0));
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public enum Type{
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }


    public static Hashtable<Type, GuideLine> getInitGuideLines(Context context){
        Hashtable<Type, GuideLine> guides = new Hashtable<Type, GuideLine>();
        int strokeColor = context.getResources().getColor(R.color.builder_canvas_position_guide_color);
        int strokeWidth = context.getResources().getInteger(R.integer.builder_canvas_position_guide_stroke_width);
        int strokeDash = context.getResources().getInteger(R.integer.builder_canvas_position_guide_stroke_dash);
        int strokeGap = context.getResources().getInteger(R.integer.builder_canvas_position_guide_stroke_gap);
        guides.put(Type.LEFT, new GuideLine(strokeColor, strokeWidth, strokeDash, strokeGap));
        guides.put(Type.RIGHT, new GuideLine(strokeColor, strokeWidth, strokeDash, strokeGap));
        guides.put(Type.TOP, new GuideLine(strokeColor, strokeWidth, strokeDash, strokeGap));
        guides.put(Type.BOTTOM, new GuideLine(strokeColor, strokeWidth, strokeDash, strokeGap));
        return guides;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
    }
}