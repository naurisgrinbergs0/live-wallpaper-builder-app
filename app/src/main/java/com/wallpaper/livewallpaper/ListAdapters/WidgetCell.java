package com.wallpaper.livewallpaper.ListAdapters;

import com.wallpaper.livewallpaper.Widgets.Widget.WidgetType;

public class WidgetCell {
    private String name;
    private WidgetType type;

    public WidgetCell(String name, WidgetType type){
        this.name = name;
        this.type = type;
    }

    public WidgetType getType(){
        return this.type;
    }
    public void setType(WidgetType type){
        this.type = type;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
}
