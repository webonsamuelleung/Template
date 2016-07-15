package com.web_on.template.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.web_on.template.R;
import com.web_on.template.adapter.DrawerAdpater;
import com.web_on.template.common.CommonHelper;
import com.web_on.template.data.DrawerItem;
import com.web_on.template.ui.fragment.BlueToothFragment;
import com.web_on.template.ui.fragment.CameraFragment;
import com.web_on.template.ui.fragment.ChatHeadFragment;
import com.web_on.template.ui.fragment.HomeFragment;
import com.web_on.template.ui.fragment.MapFragment;
import com.web_on.template.ui.fragment.NfcFragment;
import com.web_on.template.ui.fragment.WindowManagerFragment;
import com.web_on.template.ui.fragment.YouTubeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private ImageView imageView_leftDrawerIcon, imageView_rightDrawerIcon;
    private DrawerLayout drawerLayout;
    private ListView listView_leftDrawer;
    private LinearLayout layout_rightDrawer;
    private FrameLayout frameLayout;
    private float lastTranslate = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///init screen size
        CommonHelper.initScreenSize(MainActivity.this);

        //init imageloader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //init fragment man
        fragmentManager = getSupportFragmentManager();
        CommonHelper.fragmentManager = fragmentManager;


        //init fragment
        CommonHelper.replaceFragment(new HomeFragment(), "HomeFragment", false);


        //findView
        imageView_leftDrawerIcon = (ImageView) findViewById(R.id.imageView_leftDrawerIcon);
        imageView_rightDrawerIcon = (ImageView) findViewById(R.id.imageView_rightDrawerIcon);
        listView_leftDrawer = (ListView) findViewById(R.id.listView_leftDrawer);
        layout_rightDrawer = (LinearLayout) findViewById(R.id.layout_rightDrawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        imageView_leftDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(layout_rightDrawer);

                if(drawerLayout.isDrawerOpen(listView_leftDrawer))
                    drawerLayout.closeDrawer(listView_leftDrawer);
                else
                    drawerLayout.openDrawer(listView_leftDrawer);
            }
        });

        imageView_rightDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(listView_leftDrawer);

                if(drawerLayout.isDrawerOpen(layout_rightDrawer))
                    drawerLayout.closeDrawer(layout_rightDrawer);
                else
                    drawerLayout.openDrawer(layout_rightDrawer);
            }
        });

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (drawerView.getWidth() * slideOffset);

                if(drawerView==layout_rightDrawer){
                    moveFactor *= -1;
                }
//                Log.d(TAG, "moveFactor " + moveFactor);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    frameLayout.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frameLayout.startAnimation(anim);
                    lastTranslate = moveFactor;
                }
            }
            @Override
            public void onDrawerOpened(View drawerView) { }
            @Override
            public void onDrawerClosed(View drawerView) { }
            @Override
            public void onDrawerStateChanged(int newState) {  }
        });

        //left Drawer
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem("HomeFragment", R.drawable.ic_action_home, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new HomeFragment(), "HomeFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("CameraFragment", R.drawable.ic_action_camera, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new CameraFragment(), "CameraFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("ChatHeadFragment", R.drawable.ic_action_emo_evil, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new ChatHeadFragment(), "ChatHeadFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("WindowManagerFragment", R.drawable.ic_action_movie, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new WindowManagerFragment(), "WindowManagerFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("NfcFragment", R.drawable.ic_action_signal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new NfcFragment(), "NfcFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("MapFragment", R.drawable.ic_action_map, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new MapFragment(), "MapFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("YouTubeFragment", R.drawable.ic_action_video, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new YouTubeFragment(), "YouTubeFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        drawerItems.add(new DrawerItem("BlueToothFragment", R.drawable.ic_action_bluetooth, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonHelper.replaceFragment(new BlueToothFragment(), "BlueToothFragment");
                drawerLayout.closeDrawer(listView_leftDrawer);

            }
        }));
        listView_leftDrawer.setAdapter(new DrawerAdpater(MainActivity.this, drawerItems));


        View right_drawer = View.inflate(this, R.layout.right_drawer, null);
        layout_rightDrawer.addView( right_drawer );

    }//onCreate

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

}
