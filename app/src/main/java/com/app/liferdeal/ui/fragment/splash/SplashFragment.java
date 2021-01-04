/*
package com.app.liferdeal.ui.fragment.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.liferdeal.R;
import com.app.liferdeal.databinding.SpalshLayoutBinding;
import com.app.liferdeal.viewmodel.SplashViewModel;
import com.app.liferdeal.viewmodel.ViewModelFactory;

public class SplashFragment extends Fragment {
    private SpalshLayoutBinding spalshLayoutBinding;
    private SplashViewModel splashViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = ViewModelFactory.getInstance(getActivity()).create(SplashViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        spalshLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.spalsh_layout, container, false);
        init();
        return spalshLayoutBinding.getRoot();
    }

    private void init()
    {

    }
}
*/
