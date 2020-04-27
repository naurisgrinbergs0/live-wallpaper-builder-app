package com.wallpaper.livewallpaper.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.GestureDetectors.MoveGestureDetector;
import com.wallpaper.livewallpaper.GestureDetectors.ScaleGestureDetector;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.moveWidgetRelative;

public class BuilderCanvas extends View implements MoveGestureDetector.OnMoveGestureListener, ScaleGestureDetector.OnScaleGestureListener {
    private ArrayList<Widget> widgets;
    private Widget selectedWidget;
    private Paint widgetBoxPaint;
    private Paint widgetBoxCornerPaint;
    private Rect widgetBox;
    private Rect widgetBoxCorner;
    private Rect canvasBox;

    private MoveGestureDetector moveGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public BuilderCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        widgetBoxPaint = new Paint();
        widgetBoxPaint.setColor(
                getContext().getResources().getColor(R.color.builder_canvas_selected_widget_box_stroke_color));
        widgetBoxPaint.setStyle(Paint.Style.STROKE);
        widgetBoxPaint.setStrokeWidth(
                getContext().getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_width));
        widgetBoxPaint.setPathEffect(new DashPathEffect(new float[]{
                getContext().getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_dash),
                getContext().getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_gap)},
                0));

        widgetBoxCornerPaint = new Paint();
        widgetBoxCornerPaint.setColor(
                getContext().getResources().getColor(R.color.builder_canvas_selected_widget_box_corner_color));



        widgetBox = new Rect();
        widgetBoxCorner = new Rect();
        widgets = new ArrayList<Widget>();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        moveGestureDetector = new MoveGestureDetector(this);
    }

    public void addWidget(Widget widget){
        widget.setCanvasBox(canvasBox);
        widget.init();
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets(){
        return widgets;
    }


    public Widget getSelectedWidget() {
        return selectedWidget;
    }

    public void setSelectedWidget(String name) {
        for(Widget widget : widgets)
            if(widget.getName().equals(name))
                selectedWidget = widget;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float height = MeasureSpec.getSize(heightMeasureSpec);
        float width = (ServiceClass.REAL_WIDTH * height) / ServiceClass.REAL_HEIGHT;

        setMeasuredDimension((int)width, (int)height);
    }

    private int getCanvasWidth(){
        return (canvasBox.right - canvasBox.left);
    }

    private int getCanvasHeight(){
        return (canvasBox.bottom - canvasBox.top);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBox = new Rect();
        canvasBox.left = 0;
        canvasBox.top = 0;
        canvasBox.right = canvasBox.left + w;
        canvasBox.bottom = canvasBox.top + h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw all the widgets
        for(Widget widget : widgets)
            widget.draw(canvas);
        // draw box around selected widget
        if(selectedWidget != null)
            drawWidgetBox(selectedWidget, canvas);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(getCanvasWidth(), getCanvasHeight());
        layoutParams.leftToLeft = ((View)getParent()).getId();
        layoutParams.rightToRight = ((View)getParent()).getId();
        setLayoutParams(layoutParams);

        invalidate();
    }

    private void drawWidgetBox(Widget widget, Canvas canvas){
        byte stroke = (byte) widgetBoxPaint.getStrokeWidth();
        widgetBox.left = (int) (widget.getX() * getCanvasWidth()) - stroke;
        widgetBox.top = (int)(widget.getY() * getCanvasHeight()) - stroke;
        widgetBox.right = (int) (widgetBox.left + (widget.getWidth() * getCanvasWidth())) + (stroke * 2);
        widgetBox.bottom = (int) (widgetBox.top + (widget.getHeight() * getCanvasHeight())) + (stroke * 2);
        canvas.drawRect(widgetBox, widgetBoxPaint);

        drawWidgetBoxCorner(canvas, widgetBox.left, widgetBox.top);
        drawWidgetBoxCorner(canvas, widgetBox.right, widgetBox.top);
        drawWidgetBoxCorner(canvas, widgetBox.right, widgetBox.bottom);
        drawWidgetBoxCorner(canvas, widgetBox.left, widgetBox.bottom);
    }

    private void drawWidgetBoxCorner(Canvas canvas, int xCenter, int yCenter){
        byte halfCornerSize = (byte) (getContext().getResources()
                .getInteger(R.integer.builder_canvas_selected_widget_box_corner_size) / 2f);
        widgetBoxCorner.left = xCenter - halfCornerSize;
        widgetBoxCorner.top = yCenter - halfCornerSize;
        widgetBoxCorner.right = xCenter + halfCornerSize;
        widgetBoxCorner.bottom = yCenter + halfCornerSize;
        canvas.drawRect(widgetBoxCorner, widgetBoxCornerPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        moveGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void OnMove(int deltaX, int deltaY) {
        if(selectedWidget != null) {
            moveWidgetRelative(selectedWidget,
                    ServiceClass.getPercent(deltaX, getCanvasWidth()),
                    ServiceClass.getPercent(deltaY, getCanvasHeight()));
        }
    }

    @Override
    public void OnScale(float scaleFactor) {
        selectedWidget.scale(scaleFactor);
    }
}