package com.app.liferdeal.ui.adapters.splashadapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.liferdeal.ui.fragment.splash.SplashFirstFragment;
import com.app.liferdeal.ui.fragment.splash.SplashSecondFragment;
import com.app.liferdeal.ui.fragment.splash.SplashThirdFragment;

public class SplashFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private SplashFirstFragment splashFirstFragment;
    private SplashSecondFragment splashSecondFragment;
    private SplashThirdFragment splashThirdFragment;

    public SplashFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragmentManager =fm;
        splashFirstFragment = new SplashFirstFragment();
        splashSecondFragment = new SplashSecondFragment();
        splashThirdFragment = new SplashThirdFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return splashFirstFragment;
            case 1:
              //  return splashSecondFragment;
            case 2:
                //return splashThirdFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
