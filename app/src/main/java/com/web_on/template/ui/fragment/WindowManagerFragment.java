package com.web_on.template.ui.fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.web_on.template.R;
import com.web_on.template.common.CommonHelper;

/**
 * Created by samuel.leung on 11/3/2016.
 */
public class WindowManagerFragment extends Fragment {
    private static final String TAG = WindowManagerFragment.class.getSimpleName();

    private View windowmanager_view;
    private WindowManager windowManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.windowmanager_fragment, null);

        windowManager = getActivity().getWindowManager();


        windowmanager_view = getActivity().getLayoutInflater().inflate(R.layout.windowmanager_view, null);

        ImageView imageView_cancel = (ImageView) windowmanager_view.findViewById(R.id.imageVIew_cancel);
        imageView_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(windowmanager_view);
            }
        });


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 设置显示格式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        params.gravity = Gravity.LEFT | Gravity.TOP;

        params.width = 150;
        params.height = 150;
//        params.width = CommonHelper.screenWidth;
//        params.height = CommonHelper.screenHeight;

        windowManager.addView(windowmanager_view, params);


        //drag n move
        windowmanager_view.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(windowmanager_view, params);
                        return true;
                }
                return false;
            }
        });


        return view;
    }

}

