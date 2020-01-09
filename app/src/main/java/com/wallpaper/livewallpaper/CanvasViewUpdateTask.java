package com.wallpaper.livewallpaper;

import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.Widget.Widget;

import java.util.ArrayList;

public class CanvasViewUpdateTask extends AsyncTask {

    private ArrayList<Widget> widgetsToUpdate;

    @Override
    protected Object doInBackground(Object[] objects) {
        while(true){
            publishProgress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        if(widgetsToUpdate != null){
            for(Widget widget : widgetsToUpdate){
                widget.updateView();
            }
        }
    }

    public void setWidgetsToUpdate(ArrayList<Widget> widgetsToUpdate) {
        this.widgetsToUpdate = widgetsToUpdate;
    }
}
