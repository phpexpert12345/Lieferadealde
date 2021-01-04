package com.app.liferdeal.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.liferdeal.R;


public class TemanDekatSekaliFragment extends Fragment {

    private static final String ARG_PARAM = "";

    public static TemanDekatSekaliFragment newInstance() {
        TemanDekatSekaliFragment fragment = new TemanDekatSekaliFragment();
        return fragment;
    }

    public static TemanDekatSekaliFragment newInstance(String param) {
        TemanDekatSekaliFragment fragment = new TemanDekatSekaliFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    String paramText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramText = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teman_dekat_sekali_fragment, container, false);;
        TextView paramView = (TextView) view.findViewById(R.id.param);
        paramView.setText(paramText);

        return view;
    }
}
