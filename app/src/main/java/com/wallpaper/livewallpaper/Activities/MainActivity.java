package com.wallpaper.livewallpaper.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wallpaper.livewallpaper.Fragments.WidgetsFragment;
import com.wallpaper.livewallpaper.ListAdapters.TabsPagerAdapter;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.ServiceClass;
import com.wallpaper.livewallpaper.Views.BuilderCanvas;
import com.wallpaper.livewallpaper.Widgets.Widget;

public class MainActivity extends AppCompatActivity implements WidgetsFragment.OnActionListener {
    private BuilderCanvas canvas;
    private ViewPager optionsViewPager;

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
    }

    @Override
    public void onWidgetAddAction(Widget widget) {
        canvas.addWidget(widget);
        canvas.setSelectedWidget(widget.getName());
    }

    @Override
    public void onWidgetSelectAction(String widgetName) {
        canvas.setSelectedWidget(widgetName);
    }
}
