package com.wallpaper.livewallpaper.GestureDetectors;

import android.content.Context;
import android.view.MotionEvent;

public class ScaleGestureDetector {

    private float scaleFactor = 1.0f;
    private static float minScaleFactor = 0.3f;
    private static float maxScaleFactor = 20.0f;

    private OnScaleGestureListener onScaleGestureListener;
    private android.view.ScaleGestureDetector builtInScaleDetector;

    public ScaleGestureDetector(Context context, OnScaleGestureListener onScaleGestureListener) {
        this.onScaleGestureListener = onScaleGestureListener;
        this.builtInScaleDetector = new android.view.ScaleGestureDetector(context, new ScaleGestureListener());
    }

    public boolean onTouchEvent(MotionEvent event){
        builtInScaleDetector.onTouchEvent(event);
        return true;
    }


    public interface OnScaleGestureListener{
        void OnScale(float scaleFactor);
    }

    private class ScaleGestureListener extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(android.view.ScaleGestureDetector detector) {
            scaleFactor *= builtInScaleDetector.getScaleFactor();
            scaleFactor = Math.max(minScaleFactor, Math.min(scaleFactor, maxScaleFactor));
            onScaleGestureListener.OnScale(scaleFactor);
            return true;
        }
    }
}
