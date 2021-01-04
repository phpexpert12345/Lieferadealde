package com.app.liferdeal.network.retrofit;

import com.app.liferdeal.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RFClient {

    private static Retrofit retrofitInstanceOne = null;

    public static Retrofit getClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        /*OkHttpClient clientBuilder=new OkHttpClient();
        try {
            TLSSocketFactory tlsSocketFactory=new TLSSocketFactory();
            if (tlsSocketFactory.getTrustManager()!=null) {
                clientBuilder = new OkHttpClient.Builder()
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .build();
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
*/
        clientBuilder.addInterceptor(HttpLoggingInterceptor.getInterceptor());

        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        //Retrofit retrofit;
        if (retrofitInstanceOne == null) {
            Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            retrofitInstanceOne = new Retrofit.Builder().client(clientBuilder.build()).baseUrl(Constants.Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

          /*  retrofitInstanceOne= new Retrofit.Builder()
                    .baseUrl(Constants.Url.BASE_URL)
                    .client(clientBuilder)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/

        }

        return retrofitInstanceOne;
    }
}
