package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.Log;

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
        color = context.getResources().getColor(R.color.def_widget_text_color);
        icon = R.drawable.text;
    }
    public TextWidget(WidgetType type, String name, Context context){
        super(type, name, context);
        fontSize = context.getResources().getInteger(R.integer.def_widget_text_font_size);
        color = context.getResources().getColor(R.color.def_widget_text_color);
    }

    @Override
    public void scale(float scaleFactor) {
        fontSize = context.getResources().getInteger(R.integer.def_widget_text_font_size) * scaleFactor;
        fontSize /= (float) (canvasBox.bottom - canvasBox.top);
        Log.d("TW", "scale: " + scaleFactor);
    }

    public String getText(){
        return text;
    }

    @Override
    public float getWidth() {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float width = bounds.width();
        return width / (canvasBox.right - canvasBox.left);
    }

    @Override
    public float getHeight() {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float height = bounds.height();
        return height / (canvasBox.bottom - canvasBox.top);
    }

    @Override
    public void init() {
        paint = new TextPaint();
        paint.setAntiAlias(context.getResources().getBoolean(R.bool.set_antialiassing_flag));
        paint.setColor(color);
        paint.setTextSize(fontSize * context.getResources().getDisplayMetrics().density);
        fontSize /= (float) (canvasBox.bottom - canvasBox.top);
        Log.d("TW", "sssss: " + fontSize);
        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setTextSize(canvas.getClipBounds().height() * fontSize * context.getResources().getDisplayMetrics().density);
        canvas.drawText(getText(),
                ServiceClass.getValue(x, canvas.getClipBounds().width()),
                ServiceClass.getValue(y + getHeight(),
                        canvas.getClipBounds().height()), paint);
    }
}
