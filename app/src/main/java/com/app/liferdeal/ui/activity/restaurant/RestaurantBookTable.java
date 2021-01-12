package com.app.liferdeal.ui.activity.restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.restaurant.RestaurantBookTableModel;
import com.app.liferdeal.model.restaurant.TimeListModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.activity.ThankuActivity;
import com.app.liferdeal.ui.activity.cart.CartCheckout;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantBookTable extends AppCompatActivity implements View.OnClickListener {

    private TextView edit_name, edit_email_addres, edit_mobile, edit_no_person, edit_specia_instruct, tv_faq, tvBookingDate, tvBookingTime, tv_cart_item_count;
    private Button btn_submit;
    private ImageView iv_back;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    //    private TextInputEditText edit_book_date, edit_book_time;
    private String restBookLimit = "", restid = "", customerId = "", strName = "", stremail = "", strPhone = "", strBookDate = "", strBookTime = "", strNumberofPerson = "", strSpecialInstruct = "";
    private LanguageResponse model = new LanguageResponse();
    private PrefsHelper authPreference;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    Spinner spinner_tableSeat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_a_table);
        if (App.retrieveLangFromGson(RestaurantBookTable.this) != null) {
            model = App.retrieveLangFromGson(RestaurantBookTable.this);
        }
        authPreference = new PrefsHelper(this);
        init();
    }

    private void init() {
        prefsHelper = new PrefsHelper(this);
        progressDialog = new ProgressDialog(RestaurantBookTable.this);
        spinner_tableSeat = findViewById(R.id.spinner_tableSeat);
        iv_back = findViewById(R.id.iv_back);
        btn_submit = findViewById(R.id.btn_submit);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingTime = findViewById(R.id.tvBookingTime);
        edit_name = findViewById(R.id.edit_name);
        edit_email_addres = findViewById(R.id.edit_email_addres);
        edit_mobile = findViewById(R.id.edit_mobile);
        tv_cart_item_count = findViewById(R.id.tv_cart_item_count);
//        edit_book_date = findViewById(R.id.edit_book_date);
//        edit_book_time = findViewById(R.id.edit_book_time);
        //edit_no_person = findViewById(R.id.edit_no_person);
        edit_specia_instruct = findViewById(R.id.edit_specia_instruct);
        tv_faq = findViewById(R.id.tv_faq);

        tv_faq.setText(model.getBookATable());
        edit_name.setHint(model.getCustomerName());
        edit_email_addres.setHint(model.getEmailAddress());
        edit_mobile.setHint(model.getMobileNo());
//        edit_book_date.setHint(model.getBookingDate());
//        edit_book_time.setHint(model.getBookingTime());
        //edit_no_person.setHint(model.getNoOfPerson());
        edit_specia_instruct.setHint(model.getSpecialInstructions());
        tvBookingTime.setHint(model.getBookingTime());
        tvBookingDate.setHint(model.getBookingDate());
        btn_submit.setText(model.getSubmit());
        tv_cart_item_count.setText("" + AddExtraActivity.cart_Item_number);

       /* edit_no_person.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    if (Integer.parseInt(s.toString()) > 0 && Integer.parseInt(s.toString()) < 4) {
                        edit_no_person.setError(null);
                    } else {
                        edit_no_person.setError("value sholud be between 1 to 3");
                    }
                }
            }
        });*/

//        edit_book_date.setInputType(InputType.TYPE_NULL);
//        edit_book_time.setInputType(InputType.TYPE_NULL);

        customerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
        restBookLimit = getIntent().getStringExtra("RESTBOOKLIMIT");
        restid = getIntent().getStringExtra("clickRestId");

        //Log.e("TAGVIEW=",restBookLimit);
        iv_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
//        edit_book_date.setOnFocusChangeListener(this);
        tvBookingDate.setOnClickListener(this);
        tvBookingTime.setOnClickListener(this);
//        edit_book_time.setOnFocusChangeListener(this);
//        edit_no_person.setOnFocusChangeListener(this);

        edit_email_addres.setText(authPreference.getPref(Constants.USER_EMAIL));
        edit_name.setText(authPreference.getPref(Constants.USER_NAME));
        edit_mobile.setText(authPreference.getPref(Constants.USER_MOBILE));

        ArrayList tableSize = new ArrayList();
        for (int i = 1; i <= Integer.parseInt(restBookLimit); i++) {
            tableSize.add(i + "");
        }

        TagSpinnerAdapter spnner_role_adapter = new TagSpinnerAdapter(getApplication(), android.R.layout.simple_spinner_item, tableSize);
        spnner_role_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner_tableSeat.setAdapter(spnner_role_adapter);

    }

    public void showProgress() {
        progressDialog.setMessage(model.getPlease_wait_text()+"...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private TimePickerDialog mTimePicker;

    int x = 10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == x) {
            if (resultCode == Activity.RESULT_OK) {
                String gettime = data.getStringExtra("gettime");
                System.out.println("==== gettime : " + gettime);
                tvBookingTime.setText(gettime);
            }
        }
    }

    private String date = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvBookingDate:
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(RestaurantBookTable.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                tvBookingDate.setText(day + "/" + (month + 1) + "/" + year);
                                date = year + "-" + (month + 1) + "-" + day;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;

            case R.id.tvBookingTime:
                if (tvBookingDate.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), model.getPleaseEnterBootingDate(), Toast.LENGTH_SHORT).show();
                } else {
                   /* Intent i = new Intent(RestaurantBookTable.this, ActivityTableTimeList.class);
                    i.putExtra("RestId", restid);
                    i.putExtra("date", date);
                    startActivityForResult(i, x);*/
                    showProgress();
                    callTimeApi();
                }

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(RestaurantBookTable.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        edit_book_time.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
                break;

            case R.id.btn_submit:
                strName = edit_name.getText().toString().trim();
                stremail = edit_email_addres.getText().toString().trim();
                strPhone = edit_mobile.getText().toString().trim();
                strBookDate = tvBookingDate.getText().toString().trim();
                strBookTime = tvBookingTime.getText().toString().trim();
                //strNumberofPerson = edit_no_person.getText().toString().trim();
                strNumberofPerson = spinner_tableSeat.getSelectedItem().toString();
                strSpecialInstruct = edit_specia_instruct.getText().toString().trim();
                if (edit_name.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_name.setError(model.getPleaseEnterFullName());
                    edit_name.requestFocus();
                } else if (edit_email_addres.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_email_addres.setError(model.getPleaseEnterYourEmail());
                    edit_email_addres.requestFocus();
                } else if (!isValidEmailId(edit_email_addres.getText().toString().trim())) {
                    edit_email_addres.setError(model.getPLEASEENTERVALIDEMAIL());
                    edit_email_addres.requestFocus();
                } else if (edit_mobile.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_mobile.setError(model.getPleaseEnterMobileNo());
                    edit_mobile.requestFocus();
                } else if (tvBookingDate.getText().toString().trim().equalsIgnoreCase("")) {
                    tvBookingDate.setError(model.getPleaseEnterBootingDate());
//                    edit_book_date.requestFocus();
                } else if (tvBookingTime.getText().toString().trim().equalsIgnoreCase("")) {
                    tvBookingTime.setError(model.getPleaseEnterBookingTime());
//                    edit_book_time.requestFocus();
                } /*else if (edit_no_person.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_no_person.setError(model.getNoOfPerson());
                    edit_no_person.requestFocus();
                }*/ else if (edit_specia_instruct.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_specia_instruct.setError(model.getSpecialInstructions());
                    edit_specia_instruct.requestFocus();
                } else {
                    getBookingTableData();
                }
                break;

            default:
                break;
        }
    }

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,8})$").matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 9 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }

    private void getBookingTableData() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<RestaurantBookTableModel> observable = apiInterface.setBookingTableData(prefsHelper.getPref(Constants.API_KEY), prefsHelper.getPref(Constants.LNG_CODE), customerId, restid, strName, stremail, strPhone, strBookDate, strBookTime, strNumberofPerson, strSpecialInstruct);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RestaurantBookTableModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RestaurantBookTableModel searchResult) {
                        showProgress();
                        Intent intent = new Intent(RestaurantBookTable.this, ThankuActivity.class);
                        intent.putExtra("msg", searchResult.getSuccessMsg());
                        intent.putExtra("booking_no", searchResult.getBookingNumber());
                        intent.putExtra("date", searchResult.getBookingDate() + ", " + searchResult.getBookingTime());
                        intent.putExtra("note", searchResult.getImportantNote());
                        startActivity(intent);
                        finish();
//                       showCustomDialog1decline(searchResult.getSuccessMsg());
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
    }

    private void showCustomDialog1decline(String s) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);

        alertDialog.setIcon(R.drawable.tick);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();

    }


    private Dialog dialog;

    private void dialogTimeSelection(List<TimeListModel.TimeList> sessionTypeMainData) {

        dialog = new Dialog(RestaurantBookTable.this);
        dialog.setContentView(R.layout.dialog_session_type);
        Window window = dialog.getWindow();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        TextView tvDone = dialog.findViewById(R.id.tvDone);
        tvDone.setText(model.getDone());
        tvCancel.setText(model.getCancel());
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (sessionType.equalsIgnoreCase("")){
                    sessionType= sessionTypeMainData.get(0).getId();
                    tvSessionType.setText(sessionTypeMainData.get(0).getSessionType());
                }*/
                // txt_selected_time.setText();
                dialog.cancel();
            }
        });

        WheelView wvSessionType = dialog.findViewById(R.id.wvSessionType);
        wvSessionType.setCyclic(false);

        /*final List<String> mOptionsItems = new ArrayList<>();
        for (int i=0;i<sessionTypeMainData.size();i++){
            mOptionsItems.add("item0");
        }*/

        wvSessionType.setAdapter(new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return sessionTypeMainData.size();
            }

            @Override
            public Object getItem(int index) {
                return sessionTypeMainData.get(index).getGetTime();
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        });
        wvSessionType.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                tvBookingTime.setText(sessionTypeMainData.get(index).getGetTime());
                //txt_selected_time.setText(sessionTypeMainData.get(index).getGetTime());
                //Toast.makeText(getApplicationContext(), "" + sessionTypeMainData.get(index).getSessionType(), Toast.LENGTH_SHORT).show();
              /*  sessionType = sessionTypeMainData.get(index).getId();
                tvSessionType.setText(sessionTypeMainData.get(index).getSessionType());*/
            }
        });
        dialog.show();
    }

    private void callTimeApi() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<TimeListModel> observable = apiInterface.getTableTimeListData(prefsHelper.getPref(Constants.API_KEY),
                prefsHelper.getPref(Constants.LNG_CODE), restid, date);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TimeListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TimeListModel searchResult) {
                        hideProgress();
                        if (searchResult.getTimeList() != null && searchResult.getTimeList().size() > 0) {
                            List<TimeListModel.TimeList> list = new ArrayList<TimeListModel.TimeList>();
                            list.clear();
//                            TimeListModel.TimeList newList = new TimeListModel.TimeList();
//                            newList.setGetTime(model.getASSOONASPOSSIBLE());
//                            newList.setSuccess("true");
//                            list.add(newList);
                            list.addAll(searchResult.getTimeList());
                            dialogTimeSelection(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        Log.d("TAG", "log...." + e);
                    }

                    @Override
                    public void onComplete() {
                        hideProgress();
                        //   activity.mySharePreferences.setBundle("login");

                    }
                });
    }


    class TagSpinnerAdapter extends ArrayAdapter<ArrayList> {
        ArrayList ob;

        public TagSpinnerAdapter(Context ctx, int txtViewResourceId, ArrayList objects) {
            super(ctx, txtViewResourceId, objects);
            ob = objects;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(android.R.id.text1);
            main_text.setText(ob.get(position).toString());
            return mySpinner;
        }
    }
}
