package com.wallpaper.livewallpaper;

import com.wallpaper.livewallpaper.Widget.Widget.*;

public class WidgetRow {
    private String text;
    private WidgetType itemType;

    public WidgetRow(WidgetType itemType, String text){
        this.text = text;
        this.itemType = itemType;
    }

    public WidgetType getItemType(){
        return this.itemType;
    }

    public String getText(){
        return this.text;
    }
    public void setText(String text){
        this.text = text;
    }
}
