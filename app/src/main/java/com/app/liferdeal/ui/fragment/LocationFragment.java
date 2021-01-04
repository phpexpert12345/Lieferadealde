package com.app.liferdeal.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.liferdeal.R;


public class LocationFragment extends Fragment {
    private static final String TAG = "LocationFragment";
    private int containerID;
    private static final String ARG_PARAM = "";
    private TextView btnAddGps;

    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
        return fragment;
    }

    public static LocationFragment newInstance(String param) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           String paramText = getArguments().getString(ARG_PARAM);
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_fragment, container, false);;
        init(view);
        return view;
    }

    private void init(View view)
    {
        btnAddGps = view.findViewById(R.id.btnAddGps);
        btnAddGps.setOnClickListener(this::onClick);
    }

    public static String getTAG() {
        return TAG;
    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnAddGps:
                initiateHomeFragment();
                break;

            default:
                break;
        }
    }

    private void initiateHomeFragment() {
      //  HomeFragment homeFragment = HomeFragment.newInstance(containerID);
       // LocationMapFragment locationMapFragment = LocationMapFragment.newInstance(containerID);
      /*  LocationMapFragment locationMapFragment = new LocationMapFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, locationMapFragment);
        transaction.addToBackStack(locationMapFragment.getTag());
        transaction.commit();*/
    }
}
