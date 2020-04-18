package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;

import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;

public class TextWidget extends Widget {

    protected String text;
    protected TextPaint paint;
    protected float fontSize;
    protected int color;


    public TextWidget(Context context){
        super(WidgetType.TEXT, context);
    }

    public TextWidget(WidgetType type, Context context){
        super(type, context);
    }

    public String getText(){
        return text;
    }

    @Override
    public float getWidth(float parentWidth) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float width = bounds.width();
        return width / parentWidth;
    }

    @Override
    public float getHeight(float parentHeight) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float height = bounds.height();
        return height / parentHeight;
    }


    @Override
    public void init(float canvasWidth, float canvasHeight) {
        paint = new TextPaint();
        paint.setAntiAlias(context.getResources().getBoolean(R.bool.set_antialiassing_flag));
        paint.setColor(color);
        paint.setTextSize(20 * context.getResources().getDisplayMetrics().density);
        fontSize = 20 / canvasHeight;
        super.init(canvasWidth, canvasHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setTextSize(canvas.getClipBounds().height() * fontSize * context.getResources().getDisplayMetrics().density);
        canvas.drawText(getText(),
                ServiceClass.getValue(x, canvas.getClipBounds().width()),
                ServiceClass.getValue(y, canvas.getClipBounds().height()), paint);
    }
}
