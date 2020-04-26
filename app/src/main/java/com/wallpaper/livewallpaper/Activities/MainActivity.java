package com.wallpaper.livewallpaper.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallpaper.livewallpaper.Fragments.WidgetsFragment;
import com.wallpaper.livewallpaper.ListAdapters.TabsPagerAdapter;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;
import com.wallpaper.livewallpaper.Views.BuilderCanvas;
import com.wallpaper.livewallpaper.Widgets.Widget;

import static com.wallpaper.livewallpaper.Widgets.WidgetTransformation.moveWidgetRelative;

public class MainActivity extends AppCompatActivity implements WidgetsFragment.OnActionListener {

    private BuilderCanvas canvas;
    private ViewPager optionsViewPager;

    private float[] xyPrev = {-1,-1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFields();
        setUpEventListeners();
        setUpPager();
        ServiceClass.setScreenSize(this);
    }

    private void setUpPager() {
        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTabs(new String[]{"Widgets", "Background", "Colors", "A", "B", "Infinite"});
        pagerAdapter.setWidgetsFragmentOnActionListener(this);
        optionsViewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.optionsTabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(optionsViewPager);
    }


    @SuppressLint("ResourceType")
    private void setUpFields(){
        canvas = findViewById(R.id.canvas);
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
    }

    @Override
    public void onWidgetSelectAction(Widget widget) {
        canvas.addWidget(widget);
        canvas.setSelectedWidget(widget);
    }
}
