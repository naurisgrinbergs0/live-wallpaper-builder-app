package com.wallpaper.livewallpaper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
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
import static com.wallpaper.livewallpaper.Math.*;

public class MainActivity extends AppCompatActivity{

    private Button startServiceBtn;
    private ConstraintLayout canvas;
    private Button chooseWidgetBtn;

    private Widget selectedWidget;

    private Dialog chooseWidgetDialog;
    private ArrayList<WidgetRow> availableWidgetList;
    private ArrayList<Widget> onscreenWidgetList;

    private float xPrev = -1;
    private float yPrev = -1;

    private CanvasViewUpdateTask cvut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFields();
        setUpWidgetList();
        setUpEventListeners();
        setUpCanvas();
        startBackgroundUpdates();
    }

    @SuppressLint("ResourceType")
    private void setUpFields(){
        startServiceBtn = findViewById(R.id.setWallpaperBtn);
        canvas = findViewById(R.id.canvas);
        chooseWidgetBtn = findViewById(R.id.chooseWidgetBtn);

        chooseWidgetDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        availableWidgetList = new ArrayList<WidgetRow>();
        onscreenWidgetList = new ArrayList<Widget>();

        cvut = new CanvasViewUpdateTask();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpEventListeners(){

        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Widget.ALL_ONSCREEN_WIDGETS = onscreenWidgetList;

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
                float x = event.getX();
                float y = event.getY();

                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:{
                        xPrev = -1;
                        yPrev = -1;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        if(xPrev != -1 && yPrev != -1)
                            if(selectedWidget != null) {
                                moveWidgetRelative(selectedWidget,
                                        Math.getPercent(x - xPrev, canvas.getWidth()),
                                        Math.getPercent(y - yPrev, canvas.getHeight()));
                                selectedWidget.updateView();
                            }
                    }
                }

                xPrev = x;
                yPrev = y;

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

    private void setUpCanvas(){
        findViewById(R.id.canvasContainer).post(new Runnable() {
            @Override
            public void run() {
                Display display = getWindowManager().getDefaultDisplay();
                int height = 0;
                int Width = 0;
                int X = 0;
                while(height <= 0 || Width <= 0){
                    height = findViewById(R.id.canvasContainer).getHeight();
                    Width = findViewById(R.id.canvasContainer).getWidth();
                    X = (int) findViewById(R.id.canvasContainer).getX();
                }
                int width = (display.getWidth() * height) / display.getHeight();
                canvas.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));

                float x = X + (Width / 2) - (width / 2);
                moveWidgetAbsolute(canvas, x, canvas.getY());
            }
        });
    }

    private void startBackgroundUpdates(){
        cvut.execute();
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
                Widget widget = Widget.createWidget(
                        ((WidgetRow)parent.getItemAtPosition(position)).getItemType(), getApplicationContext(), canvas);
                widget.setParent(canvas);
                onscreenWidgetList.add(widget);
                widget.placeView();
                selectedWidget = widget;

                cvut.setWidgetsToUpdate(onscreenWidgetList);

                chooseWidgetDialog.dismiss();
            }
        });
    }
}
