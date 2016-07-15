package com.web_on.template.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.web_on.template.R;

/**
 * Created by samuel.leung on 10/3/2016.
 */
public class SecondFragment extends Fragment {
    private static final String TAG = SecondFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, null);

        return view;
    }

}
