package com.wallpaper.livewallpaper.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wallpaper.livewallpaper.R;

import java.util.ArrayList;

public class WidgetListAdapter extends BaseAdapter {

    private ArrayList<WidgetListRow> singleRow;
    private LayoutInflater thisInflater;

    public WidgetListAdapter(Context context, ArrayList<WidgetListRow> aRow) {
        this.singleRow = aRow;
        thisInflater = ( LayoutInflater.from(context) );
    }

    @Override
    public int getCount() {
        return singleRow.size();
    }

    @Override
    public Object getItem(int position) {
        return singleRow.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = thisInflater.inflate(R.layout.row_widgets, parent, false);
            TextView rowText = (TextView)convertView.findViewById(R.id.rowText);

            WidgetListRow currentRow = (WidgetListRow)getItem(position);
            rowText.setText(currentRow.getText());
        }

        return convertView;
    }
}
