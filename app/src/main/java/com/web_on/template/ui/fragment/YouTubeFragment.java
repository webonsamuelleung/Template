package com.web_on.template.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.web_on.template.R;

/**
 * Created by samuel.leung on 10/3/2016.
 */
public class YouTubeFragment extends Fragment {
    private static final String TAG = YouTubeFragment.class.getSimpleName();

    public static final String API_KEY = "AIzaSyD6qB8ZTOgX2Nlr8mFIsNbPvQjyE";
    public static final String VIDEO_ID = "hDDbbwIku2Q";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.youtube_fragment, null);


        YouTubePlayerSupportFragment mYoutubePlayerFragment = new YouTubePlayerSupportFragment();
        mYoutubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(VIDEO_ID);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getActivity(), "Failure to Initialize!", Toast.LENGTH_LONG).show();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_player, mYoutubePlayerFragment);
        fragmentTransaction.commit();


        return view;
    }



}
