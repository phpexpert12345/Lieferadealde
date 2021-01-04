package com.app.liferdeal.ui.fragment.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.liferdeal.viewmodel.ViewModelFactory;

public class SplashFirstFragment extends Fragment {
    private static final String TAG = "StartupFirstFragment";
   // private SplashViewModel splashViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  splashViewModel = ViewModelFactory.getInstance(getActivity()).create(SplashViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
