package com.wallpaper.livewallpaper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpaper.livewallpaper.ListAdapters.WidgetsRecyclerViewAdapter;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;


public class WidgetsFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_widgets, container, false);

        setFields(rootView);
        setEventListeners();


        return rootView;
    }

    private void setFields(View root) {
        recyclerView = root.findViewById(R.id.widgetsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        WidgetsRecyclerViewAdapter adapter = new WidgetsRecyclerViewAdapter(getContext());
        adapter.data.add(adapter.new WidgetDataHolder(R.drawable.add));
        recyclerView.setAdapter(adapter);
    }

    private void setEventListeners() {

    }
}
