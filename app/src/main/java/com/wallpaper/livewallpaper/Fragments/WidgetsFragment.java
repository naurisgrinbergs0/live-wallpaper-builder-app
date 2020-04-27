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
        if(recyclerViewAdapter.getData(position).getName() == null){
            addWidgetDialog.show();
        }
        else {
            WidgetsRecyclerViewAdapter.WidgetDataHolder item = recyclerViewAdapter.getData(position);
            setNewSelectedWidget(item.getName());
            onActionListener.onWidgetSelectAction(item.getName());
        }
    }

    private void setNewSelectedWidget(String itemName) {
        for(byte i = 0; i < recyclerViewAdapter.getItemCount(); i++) {
            WidgetsRecyclerViewAdapter.WidgetViewHolder vh =
                    (WidgetsRecyclerViewAdapter.WidgetViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            int color = (itemName == recyclerViewAdapter.getData(i).getName())
                    ? getContext().getResources().getColor(R.color.builder_canvas_selected_widget_box_stroke_color)
                    : getContext().getResources().getColor(R.color.light);
            vh.getIconImageView().setColorFilter(color);
            vh.getNameTextView().setTextColor(color);
        }
    }


    @Override
    public void onWidgetAdd(Widget widget) {
        recyclerViewAdapter.addData(recyclerViewAdapter.new WidgetDataHolder(widget));
        onActionListener.onWidgetAddAction(widget);
        //setNewSelectedWidget(widget.getName());
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    public interface OnActionListener{
        void onWidgetAddAction(Widget widget);
        void onWidgetSelectAction(String widgetName);
    }
}
