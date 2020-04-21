package com.wallpaper.livewallpaper.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpaper.livewallpaper.R;
import com.wallpaper.livewallpaper.Widgets.Widget;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WidgetsRecyclerViewAdapter extends RecyclerView.Adapter<WidgetsRecyclerViewAdapter.WidgetViewHolder> {

    public ArrayList<WidgetDataHolder> data = new ArrayList<>();
    private Context context;

    public WidgetsRecyclerViewAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public WidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_widgets, parent, false);
        return new WidgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WidgetViewHolder holder, int position) {
        holder.name.setText(data.get(position).name);
        holder.icon.setImageResource(data.get(position).icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class WidgetViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView icon;

        public WidgetViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cw_name);
            icon = itemView.findViewById(R.id.cw_icon);
        }
    }

    public class WidgetDataHolder{
        public String name;
        public int icon;

        public WidgetDataHolder(int icon){
            this.icon = icon;
        }

        public WidgetDataHolder(String name, int icon){
            this.name = name;
            this.icon = icon;
        }
    }
}
