package com.wallpaper.livewallpaper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.text.TextPaint;
import android.view.SurfaceHolder;

import androidx.core.content.res.ResourcesCompat;

import com.wallpaper.livewallpaper.Widget.Widget;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Math.*;
import static com.wallpaper.livewallpaper.Widget.WidgetTransformation.*;

public class LiveWallpaperService extends WallpaperService {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_USER_PRESENT)){
                timesUnlocked++;
                delay = true;
            }
        }
    };

    private boolean delay = false;
    private int timesUnlocked = 0;
    private static float textPercent = 50;

    private ArrayList<Widget> widgets;

    private float canvasWidth;
    private float canvasHeight;

    @Override
    public Engine onCreateEngine() {
        widgets = Widget.ALL_ONSCREEN_WIDGETS;
        return new LiveWallpaperEngine();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, filter);
    }

    private class LiveWallpaperEngine extends WallpaperService.Engine{
        private final int frameDuration = 20;
        private boolean visible;
        private SurfaceHolder holder;
        private Handler handler;

        public LiveWallpaperEngine(){
            this.handler = new Handler();
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.holder = surfaceHolder;
        }

        private Runnable liveWallpaperDrawer = new Runnable() {
            @Override
            public void run() {
                drawWallpaper();
            }
        };

        @SuppressLint("NewApi")
        private void drawWallpaper() {
            if(this.visible){
                // if unlock has happened - wait
                if(delay) {
                    sleep(1000);
                    delay = false;
                }

                // get canvas
                Canvas canvas = holder.lockCanvas();
                canvas.save();

                // clear canvas
                canvas.drawColor(Color.BLACK);

                for(int i = 0; i < widgets.size(); i++){
                    widgets.get(i).renderWidget(canvas);
                }

                canvas.restore();
                holder.unlockCanvasAndPost(canvas);

                handler.removeCallbacks(liveWallpaperDrawer);
                handler.postDelayed(liveWallpaperDrawer, this.frameDuration);
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if(visible)
                handler.post(liveWallpaperDrawer);
            else
                handler.removeCallbacks(liveWallpaperDrawer);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(liveWallpaperDrawer);
        }
    }
}
