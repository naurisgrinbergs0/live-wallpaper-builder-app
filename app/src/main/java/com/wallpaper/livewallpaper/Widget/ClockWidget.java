package com.wallpaper.livewallpaper.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.Math;

public class ClockWidget extends Widget {

    private String text;
    private TextPaint paint;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClockWidget(Context context){
        super(WidgetType.CLOCK, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getText(){
        text = Math.getTimeString();
        return text;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public float getWidth(float parentWidth) {
        Rect bounds = new Rect();
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), bounds);
        float width = bounds.width();
        return width / parentWidth;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public float getHeight(float parentHeight) {
        Rect bounds = new Rect();
        paint.getTextBounds("00:00:00", 0, "00:00:00".length(), bounds);
        float height = bounds.height();
        return height / parentHeight;
    }


    @Override
    public void init(float canvasWidth, float canvasHeight) {
        paint = new TextPaint();
        paint.setColor(Color.RED);
        paint.setTextSize(20 * context.getResources().getDisplayMetrics().density);
        super.init(canvasWidth, canvasHeight);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(getText(),
                Math.getValue(x, canvas.getWidth()),
                Math.getValue(y, canvas.getHeight()), paint);
    }
}
