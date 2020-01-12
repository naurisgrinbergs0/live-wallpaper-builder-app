package com.wallpaper.livewallpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.Widget.Widget;

import java.util.ArrayList;

public class BuilderCanvas extends View {
    private ArrayList<Widget> widgets;
    private Widget selectedWidget;

    public BuilderCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        widgets = new ArrayList<Widget>();
    }

    public void addWidget(Widget widget){
        widget.init(getWidth(), getHeight());
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets(){
        return widgets;
    }

    public Widget getSelectedWidget() {
        return selectedWidget;
    }

    public void setSelectedWidget(Widget selectedWidget) {
        this.selectedWidget = selectedWidget;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setUpSizeAndPosition();

        for(Widget widget : widgets)
            widget.draw(canvas);
        canvas.save();
        canvas.scale(0.5f, 0.5f);
        canvas.restore();
        invalidate();
    }

    private void setUpSizeAndPosition(){
        float screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        float screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        float height = getHeight();
        float width = (screenWidth * height) / screenHeight;

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int)width, (int)height);
        layoutParams.leftToLeft = ((View)getParent()).getId();
        layoutParams.rightToRight = ((View)getParent()).getId();
        setLayoutParams(layoutParams);
    }
}