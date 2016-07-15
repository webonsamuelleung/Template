package com.web_on.template.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.web_on.template.R;

import static com.web_on.template.R.id.mapView;

/**
 * Created by samuel.leung on 10/3/2016.
 */
public class MapFragment extends Fragment {
    private static final String TAG = MapFragment.class.getSimpleName();

    MapView mMapView;
    private GoogleMap googleMap;

    private LatLngBounds AUSTRALIA = new LatLngBounds(new LatLng(-44, 113), new LatLng(-10, 154));
    private static final LatLng SYDNEY = new LatLng(-33.88,151.21);
    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, null);

        //init map
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView = (MapView) view.findViewById(mapView);
        mMapView.onCreate(savedInstanceState);
        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);


        //marker
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("SYDNEY")
                .snippet("Population: 4,137,400")
                .alpha(0.7f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        marker.showInfoWindow();


        view.findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move the camera instantly to Sydney with a zoom of 15.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));
            }
        });

        view.findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zoom in, animating the camera.
                 googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        view.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            }
        });

        view.findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
