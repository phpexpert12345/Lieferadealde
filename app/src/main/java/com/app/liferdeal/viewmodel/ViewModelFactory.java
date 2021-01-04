package com.app.liferdeal.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final static Map<Class<? extends ViewModel>, ViewModel> mFactory = new HashMap<>();

    private static Context context;

    private static ViewModelFactory factory;

    private ViewModelFactory(Context context) {
        this.context = context;
    }

    public static ViewModelFactory getInstance(Context con) {

        if (factory == null) {
            context = con;
            factory = new ViewModelFactory(context);
        }

        return factory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(final @NonNull Class<T> modelClass) {

        T viewModel;

        if (mFactory.containsKey(modelClass)) {
            viewModel = (T) mFactory.get(modelClass);
        } else {
            viewModel = ViewModelFactory.getInstance((Application) context).create(modelClass);
            mFactory.put(modelClass, viewModel);
        }
        return viewModel;
    }

}
