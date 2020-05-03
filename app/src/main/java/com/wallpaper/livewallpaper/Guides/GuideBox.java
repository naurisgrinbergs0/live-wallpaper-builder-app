package com.wallpaper.livewallpaper.Guides;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

public class GuideBox extends Guide{

    private Rect guideBox;
    private Rect cornerBox;
    private Paint strokePaint;
    private Paint cornerPaint;
    private int cornerSize;

    public GuideBox(int strokeColor, int strokeWidth, int strokeDash, int strokeGap,
                    int cornerColor, int cornerSize){
        super();
        guideBox = new Rect();
        cornerBox = new Rect();
        strokePaint = new Paint();
        cornerPaint = new Paint();

        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setPathEffect(new DashPathEffect(new float[]{strokeDash, strokeGap}, 0));

        cornerPaint.setColor(cornerColor);
        this.cornerSize = cornerSize;
    }

    public void setLeft(int left){
        guideBox.left = left;
    }
    public void setRight(int right){
        guideBox.right = right;
    }
    public void setTop(int top){
        guideBox.top = top;
    }
    public void setBottom(int bottom){
        guideBox.bottom = bottom;
    }

    public int getLeft(){
        return guideBox.left;
    }
    public int getRight(){
        return guideBox.right;
    }
    public int getTop(){
        return guideBox.top;
    }
    public int getBottom(){
        return guideBox.bottom;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(guideBox, strokePaint);

        drawGuideBoxCorner(canvas, guideBox.left, guideBox.top);
        drawGuideBoxCorner(canvas, guideBox.right, guideBox.top);
        drawGuideBoxCorner(canvas, guideBox.right, guideBox.bottom);
        drawGuideBoxCorner(canvas, guideBox.left, guideBox.bottom);
    }

    private void drawGuideBoxCorner(Canvas canvas, int xCenter, int yCenter){
        byte halfCornerSize = (byte) (cornerSize / 2f);
        cornerBox.left = xCenter - halfCornerSize;
        cornerBox.top = yCenter - halfCornerSize;
        cornerBox.right = xCenter + halfCornerSize;
        cornerBox.bottom = yCenter + halfCornerSize;
        canvas.drawRect(cornerBox, cornerPaint);
    }
}
