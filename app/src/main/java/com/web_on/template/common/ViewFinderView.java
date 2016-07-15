package com.web_on.template.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Samuel on 28/5/2016.
 */
public class ViewFinderView extends View {
    public static final String TAG = ViewFinderView.class.getSimpleName();
    private Rect _touchRect = null;
    private int _storkeWidth = 0;
    private int _color = 0;

    public ViewFinderView(Context context) {
        super(context);
    }

    public ViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRect(Rect touchRect, int storkeWidth, int color) {
        _touchRect = touchRect;
        _storkeWidth = storkeWidth;
        _color = color;
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (_touchRect != null) {
            //canvas.drawColor(0, Mode.CLEAR);
            Log.d(TAG, "onDraw");
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(_color);
            paint.setStrokeWidth(_storkeWidth);
            canvas.drawRect(_touchRect, paint);

        }
    }

}
