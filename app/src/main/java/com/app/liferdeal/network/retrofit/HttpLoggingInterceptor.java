package com.app.liferdeal.network.retrofit;

public class HttpLoggingInterceptor {

    public static okhttp3.logging.HttpLoggingInterceptor getInterceptor() {
        okhttp3.logging.HttpLoggingInterceptor loggingInterceptor = new okhttp3.logging.HttpLoggingInterceptor();
        loggingInterceptor.level(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
