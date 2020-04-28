package com.wallpaper.livewallpaper.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import com.wallpaper.livewallpaper.R;

public class TextWidget extends Widget {

    private static int lastWidgetId = -1;

    protected TextPaint paint;
    protected String text;
    protected int color;


    public TextWidget(Context context){
        super(WidgetType.TEXT, context.getString(R.string.pre_widget_text) + (lastWidgetId + 1), context);
        lastWidgetId++;
        text = name;
        color = context.getResources().getColor(R.color.def_widget_text_color);
        icon = R.drawable.text;
    }
    public TextWidget(WidgetType type, String name, Context context){
        super(type, name, context);
        color = context.getResources().getColor(R.color.def_widget_text_color);
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
        paint.setTextSize(context.getResources().getInteger(R.integer.def_widget_text_font_size));
        super.init();
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(getText(),0, getHeight() * canvasBox.height(), paint);
    }


    private static void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {
        final float testTextSize = 48f;

        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        float desiredTextSize = testTextSize * desiredWidth / bounds.width();
        paint.setTextSize(desiredTextSize);
    }
}
