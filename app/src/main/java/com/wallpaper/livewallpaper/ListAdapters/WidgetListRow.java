package com.wallpaper.livewallpaper.ListAdapters;

import com.wallpaper.livewallpaper.Widgets.Widget;

public class WidgetListRow {
    private String text;
    private Widget.WidgetType itemType;

    public WidgetListRow(Widget.WidgetType itemType, String text){
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