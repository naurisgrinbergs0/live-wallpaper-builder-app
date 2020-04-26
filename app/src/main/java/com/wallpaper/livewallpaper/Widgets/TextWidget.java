package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;

public class TextWidget extends Widget {

    private static int lastWidgetId = -1;

    protected TextPaint paint;
    protected String text;
    protected float fontSize;
    protected int color;


    public TextWidget(Context context){
        super(WidgetType.TEXT, context.getString(R.string.pre_widget_text) + (lastWidgetId + 1), context);
        lastWidgetId++;
        text = name;
        fontSize = context.getResources().getInteger(R.integer.def_widget_text_font_size);
        color = context.getResources().getInteger(R.integer.def_widget_text_color);
        icon = 999;
    }
    public TextWidget(WidgetType type, String name, Context context){
        super(type, name, context);
        fontSize = context.getResources().getInteger(R.integer.def_widget_text_font_size);
        color = context.getResources().getInteger(R.integer.def_widget_text_color);
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
