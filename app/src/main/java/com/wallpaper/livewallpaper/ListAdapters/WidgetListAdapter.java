package com.wallpaper.livewallpaper.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.util.ArrayList;

public class WidgetListAdapter extends BaseAdapter {

    private ArrayList<WidgetRow> singleRow;
    private LayoutInflater thisInflater;

    public WidgetListAdapter(Context context, ArrayList<WidgetRow> aRow) {
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

            WidgetRow currentRow = (WidgetRow)getItem(position);
            rowText.setText(currentRow.getText());
        }

        return convertView;
    }


    public class WidgetRow {
        private String text;
        private Widget.WidgetType itemType;

        public WidgetRow(Widget.WidgetType itemType, String text){
            this.text = text;
            this.itemType = itemType;
        }

        public Widget.WidgetType getItemType(){
            return this.itemType;
        }

        public String getText(){
            return this.text;
        }
        public void setText(String text){
            this.text = text;
        }
    }
}
