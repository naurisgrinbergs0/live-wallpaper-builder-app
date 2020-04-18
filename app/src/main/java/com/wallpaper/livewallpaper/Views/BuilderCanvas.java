package com.wallpaper.livewallpaper.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.Activities.MainActivity;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

public class BuilderCanvas extends View {
    private ArrayList<Widget> widgets;
    private Widget selectedWidget;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public BuilderCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        widgets = new ArrayList<Widget>();
    }

    public void addWidget(Widget widget){
        widget.init(getMeasuredWidth(), getMeasuredHeight());
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float height = MeasureSpec.getSize(heightMeasureSpec);
        float width = (MainActivity.REAL_WIDTH * height) / MainActivity.REAL_HEIGHT;

        setMeasuredDimension((int)width, (int)height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Widget widget : widgets)
            widget.draw(canvas);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(getMeasuredWidth(), getMeasuredHeight());
        layoutParams.leftToLeft = ((View)getParent()).getId();
        layoutParams.rightToRight = ((View)getParent()).getId();
        setLayoutParams(layoutParams);

        invalidate();
    }
}