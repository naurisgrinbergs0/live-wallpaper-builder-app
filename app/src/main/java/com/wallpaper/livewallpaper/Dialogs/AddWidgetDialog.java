package com.wallpaper.livewallpaper.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.wallpaper.livewallpaper.ListAdapters.WidgetListAdapter;
import com.wallpaper.livewallpaper.ListAdapters.WidgetListRow;
import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.ClockWidget;
import com.wallpaper.livewallpaper.Widgets.TextWidget;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

public class AddWidgetDialog extends Dialog {
    private Button cancelButton;
    private ListView availableWidgetListView;
    private WidgetListAdapter widgetListAdapter;
    private ArrayList<WidgetListRow> availableWidgetListRows;

    private OnWidgetSelectListener onWidgetSelectListener;

    public AddWidgetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_widget);

        setFields();
        setAvailableWidgetListRows();
        setEventListeners();
    }

    private void setFields() {
        cancelButton = findViewById(R.id.cancelButton);
        availableWidgetListView = findViewById(R.id.availableWidgetListView);
        availableWidgetListRows = new ArrayList<WidgetListRow>();
        widgetListAdapter = new WidgetListAdapter(getContext(), availableWidgetListRows);
        availableWidgetListView.setAdapter(widgetListAdapter);
    }

    private void setEventListeners() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        availableWidgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getContext();
                Widget widget = null;

                switch (((WidgetListRow)parent.getItemAtPosition(position)).getItemType()){
                    case TEXT:{
                        widget = new TextWidget(context);
                        break;
                    }
                    case CLOCK:{
                        widget = new ClockWidget(context);
                        break;
                    }
                }

                if(onWidgetSelectListener != null)
                    onWidgetSelectListener.onSelect(widget);

                dismiss();
            }
        });
    }


    private void setAvailableWidgetListRows(){
        availableWidgetListRows.add(new WidgetListRow(Widget.WidgetType.TEXT,"Text"));
        availableWidgetListRows.add(new WidgetListRow(Widget.WidgetType.CLOCK,"Clock"));
        widgetListAdapter.notifyDataSetChanged();
    }


    public void setOnWidgetSelectListener(OnWidgetSelectListener onWidgetSelectListener){
        this.onWidgetSelectListener = onWidgetSelectListener;
    }

    public interface OnWidgetSelectListener{
        void onSelect(Widget widget);
    }
}
