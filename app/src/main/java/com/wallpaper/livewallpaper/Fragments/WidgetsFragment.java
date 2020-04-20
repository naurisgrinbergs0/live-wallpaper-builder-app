package com.wallpaper.livewallpaper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wallpaper.livewallpaper.ListAdapters.WidgetCell;
import com.wallpaper.livewallpaper.ListAdapters.WidgetsGridAdapter;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

public class WidgetsFragment extends Fragment {

    private GridView widgetsGridView;
    private WidgetsGridAdapter widgetsGridAdapter;
    private ArrayList<WidgetCell> cells;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_widgets, container, false);

        setFields(rootView);
        setEventListeners();


        return rootView;
    }

    private void setEventListeners() {
    }

    private void setFields(View root) {
        widgetsGridView = root.findViewById(R.id.widgetsGridView);
        cells = new ArrayList<>(); // add items from somewhere
        cells.add(new WidgetCell("cell", Widget.WidgetType.CLOCK));
        cells.add(new WidgetCell("cell", Widget.WidgetType.CLOCK));
        cells.add(new WidgetCell("cell", Widget.WidgetType.CLOCK));
        cells.add(new WidgetCell("cell", Widget.WidgetType.CLOCK));
        cells.add(new WidgetCell("cell", Widget.WidgetType.CLOCK));
        widgetsGridAdapter = new WidgetsGridAdapter(getContext(), cells);
        widgetsGridView.setAdapter(widgetsGridAdapter);
    }
}
