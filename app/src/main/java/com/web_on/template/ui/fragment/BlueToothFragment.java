package com.web_on.template.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.web_on.template.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by samuel.leung on 8/7/2016.
 */

public class BlueToothFragment  extends Fragment {
    private static final String TAG = CameraFragment.class.getSimpleName();

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.bluetooth_fragment, null);

        BA = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = BA.getBondedDevices();
        listView = (ListView) view.findViewById(R.id.listView);

        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, 0);

        view.findViewById(R.id.textView_visible).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible(view);
            }
        });

        view.findViewById(R.id.textView_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list(view);
            }
        });

        return view;
    }

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }
    public void list(View v){
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices)
            list.add(bt.getName());
        Toast.makeText(getActivity(),"Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

}
