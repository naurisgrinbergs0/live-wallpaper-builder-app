package com.wallpaper.livewallpaper.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.GestureDetectors.MoveGestureDetector;
import com.wallpaper.livewallpaper.GestureDetectors.RotateGestureDetector;
import com.wallpaper.livewallpaper.GestureDetectors.ScaleGestureDetector;
import com.wallpaper.livewallpaper.Guides.GuideBox;
import com.wallpaper.livewallpaper.Guides.GuideLine;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;
import java.util.Hashtable;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.moveWidgetRelative;

public class BuilderCanvas extends View
        implements MoveGestureDetector.OnMoveGestureListener,
        ScaleGestureDetector.OnScaleGestureListener, RotateGestureDetector.OnRotateGestureListener {

    private ArrayList<Widget> widgets;
    private Widget selectedWidget;

    private Hashtable<GuideLine.Type, GuideLine> guideLines;
    private GuideBox guideBox;

    private Rect canvasBox;

    private MoveGestureDetector moveGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private RotateGestureDetector rotateGestureDetector;

    private static int POSITION_SNAP_THRESHOLD = 15; // pixels
    private static int ROTATION_SNAP_THRESHOLD = 2; // degrees

    @RequiresApi(api = Build.VERSION_CODES.O)
    public BuilderCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        // initial values
        guideBox = new GuideBox(getResources().getColor(R.color.builder_canvas_selected_widget_box_stroke_color),
                getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_width),
                getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_dash),
                getResources().getInteger(R.integer.builder_canvas_selected_widget_box_stroke_gap),
                getResources().getColor(R.color.builder_canvas_selected_widget_box_corner_color),
                getResources().getInteger(R.integer.builder_canvas_selected_widget_box_corner_size)
                );

        widgets = new ArrayList<Widget>();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), this);
        moveGestureDetector = new MoveGestureDetector(this);
        rotateGestureDetector = new RotateGestureDetector(this);
        guideLines = GuideLine.getInitGuideLines(getContext());
    }

    public void addWidget(Widget widget){
        widget.setCanvasBox(canvasBox);
        widget.init();
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets(){
        return widgets;
    }


    public void setSelectedWidget(String name) {
        for(Widget widget : widgets) {
            if (widget.getName().equals(name)) {
                selectedWidget = widget;
                scaleGestureDetector.setScaleFactor(widget.getScale());
            }
        }
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

        // recalculate guideLines
        recalculateGuides();
        // draw guideLines
        drawGuideLines(canvas);
        // draw box if needed
        if(selectedWidget != null)
            drawGuideBox(selectedWidget, canvas);

        // draw all the widgets
        for(Widget widget : widgets)
            drawWidget(widget, canvas);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(getCanvasWidth(), getCanvasHeight());
        layoutParams.leftToLeft = ((View)getParent()).getId();
        layoutParams.rightToRight = ((View)getParent()).getId();
        setLayoutParams(layoutParams);

        invalidate();
    }

    private void recalculateGuides() {
        // loop throught all widgets for position guideLines
        for(Widget w : widgets){
            if(selectedWidget == w)
                continue;

            // calculate distances and
            // check if the selected widget is within snapping threshold
            boolean snapLeftToLeft = Math.abs(selectedWidget.getLeft() - w.getLeft()) <= POSITION_SNAP_THRESHOLD;
            boolean snapLeftToRight = Math.abs(selectedWidget.getLeft() - w.getRight()) <= POSITION_SNAP_THRESHOLD;
            boolean snapRightToRight = Math.abs(selectedWidget.getRight() - w.getRight()) <= POSITION_SNAP_THRESHOLD;
            boolean snapRightToLeft = Math.abs(selectedWidget.getRight() - w.getLeft()) <= POSITION_SNAP_THRESHOLD;
            boolean snapTopToTop = Math.abs(selectedWidget.getTop() - w.getTop()) <= POSITION_SNAP_THRESHOLD;
            boolean snapTopToBottom = Math.abs(selectedWidget.getTop() - w.getBottom()) <= POSITION_SNAP_THRESHOLD;
            boolean snapBottomToBottom = Math.abs(selectedWidget.getBottom() - w.getBottom()) <= POSITION_SNAP_THRESHOLD;
            boolean snapBottomToTop = Math.abs(selectedWidget.getBottom() - w.getTop()) <= POSITION_SNAP_THRESHOLD;

            GuideLine leftGuide = guideLines.get(GuideLine.Type.LEFT);
            GuideLine rightGuide = guideLines.get(GuideLine.Type.RIGHT);
            GuideLine topGuide = guideLines.get(GuideLine.Type.TOP);
            GuideLine bottomGuide = guideLines.get(GuideLine.Type.BOTTOM);

            leftGuide.setVisible(false);
            rightGuide.setVisible(false);
            topGuide.setVisible(false);
            bottomGuide.setVisible(false);

            int halfHoriz = (selectedWidget.getRight() - selectedWidget.getLeft()) / 2;
            int halfVert = (selectedWidget.getBottom() - selectedWidget.getTop()) / 2;

            // calculate guide line start and end xy
            // also set selected widget position
            if(snapLeftToLeft || snapRightToLeft) {
                leftGuide.setxStart(w.getLeft());
                leftGuide.setyStart(0);
                leftGuide.setxEnd(w.getLeft());
                leftGuide.setyEnd(w.getCanvasBox().height());
                leftGuide.setVisible(true);
                selectedWidget.setX(w.getLeft() + ((snapLeftToLeft) ? halfVert : -halfVert));
            }
            if(snapRightToRight || snapLeftToRight) {
                rightGuide.setxStart(w.getRight());
                rightGuide.setyStart(0);
                rightGuide.setxEnd(w.getRight());
                rightGuide.setyEnd(w.getCanvasBox().height());
                rightGuide.setVisible(true);
                selectedWidget.setX(w.getRight() + ((snapRightToRight) ? -halfVert : halfVert));
            }
            if(snapTopToTop || snapBottomToTop) {
                topGuide.setxStart(0);
                topGuide.setyStart(w.getTop());
                topGuide.setxEnd(w.getCanvasBox().width());
                topGuide.setyEnd(w.getTop());
                topGuide.setVisible(true);
                selectedWidget.setY(w.getTop() + ((snapTopToTop) ? halfHoriz : -halfHoriz));
            }
            if(snapBottomToBottom || snapTopToBottom) {
                bottomGuide.setxStart(0);
                bottomGuide.setyStart(w.getBottom());
                bottomGuide.setxEnd(w.getCanvasBox().width());
                bottomGuide.setyEnd(w.getBottom());
                bottomGuide.setVisible(true);
                selectedWidget.setY(w.getBottom() + ((snapBottomToBottom) ? -halfHoriz : halfHoriz));
            }
        }
    }


    private void drawWidget(Widget widget, Canvas canvas){
        canvas.save();

        canvas.translate(widget.getX(), widget.getY());
        canvas.rotate(-widget.getRotation());
        canvas.scale(widget.getScale(), widget.getScale(), -widget.getWidth() / 2f, widget.getHeight() / 2f);
        widget.draw(canvas);

        canvas.restore();
    }

    private void drawGuideBox(Widget widget, Canvas canvas){
        canvas.save();
        canvas.rotate(-widget.getRotation(), widget.getX(), widget.getY());

        int stroke = getResources().getInteger(R.integer.builder_canvas_position_guide_stroke_width);
        int halfWidth = (int) (widget.getWidth() / 2);
        int halfHeight = (int) (widget.getHeight() / 2);

        guideBox.setLeft(widget.getX() - halfWidth - stroke);
        guideBox.setRight(widget.getX() + halfWidth + stroke);
        guideBox.setTop(widget.getY() - halfHeight - stroke);
        guideBox.setBottom(widget.getY() + halfHeight + stroke);

        guideBox.draw(canvas);

        canvas.restore();
    }

    private void drawGuideLines(Canvas canvas){
        for(GuideLine g : guideLines.values())
            if(g.isVisible())
                g.draw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        moveGestureDetector.onTouchEvent(event);
        rotateGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void OnMove(int deltaX, int deltaY) {
        if(selectedWidget != null)
            moveWidgetRelative(selectedWidget, deltaX, deltaY);
    }

    @Override
    public void OnScale(float scaleFactor) {
        if(selectedWidget != null)
            selectedWidget.setScale(scaleFactor);
    }

    @Override
    public void OnRotate(float degrees) {
        if(selectedWidget != null)
            selectedWidget.setRotation(selectedWidget.getRotation() + degrees);
    }
}