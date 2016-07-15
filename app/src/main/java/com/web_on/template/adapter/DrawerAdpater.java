package com.web_on.template.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.web_on.template.R;
import com.web_on.template.data.DrawerItem;

import java.util.ArrayList;

/**
 * Created by samuel.leung on 11/3/2016.
 */
public class DrawerAdpater extends BaseAdapter {

    private Context mContext;
    private ArrayList<DrawerItem> drawerItems = new ArrayList<>();

    public DrawerAdpater( Context mContext,ArrayList<DrawerItem> drawerItems) {
        this.mContext = mContext;
        this.drawerItems = drawerItems;
    }

    @Override
    public int getCount() {
        return drawerItems.size();
    }
    @Override
    public Object getItem(int position) {
        return drawerItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.drawer_adapter_row, null);

        ImageLoader imageLoader = ImageLoader.getInstance();

        DrawerItem currentDrawerItem = drawerItems.get(position);

        //title
        TextView textView_title = (TextView) view.findViewById(R.id.textView_title);
        textView_title.setText(currentDrawerItem.title);

        //icon
        ImageView imageView_icon = (ImageView) view.findViewById(R.id.imageView_icon);
        imageLoader.displayImage("drawable://"+currentDrawerItem.icon, imageView_icon);

        //onclick
        textView_title.setOnClickListener( currentDrawerItem.onClickListener );

        return view;
    }
}
