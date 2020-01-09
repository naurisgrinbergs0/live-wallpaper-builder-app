package com.wallpaper.livewallpaper.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.wallpaper.livewallpaper.Math;
import com.wallpaper.livewallpaper.R;

public class ClockWidget extends Widget {

    private String text;
    private Typeface fontFamily;
    private float fontSizeLayout;
    private float fontSizeCanvas;
    private int color;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClockWidget(Context context, View parent){
        super.Widget(WidgetType.CLOCK, context, parent);

        view = new TextView(context);
        ((TextView)view).setText(getText());
        ((TextView)view).setTypeface(fontFamily);

        fontFamily = ResourcesCompat.getFont(context, R.font.frederickathe_great_regular);
        setFontSizeForPercentageLayout(.5f);
        color = Color.RED;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getText(){
        text = Math.getTimeString();
        return text;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public float getWidth() {
        view.measure(0, 0);
        return ((float)view.getMeasuredWidth()) / ((float)parent.getMeasuredWidth());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public float getHeight() {
        view.measure(0, 0);
        return ((float)view.getMeasuredHeight()) / ((float)parent.getMeasuredHeight());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFontSizeForPercentageLayout(float targetPercentage){
        if(!((TextView)view).getText().equals("")){
            float step = .1f;
            float percentage = 0;
            fontSizeLayout = ((TextView)view).getTextSize();
            while(percentage < targetPercentage){
                fontSizeLayout += step;
                ((TextView)view).setTextSize(fontSizeLayout);
                percentage = getWidth();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setFontSizeForCanvas(TextPaint paint, Canvas canvas){
        float desiredWidth = canvas.getWidth() * (getWidth() / parent.getMeasuredWidth());
        fontSizeCanvas = (paint.getTextSize() * desiredWidth) / paint.measureText(text, 0, text.length());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updateView() {
        super.updateView();
        ((TextView)view).setText(getText());
        ((TextView)view).setTypeface(fontFamily);
        ((TextView)view).setTextColor(color);
        ((TextView)view).setTextSize(fontSizeLayout);
        view.setX(Math.getValue(x, parent.getWidth()));
        view.setY(Math.getValue(y, parent.getHeight()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void renderWidget(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setTypeface(fontFamily);
        paint.setColor(color);
        setFontSizeForCanvas(paint, canvas);
        paint.setTextSize(fontSizeCanvas * context.getResources().getDisplayMetrics().density);
        canvas.drawText(getText(), 300, 300, paint);
    }
}
