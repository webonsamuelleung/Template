package com.web_on.template.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.web_on.template.R;

/**
 * Created by samuel.leung on 10/3/2016.
 */
public class CommonHelper {
    public static final String TAG = CommonHelper.class.getSimpleName();
    public static FragmentManager fragmentManager = null;
    public static int screenWidth = 0, screenHeight = 0;

    public static void initScreenSize(Context mContext){
        screenWidth = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth();
        screenHeight = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getHeight();
        Log.d(TAG, "Screeen Width: " + screenWidth + " height: " + screenHeight);
    }

    public static void replaceFragment(Fragment fragment, String fragmentName){
        replaceFragment(fragment, fragmentName, true);
    }

    public static void replaceFragment(Fragment fragment, String fragmentName, Boolean addToBackStack){
        if(fragmentManager == null){
            Log.d(TAG, "fragmentManager " + fragmentManager);
            return ;
        }
        Log.d(TAG, "ReplaceFragment " + fragmentName);

        if(addToBackStack){
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(fragmentName)
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
        }

    }

    public static DisplayImageOptions.Builder getDisplayImageBuilder() {
        return new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_action_picture).showImageOnFail(R.drawable.ic_action_picture).cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(200)).bitmapConfig(Bitmap.Config.RGB_565);
    }

}
