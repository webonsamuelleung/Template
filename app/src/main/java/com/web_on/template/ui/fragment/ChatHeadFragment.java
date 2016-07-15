package com.web_on.template.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.web_on.template.R;
import com.web_on.template.service.ChatHeadService;

/**
 * Created by samuel.leung on 11/3/2016.
 */
public class ChatHeadFragment extends Fragment {
    private static final String TAG = ChatHeadFragment.class.getSimpleName();

    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;
    public static Button btnStartService, btnShowMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chathead_fragment, null);

        btnStartService = (Button) view.findViewById(R.id.btnStartService);
        btnShowMsg = (Button) view.findViewById(R.id.btnMsg);

        btnStartService.setOnClickListener(lst_StartService);
        btnShowMsg.setOnClickListener(lst_ShowMsg);

        return view;
    }

    public static boolean canDrawOverlays(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }else{
            return Settings.canDrawOverlays(context);
        }


    }
    Button.OnClickListener lst_StartService = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d(TAG, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + canDrawOverlays(getActivity()));


            if(canDrawOverlays(getActivity())) {
                startChatHead();
            }else{
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
            }
        }

    };


    Button.OnClickListener lst_ShowMsg = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(canDrawOverlays(getActivity()))
                showChatHeadMsg();
            else{
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG);
            }

        }

    };

    private void startChatHead(){
        getActivity().startService(new Intent(getActivity(), ChatHeadService.class));
    }
    private void showChatHeadMsg(){
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        Intent it = new Intent(getActivity(), ChatHeadService.class);
        it.putExtra("EXTRA MSG", str);
        getActivity().startService(it);
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);
            }else{
                startChatHead();
            }

        }else if(requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG){
            if (!canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);
            }else{
                showChatHeadMsg();
            }

        }

    }


}

