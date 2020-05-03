package com.wallpaper.livewallpaper.Guides;

import android.graphics.Canvas;

public abstract class Guide {
    protected boolean isVisible;

    public Guide(){
        this.isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public abstract void draw(Canvas canvas);
}
