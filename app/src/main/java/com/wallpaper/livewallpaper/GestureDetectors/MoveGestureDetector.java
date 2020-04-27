package com.wallpaper.livewallpaper.GestureDetectors;

import android.view.MotionEvent;

public class MoveGestureDetector {
    private OnMoveGestureListener onMoveGestureListener;

    private int xPrev;
    private int yPrev;

    public MoveGestureDetector(OnMoveGestureListener onMoveGestureListener){
        this.onMoveGestureListener = onMoveGestureListener;
    }


    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                xPrev = -1;
                yPrev = -1;
            }
            case MotionEvent.ACTION_MOVE:{
                if(xPrev != -1 && yPrev != -1)
                    if(onMoveGestureListener != null)
                        onMoveGestureListener.OnMove(x - xPrev, y - yPrev);
            }
        }
        xPrev = x;
        yPrev = y;
        return true;
    }


    public interface OnMoveGestureListener{
        void OnMove(int deltaX, int deltaY);
    }
}
