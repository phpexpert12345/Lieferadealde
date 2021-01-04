package com.app.liferdeal.ui.activity.restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantBookTable extends AppCompatActivity implements View.OnClickListener {

    private TextView edit_name, edit_email_addres, edit_mobile, edit_no_person, edit_specia_instruct, tv_faq, tvBookingDate, tvBookingTime;
    private Button btn_submit;
    private ImageView iv_back;
    private PrefsHelper prefsHelper;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
//    private TextInputEditText edit_book_date, edit_book_time;
    private String restid = "", customerId = "", strName = "", stremail = "", strPhone = "", strBookDate = "", strBookTime = "", strNumberofPerson = "", strSpecialInstruct = "";
    private LanguageResponse model = new LanguageResponse();
    private PrefsHelper authPreference;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

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
        iv_back = findViewById(R.id.iv_back);
        btn_submit = findViewById(R.id.btn_submit);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvBookingTime = findViewById(R.id.tvBookingTime);
        edit_name = findViewById(R.id.edit_name);
        edit_email_addres = findViewById(R.id.edit_email_addres);
        edit_mobile = findViewById(R.id.edit_mobile);
//        edit_book_date = findViewById(R.id.edit_book_date);
//        edit_book_time = findViewById(R.id.edit_book_time);
        edit_no_person = findViewById(R.id.edit_no_person);
        edit_specia_instruct = findViewById(R.id.edit_specia_instruct);
        tv_faq = findViewById(R.id.tv_faq);

        tv_faq.setText(model.getBookATable());
        edit_name.setHint(model.getCustomerName());
        edit_email_addres.setHint(model.getEmailAddress());
        edit_mobile.setHint(model.getMobileNo());
//        edit_book_date.setHint(model.getBookingDate());
//        edit_book_time.setHint(model.getBookingTime());
        edit_no_person.setHint(model.getNoOfPerson());
        edit_specia_instruct.setHint(model.getSpecialInstructions());
        tvBookingTime.setHint(model.getBookingTime());
        tvBookingDate.setHint(model.getBookingDate());
        btn_submit.setText(model.getSubmit());

        edit_no_person.addTextChangedListener(new TextWatcher() {
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
        });

//        edit_book_date.setInputType(InputType.TYPE_NULL);
//        edit_book_time.setInputType(InputType.TYPE_NULL);

        customerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
        restid = getIntent().getStringExtra("clickRestId");
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

    }

    public void showProgress() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();
    }

    private TimePickerDialog mTimePicker;

//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        switch (v.getId()) {
//            case R.id.tvBookingDate:
//                if (hasFocus) {
//                    calendar = Calendar.getInstance();
//                    year = calendar.get(Calendar.YEAR);
//                    month = calendar.get(Calendar.MONTH);
//                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//                    datePickerDialog = new DatePickerDialog(RestaurantBookTable.this,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                    edit_book_date.setText(day + "/" + (month + 1) + "/" + year);
//                                }
//                            }, year, month, dayOfMonth);
//                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                    datePickerDialog.show();
//                }
//                break;
//            case R.id.tvBookingTime:
//                if (edit_book_date.getText().toString().length() == 0) {
//                    Toast.makeText(getApplicationContext(), model.getPleaseEnterBootingDate(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent i = new Intent(RestaurantBookTable.this, ActivityTableTimeList.class);
//                    i.putExtra("RestId", restid);
//                    startActivityForResult(i, x);
////                    callTimeApi();
////
////                    Calendar mcurrentTime = Calendar.getInstance();
////                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
////                    int minute = mcurrentTime.get(Calendar.MINUTE);
////                    mTimePicker = new TimePickerDialog(RestaurantBookTable.this, new TimePickerDialog.OnTimeSetListener() {
////                        @Override
////                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
////                            edit_book_time.setText(selectedHour + ":" + selectedMinute);
////                        }
////                    }, hour, minute, true);//Yes 24 hour time
////                    mTimePicker.setTitle("Select Time");
////                    mTimePicker.show();
//                }
//                break;
//            case R.id.edit_no_person:
//                if (mTimePicker != null && mTimePicker.isShowing())
//                    mTimePicker.dismiss();
//
//                edit_no_person.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
//                break;
//        }
//    }

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
                    Intent i = new Intent(RestaurantBookTable.this, ActivityTableTimeList.class);
                    i.putExtra("RestId", restid);
                    i.putExtra("date", date);
                    startActivityForResult(i, x);
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
                strNumberofPerson = edit_no_person.getText().toString().trim();
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
                } else if (edit_no_person.getText().toString().trim().equalsIgnoreCase("")) {
                    edit_no_person.setError(model.getNoOfPerson());
                    edit_no_person.requestFocus();
                } else if (edit_specia_instruct.getText().toString().trim().equalsIgnoreCase("")) {
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
                        intent.putExtra("date", searchResult.getBookingDate());
                        intent.putExtra("note", searchResult.getImportantNote());
                        startActivity(intent);
//                        showCustomDialog1decline(searchResult.getSuccessMsg());
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
}
