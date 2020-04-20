package com.wallpaper.livewallpaper.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallpaper.livewallpaper.ListAdapters.TabsPagerAdapter;
import com.wallpaper.livewallpaper.Views.BuilderCanvas;
import com.wallpaper.livewallpaper.Services.LiveWallpaperService;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;
import com.wallpaper.livewallpaper.ListAdapters.WidgetListAdapter;
import com.wallpaper.livewallpaper.ListAdapters.WidgetRow;
import com.wallpaper.livewallpaper.Widgets.*;
import com.wallpaper.livewallpaper.Widgets.Widget.*;

import java.util.ArrayList;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.*;

public class MainActivity extends AppCompatActivity{

    private BuilderCanvas canvas;
    private Dialog chooseWidgetDialog;
    private ArrayList<WidgetRow> availableWidgetList;
    private ViewPager optionsViewPager;

    private float[] xyPrev = {-1,-1};

    public static float REAL_WIDTH;
    public static float REAL_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setScreenSize();
        setUpFields();
        setUpWidgetList();
        setUpEventListeners();
        setUpPager();
    }

    private void setUpPager() {
        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTabs(new String[]{"Widgets", "Background", "Colors", "A", "B", "Infinite"});
        optionsViewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.optionsTabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(optionsViewPager);
    }

    private void setScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        REAL_HEIGHT = displayMetrics.heightPixels;
        REAL_WIDTH = displayMetrics.widthPixels;
    }

    @SuppressLint("ResourceType")
    private void setUpFields(){
        canvas = findViewById(R.id.canvas);

        chooseWidgetDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        availableWidgetList = new ArrayList<WidgetRow>();
        optionsViewPager = findViewById(R.id.optionsViewPager);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpEventListeners(){
        /*
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Widget.allOnscreenWidgets = canvas.getWidgets();

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
        */

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
                                        ServiceClass.getPercent(xy[0] - xyPrev[0], canvas.getMeasuredWidth()),
                                        ServiceClass.getPercent(xy[1] - xyPrev[1], canvas.getMeasuredHeight()));
                                Log.d("XY", ""
                                        + ServiceClass.getValue(canvas.getWidgets().get(0).getX(), canvas.getMeasuredWidth())
                                        + " " + ServiceClass.getValue(canvas.getWidgets().get(0).getY(), canvas.getMeasuredHeight()));
                            }
                    }
                }

                xyPrev = xy;

                return true;
            }
        });
        /*
        chooseWidgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWidgetDialog.setContentView(R.layout.dialog_widgets);
                chooseWidgetDialog.show();

                ListView widgetListView = chooseWidgetDialog.findViewById(R.id.widgetListView);
                WidgetListAdapter widgetListAdapter = new WidgetListAdapter(getApplicationContext(), availableWidgetList);
                widgetListView.setAdapter(widgetListAdapter);

                setUpWidgetDialogEventListeners();
            }
        });
        */
    }

    private void setUpWidgetList(){
        availableWidgetList.add(new WidgetRow(WidgetType.TEXT,"Text"));
        availableWidgetList.add(new WidgetRow(WidgetType.CLOCK,"Clock"));
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
                    case TEXT:{
                        widget = new TextWidget(context);
                        break;
                    }
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
