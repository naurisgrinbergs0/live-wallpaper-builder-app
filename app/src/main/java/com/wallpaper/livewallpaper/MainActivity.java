package com.wallpaper.livewallpaper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wallpaper.livewallpaper.Widget.*;
import com.wallpaper.livewallpaper.Widget.Widget.*;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widget.WidgetTransformation.*;

public class MainActivity extends AppCompatActivity{

    private Button startServiceBtn;
    private BuilderCanvas canvas;
    private Button chooseWidgetBtn;

    private Dialog chooseWidgetDialog;
    private ArrayList<WidgetRow> availableWidgetList;

    private float[] xyPrev = {-1,-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFields();
        setUpWidgetList();
        setUpEventListeners();
    }

    @SuppressLint("ResourceType")
    private void setUpFields(){
        startServiceBtn = findViewById(R.id.setWallpaperBtn);
        canvas = findViewById(R.id.canvas);
        chooseWidgetBtn = findViewById(R.id.chooseWidgetBtn);

        chooseWidgetDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        availableWidgetList = new ArrayList<WidgetRow>();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpEventListeners(){

        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Widget.ALL_ONSCREEN_WIDGETS = canvas.getWidgets();

                // start service
                Intent serviceIntent = new Intent(getApplicationContext(), LiveWallpaperService.class);
                startService(serviceIntent);

                // start wallpaper setter activity
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(getApplicationContext(), LiveWallpaperService.class));
                startActivity(intent);
            }
        });


        canvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float[] xy = {event.getX(), event.getY()};

                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:{
                        xyPrev = new float[]{-1,-1};
                    }
                    case MotionEvent.ACTION_MOVE:{
                        if(xyPrev[0] != -1 && xyPrev[1] != -1)
                            if(canvas.getSelectedWidget() != null) {
                                moveWidgetRelative(canvas.getSelectedWidget(),
                                        Math.getPercent(xy[0] - xyPrev[0], canvas.getWidth()),
                                        Math.getPercent(xy[1] - xyPrev[1], canvas.getHeight()));
                                Log.d("XY", ""
                                        + Math.getValue(canvas.getWidgets().get(0).getX(), canvas.getWidth())
                                        + " " + Math.getValue(canvas.getWidgets().get(0).getY(), canvas.getHeight()));
                            }
                    }
                }

                xyPrev = xy;

                return true;
            }
        });

        chooseWidgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWidgetDialog.setContentView(R.layout.widget_layout);
                chooseWidgetDialog.show();

                ListView widgetListView = chooseWidgetDialog.findViewById(R.id.widgetListView);
                WidgetListAdapter widgetListAdapter = new WidgetListAdapter(getApplicationContext(), availableWidgetList);
                widgetListView.setAdapter(widgetListAdapter);

                setUpWidgetDialogEventListeners();
            }
        });
    }

    private void setUpWidgetList(){
        availableWidgetList.add(new WidgetRow(WidgetType.CLOCK,"Time"));
        availableWidgetList.add(new WidgetRow(WidgetType.TEXT,"Text"));
    }

    private void setUpWidgetDialogEventListeners(){
        chooseWidgetDialog.findViewById(R.id.exitDialogBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWidgetDialog.dismiss();
            }
        });

        ((ListView)chooseWidgetDialog.findViewById(R.id.widgetListView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Widget widget = null;

                switch (((WidgetRow)parent.getItemAtPosition(position)).getItemType()){
                    case CLOCK:{
                        widget = new ClockWidget(context);
                        break;
                    }
                }

                canvas.addWidget(widget);
                canvas.setSelectedWidget(widget);

                chooseWidgetDialog.dismiss();
            }
        });
    }
}
