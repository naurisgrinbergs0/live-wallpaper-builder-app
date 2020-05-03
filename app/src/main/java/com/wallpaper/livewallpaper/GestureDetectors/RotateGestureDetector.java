package com.wallpaper.livewallpaper.GestureDetectors;

import android.view.MotionEvent;

public class RotateGestureDetector {
    private OnRotateGestureListener onRotateGestureListener;

    private int pointer1Id;
    private int pointer2Id;
    private boolean isRotationEnabled = true;

    private int x1Prev;
    private int y1Prev;
    private int x2Prev;
    private int y2Prev;

    private static float ROTATION_FACTOR = 1f;

    public RotateGestureDetector(OnRotateGestureListener onRotateGestureListener){
        this.onRotateGestureListener = onRotateGestureListener;
    }


    public boolean onTouchEvent(MotionEvent event){
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:{
                pointer1Id = event.getPointerId(event.getActionIndex());
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:{
                pointer2Id = event.getPointerId(event.getActionIndex());
                x1Prev = (int) event.getX(event.findPointerIndex(pointer1Id));
                y1Prev = (int) event.getY(event.findPointerIndex(pointer1Id));
                x2Prev = (int) event.getX(event.findPointerIndex(pointer2Id));
                y2Prev = (int) event.getY(event.findPointerIndex(pointer2Id));
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if(isRotationEnabled) {
                    try {
                        int x1 = (int) event.getX(event.findPointerIndex(pointer1Id));
                        int y1 = (int) event.getY(event.findPointerIndex(pointer1Id));
                        int x2 = (int) event.getX(event.findPointerIndex(pointer2Id));
                        int y2 = (int) event.getY(event.findPointerIndex(pointer2Id));

                        if(onRotateGestureListener != null)
                            onRotateGestureListener.OnRotate(
                                    angleBetweenLines(x1Prev, y1Prev, x2Prev, y2Prev, x1, y1, x2, y2)
                                            * ROTATION_FACTOR);

                        x1Prev = x1;
                        y1Prev = y1;
                        x2Prev = x2;
                        y2Prev = y2;
                    }catch (Exception e){
                        break;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                pointer1Id = -1;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointer2Id = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                pointer1Id = -1;
                pointer2Id = -1;
                break;
        }
        return true;
    }


    private static float angleBetweenLines(float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY)
    {
        float angle1 = (float) Math.atan2( (fY - sY), (fX - sX) );
        float angle2 = (float) Math.atan2( (nfY - nsY), (nfX - nsX) );

        float angle = ((float)Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return angle;
    }


    public interface OnRotateGestureListener{
        void OnRotate(float degrees);
    }
}
