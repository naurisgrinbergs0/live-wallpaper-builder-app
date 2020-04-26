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

import com.wallpaper.livewallpaper.Dialogs.AddWidgetDialog;
import com.wallpaper.livewallpaper.ListAdapters.WidgetsRecyclerViewAdapter;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;


public class WidgetsFragment extends Fragment implements WidgetsRecyclerViewAdapter.OnItemClickListener, AddWidgetDialog.OnWidgetSelectListener {
    private RecyclerView recyclerView;
    private AddWidgetDialog addWidgetDialog;
    private WidgetsRecyclerViewAdapter recyclerViewAdapter;

    private OnActionListener onActionListener;

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
        recyclerViewAdapter = new WidgetsRecyclerViewAdapter(getContext(), this);
        recyclerViewAdapter.addData(recyclerViewAdapter.new WidgetDataHolder(R.drawable.add));
        recyclerView.setAdapter(recyclerViewAdapter);

        addWidgetDialog = new AddWidgetDialog(getContext());
        addWidgetDialog.setOnWidgetSelectListener(this);
    }


    private void setEventListeners() {

    }


    @Override
    public void onItemClick(int position) {
        if(position == 0){
            addWidgetDialog.show();
        }
    }


    @Override
    public void onSelect(Widget widget) {
        onActionListener.onWidgetSelectAction(widget);
        recyclerViewAdapter.addData(recyclerViewAdapter.new WidgetDataHolder(widget));
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onWidgetSelectAction(Widget widget);
    }
}
