package com.wallpaper.livewallpaper.GestureDetectors;

import android.view.MotionEvent;

public class MoveGestureDetector {
    private OnMoveGestureListener onMoveGestureListener;

    private int pointerId;
    private boolean isMotionEnabled;
    private int xPrev;
    private int yPrev;

    public MoveGestureDetector(OnMoveGestureListener onMoveGestureListener){
        this.onMoveGestureListener = onMoveGestureListener;
    }


    public boolean onTouchEvent(MotionEvent event){
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                isMotionEnabled = true;
                pointerId = event.getPointerId(event.getActionIndex()); // get main pointer id
                xPrev = -1;
                yPrev = -1;
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                isMotionEnabled = false; // disables move if another pointer is down
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if(isMotionEnabled) { // if movement enabled and pointer id accords to pointer
                    int x = (int) event.getX(event.findPointerIndex(pointerId));
                    int y = (int) event.getY(event.findPointerIndex(pointerId));
                    if (xPrev != -1 && yPrev != -1) { // if new movement not started
                        if (onMoveGestureListener != null)
                            onMoveGestureListener.OnMove(x - xPrev, y - yPrev);
                    }
                    xPrev = x;
                    yPrev = y;
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                isMotionEnabled = false; // when main pointer is up - disable movement
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:{
                xPrev = -1;
                yPrev = -1;
            }
        }
        return true;
    }


    public interface OnMoveGestureListener{
        void OnMove(int deltaX, int deltaY);
    }
}
