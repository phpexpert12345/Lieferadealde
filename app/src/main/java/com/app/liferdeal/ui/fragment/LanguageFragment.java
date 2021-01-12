package com.app.liferdeal.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.adapters.LanguageAdapter;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LanguageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LanguageFragment";
    private static final String ARG_PARAM = "";
    private ImageView iv_backr;
    private RecyclerView lng_recycle;
    private RadioButton rd_btn;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    private LanguageResponse model = new LanguageResponse();

    public LanguageFragment() {
    }

    public static LanguageFragment newInstance() {
        LanguageFragment fragment = new LanguageFragment();
        return fragment;
    }

    public static LanguageFragment newInstance(String param) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.language_fragment);
        // intializingView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.language_fragment, container, false);
        if (App.retrieveLangFromGson(getActivity()) != null) {
            model = App.retrieveLangFromGson(getActivity());
        }
        intializingView(view);
        return view;
    }

    private void intializingView(View v) {
        prefsHelper = new PrefsHelper(getActivity());
        iv_backr = v.findViewById(R.id.iv_backr);
        lng_recycle = v.findViewById(R.id.rcv_language_listview);
//        rd_btn = v.findViewById(R.id.rd_btn);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        lng_recycle.setLayoutManager(mLayoutManager);
        lng_recycle.setItemAnimator(new DefaultItemAnimator());
        iv_backr.setOnClickListener(this);
        getLanguageList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_backr:
               /* Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/
                // initiateHomeFragment();
                break;

            default:
                break;
        }
    }

    private void getLanguageList() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<LanguageModel> observable = apiInterface.getLanguageList(prefsHelper.getPref(Constants.API_KEY));

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LanguageModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LanguageModel searchResult) {
                        showProgress();
                        setAdapterCategory(searchResult.getLanguageListList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(model.getPlease_wait_text().trim() + "...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private void setAdapterCategory(List<LanguageModel.LanguageListList> list) {
//        LanguageAdapter adapterCategory = new LanguageAdapter(getActivity(), list, selectedLang);
//        lng_recycle.setAdapter(adapterCategory);


        hideProgress();
    }

    public static String getTAG() {
        return TAG;
    }

    private void initiateHomeFragment() {

        // Constants.getMenuNavigasi();
       /* LocationMapFragment locationMapFragment = new LocationMapFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, locationMapFragment);
        transaction.addToBackStack(locationMapFragment.getTag());
        transaction.commit();*/
    }

}
