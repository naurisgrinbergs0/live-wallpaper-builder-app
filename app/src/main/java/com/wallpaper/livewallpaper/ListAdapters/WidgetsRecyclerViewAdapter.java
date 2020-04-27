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

import java.util.ArrayList;

public class WidgetsRecyclerViewAdapter extends RecyclerView.Adapter<WidgetsRecyclerViewAdapter.WidgetViewHolder> {

    private ArrayList<WidgetDataHolder> data = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;


    public void addData(WidgetDataHolder data){
        this.data.add(data);
        notifyDataSetChanged();
    }
    public WidgetDataHolder getData(int index){
        return this.data.get(index);
    }
    public void removeData(int index){
        this.data.remove(index);
        notifyDataSetChanged();
    }




    public WidgetsRecyclerViewAdapter(Context context, OnItemClickListener onItemClickListener){
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_widgets, parent, false);
        return new WidgetViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WidgetViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).name);
        holder.iconImageView.setImageResource(data.get(position).icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class WidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTextView;
        private ImageView iconImageView;
        private OnItemClickListener onItemClickListener;

        public WidgetViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cw_name);
            iconImageView = itemView.findViewById(R.id.cw_icon);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public ImageView getIconImageView() {
            return iconImageView;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public class WidgetDataHolder{
        private String name;
        private int icon;

        public String getName() {
            return name;
        }

        public WidgetDataHolder(int icon){
            this.icon = icon;
        }

        public WidgetDataHolder(Widget widget){
            this.name = widget.getName();
            this.icon = widget.getIcon();
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
