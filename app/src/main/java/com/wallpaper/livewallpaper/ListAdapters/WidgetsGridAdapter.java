package com.wallpaper.livewallpaper.ListAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

public class WidgetsGridAdapter extends BaseAdapter {

    private ArrayList<WidgetCell> cells;
    private LayoutInflater thisInflater;

    public WidgetsGridAdapter(Context context, ArrayList<WidgetCell> cells) {
        this.cells = cells;
        thisInflater = ( LayoutInflater.from(context) );
    }

    @Override
    public int getCount() {
        return cells.size();
    }

    @Override
    public Object getItem(int position) {
        return cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = thisInflater.inflate(R.layout.cell_widgets, parent, false);

        WidgetCell currentCell = (WidgetCell)getItem(position);

        TextView name = (TextView) convertView.findViewById(R.id.cw_name);
        ImageView icon = (ImageView) convertView.findViewById(R.id.cw_icon);

        name.setText(currentCell.getName());
        icon.setImageResource(chooseIcon(convertView.getContext(), currentCell.getType()));

        return convertView;
    }

    private static int chooseIcon(Context context, Widget.WidgetType type){
        return R.drawable.clock;
    }
}
