package com.web_on.template.data;

import android.view.View;

/**
 * Created by samuel.leung on 11/3/2016.
 */
public class DrawerItem {
    public String title = "";
    public int icon = -1;
    public View.OnClickListener onClickListener;

    public DrawerItem(String title, int icon, View.OnClickListener onClickListener) {
        this.title = title;
        this.icon = icon;
        this.onClickListener = onClickListener;
    }


}
