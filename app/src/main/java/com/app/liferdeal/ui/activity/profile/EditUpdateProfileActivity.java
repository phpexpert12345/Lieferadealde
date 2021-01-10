package com.app.liferdeal.ui.activity.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.app.liferdeal.R;
import com.app.liferdeal.application.App;
import com.app.liferdeal.model.GetProfileModel;
import com.app.liferdeal.model.LanguageResponse;
import com.app.liferdeal.model.ProfileResponse;
import com.app.liferdeal.model.restaurant.LoyalityPointsModel;
import com.app.liferdeal.network.retrofit.ApiInterface;
import com.app.liferdeal.network.retrofit.RFClient;
import com.app.liferdeal.ui.Multipart.MultipartRequest;
import com.app.liferdeal.ui.Multipart.NetworkOperationHelper;
import com.app.liferdeal.util.CommonMethods;
import com.app.liferdeal.util.Constants;
import com.app.liferdeal.util.PrefsHelper;
import com.app.liferdeal.util.SharedPreferencesData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EditUpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private PrefsHelper prefsHelper;
    private RecyclerView rcv_loyality_points_list;
    List<LoyalityPointsModel> listt;
    private ImageView iv_back;
    private int REQUEST_CAMERA = 0;
    private String UserProfileImage = "";
    String imageviewSelected = "";
    MaterialEditText edtFirstName, edtLastName, edtPhoneNumber, edt_flat_name, edt_house_no,
            edtFloor, edtStreetName, edtZipCode, edtCity, edtState;
    private TextView edtEmail, tvUpdateProfile;
    private String s = "Customer id:";
    private String customerId, firstName, lastName, userPhone, userEmail, flatname, houseNo, userFloor, userStreetName, userZipCode, city, state, userAddress;
    private ProgressDialog pDialog;
    private CircleImageView profile_image;
    private Button btn_submit;
    private LanguageResponse model;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 1;
    private EditUpdateProfileActivityInterface editUpdateProfileActivityInterface;

    CircleImageView profileimage1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_profile_page);
        model = new LanguageResponse();
        if (App.retrieveLangFromGson(EditUpdateProfileActivity.this) != null) {
            model = App.retrieveLangFromGson(EditUpdateProfileActivity.this);
        }
        init();
    }

    public interface EditUpdateProfileActivityInterface {
        public void getupdateProfile(String profileimage);
    }


    private void init() {
        try {

            prefsHelper = new PrefsHelper(this);
            progressDialog = new ProgressDialog(this);
            iv_back = findViewById(R.id.iv_back);
            profile_image = findViewById(R.id.profile_image);
            btn_submit = findViewById(R.id.btn_submit);
            edtFirstName = findViewById(R.id.edt_first_name);
            edtLastName = findViewById(R.id.edt_last_name);
            edt_house_no = findViewById(R.id.edt_house_no);
            edt_flat_name = findViewById(R.id.edt_flat_name);
            edtStreetName = findViewById(R.id.edt_street_name);
            edtZipCode = findViewById(R.id.edt_zip_code);
            edtPhoneNumber = findViewById(R.id.edt_phone_number);
            tvUpdateProfile = findViewById(R.id.tvUpdateProfile);

            tvUpdateProfile.setText("" + model.getEditProfile().trim());
            edtFirstName.setHint("" + model.getFirstName().trim());
            edtLastName.setHint("" + model.getLastName().trim());
            edt_house_no.setHint("" + model.getHouseNo().trim());
            edt_flat_name.setHint("" + model.getFlatName().trim());
            edtStreetName.setHint("" + model.getStreetName().trim());
            edtZipCode.setHint("" + model.getPostalCode().trim());
            edtPhoneNumber.setHint("" + model.getMobileNo().trim());
            btn_submit.setText("" + model.getSubmit().trim());


            String userphoto = prefsHelper.getPref(Constants.USER_PROFILE_IMAGE);
            if (userphoto != null) {
                Glide.with(EditUpdateProfileActivity.this).load(userphoto).apply(new RequestOptions().override(100, 100).
                        placeholder(R.drawable.upload_profile)).into(profile_image);
            }

            iv_back.setOnClickListener(this);
            profile_image.setOnClickListener(this);
            btn_submit.setOnClickListener(this);
            customerId = prefsHelper.getPref(Constants.CUSTOMER_ID);
            getSavedAddress();
            // getLoyalty();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.profile_image:
                String title = model.getSelectOption();
                CharSequence[] itemlist = {"Take a Photo", "Pick from Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(EditUpdateProfileActivity.this);
                builder.setTitle(title);
                builder.setItems(itemlist, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// Take Photo
                                takePhotoFromCamera();
                                break;
                            case 1:// Choose Existing Photo
                                imageviewSelected = "profilepic";
                                selectImage();
                                break;

                            default:
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.setCancelable(true);
                alert.show();

                break;

            case R.id.btn_submit:

                if (edtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
                    edtFirstName.setError(model.getPleaseEnterFirstName());
                    edtFirstName.requestFocus();
                } else if (edtLastName.getText().toString().trim().equalsIgnoreCase("")) {
                    edtLastName.setError(model.getPleaseEnterLastName());
                    edtLastName.requestFocus();
                } else if (edt_house_no.getText().toString().trim().equalsIgnoreCase("")) {
                    edt_house_no.setError(model.getPleaseEnterHouseNo());
                    edt_house_no.requestFocus();
                } else if (edt_flat_name.getText().toString().trim().equalsIgnoreCase("")) {
                    edt_flat_name.setError(model.getPleaseEnterFlatName());
                    edt_flat_name.requestFocus();
                } else if (edtStreetName.getText().toString().trim().equalsIgnoreCase("")) {
                    edtStreetName.setError(model.getPleaseEnterStreetName());
                    edtStreetName.requestFocus();
                } else if (edtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
                    edtZipCode.setError(model.getPleaseEnterPostalCode());
                    edtZipCode.requestFocus();
                } else if (edtPhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    edtPhoneNumber.setError(model.getPleaseEnterMobileNo());
                    edtPhoneNumber.requestFocus();
                } else {
                    updateProfile();
//                    updateProfileData();
                }
                break;

            default:
                break;
        }
    }

    private void updateProfileData() {
//        File file = new File(imageUri.getPath());
//        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), edtFirstName.getText().toString().trim());
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), customerId);
        RequestBody api_key = RequestBody.create(MediaType.parse("text/plain"), "" + prefsHelper.getPref(Constants.API_KEY));
        RequestBody lang_code = RequestBody.create(MediaType.parse("text/plain"), "" + prefsHelper.getPref(Constants.LNG_CODE));

        RequestBody lastname = RequestBody.create(MediaType.parse("text/plain"), edtLastName.getText().toString().trim());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), edtPhoneNumber.getText().toString().trim());
        RequestBody house_no = RequestBody.create(MediaType.parse("text/plain"), edt_house_no.getText().toString().trim());
        RequestBody flat = RequestBody.create(MediaType.parse("text/plain"), edt_flat_name.getText().toString().trim());
        RequestBody street = RequestBody.create(MediaType.parse("text/plain"), edtStreetName.getText().toString().trim());
        RequestBody zip = RequestBody.create(MediaType.parse("text/plain"), edtZipCode.getText().toString().trim());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), "");

        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Call<ProfileResponse> call = apiInterface.updateUser(api_key, lang_code, id, name, lastname, phone, house_no, street, zip, city, flat);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull retrofit2.Response<ProfileResponse> response) {
                Log.e("Success", "" + response);
//                pDialog.dismiss();
                try {
                    if (response.body().getSuccess() == 1) {
                        String success_msg = response.body().getSuccessMsg();
                        String user_photo = response.body().getUserPhoto();
                        prefsHelper.savePref(Constants.USER_PROFILE_IMAGE, response.body().getUserPhoto());
                        if (response.body().getUserPhoto() != null) {
                            Glide.with(EditUpdateProfileActivity.this).load(response.body().getUserPhoto()).apply(new RequestOptions().
                                    override(100, 100).placeholder(R.drawable.user)).into(profile_image);

                        }
                        showCustomDialog1decline(success_msg, user_photo);

                    } else {
                        String success_msg = response.body().getSuccessMsg();
                        // showCustomDialog1decline(success_msg, "2");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
        imageviewSelected = "profilepic";
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && null != data) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                panpic.setImageBitmap(photo);

                if (imageviewSelected == "profilepic") {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profile_image.setImageBitmap(photo);
                    Uri tempUri = getImageUri(getApplicationContext(), photo);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    UserProfileImage = String.valueOf(finalFile);
                    Log.e("image", "" + finalFile + "     " + tempUri);

                }

            } else if (imageviewSelected == "profilepic") {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                UserProfileImage = cursor.getString(columnIndex);
                profile_image.setImageBitmap(decodeUri(selectedImage));

                cursor.close();

            }
        } catch (Exception e) {
            Log.d("expception:", "" + e.getMessage());
            Toast.makeText(this, "Something went wrong" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(EditUpdateProfileActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private SharedPreferencesData sharedPreferencesData;

    private void updateProfile() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(true);
        pDialog.show();

        MultipartRequest req = null;
        HashMap<String, String> params = new HashMap<>();
        params.put("api_key", "" + prefsHelper.getPref(Constants.API_KEY));
        params.put("lang_code", "" + prefsHelper.getPref(Constants.LNG_CODE));
        params.put("CustomerId", "" + customerId);
        params.put("CustomerFirstName", "" + CommonMethods.getStringDataInbase64(edtFirstName.getText().toString().trim())); //
        params.put("CustomerLastName", "" + CommonMethods.getStringDataInbase64(edtLastName.getText().toString().trim())); //
        params.put("CustomerPhone", "" + edtPhoneNumber.getText().toString().trim());
        params.put("customerFloor_House_Number", "" + CommonMethods.getStringDataInbase64(edt_house_no.getText().toString().trim())); //
        params.put("customerFlat_Name", "" + CommonMethods.getStringDataInbase64(edt_flat_name.getText().toString().trim())); //
        params.put("customerStreet", "" + CommonMethods.getStringDataInbase64(edtStreetName.getText().toString().trim())); //
        params.put("customerZipcode", "" + edtZipCode.getText().toString().trim());
        params.put("customerCity", "" + CommonMethods.getStringDataInbase64("")); //
        // params.put("customerState", "" + state);

        try {
            req = new MultipartRequest(Constants.UPDATE_PROFILE_URL, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //     showCustomDialog(error.getMessage());
                    return;
                }
            }, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Success", "" + response);
                    pDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        int success = object.getInt("success");
                        if (success == 1) {
                            String success_msg = object.optString("success_msg");
                            String user_photo = object.optString("user_photo");
                            prefsHelper.savePref(Constants.USER_PROFILE_IMAGE, user_photo);
                            //Log.e("PROFILEPP=",user_photo);

                            //sharedPreferencesData=new SharedPreferencesData(getApplicationContext());
                            //sharedPreferencesData.setSharedPreferenceData(Constants.);

                           /* Glide.with(EditUpdateProfileActivity.this).load(user_photo).apply(new RequestOptions().
                                    override(100, 100).placeholder(R.drawable.user)).into(profileimage1);*/
                            showCustomDialog1decline(success_msg, user_photo);

                        } else {
                            String success_msg = object.optString("success_msg");
                            // showCustomDialog1decline(success_msg, "2");

                        }

                    } catch (JSONException e) {
                        pDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }, params);

            req.addImageData("user_photo", new File(UserProfileImage));
            Log.e("qqqq", "" + UserProfileImage);
            NetworkOperationHelper.getInstance(EditUpdateProfileActivity.this).addToRequestQueue(req);

        } catch (UnsupportedEncodingException e) {
            pDialog.dismiss();
            e.printStackTrace();
        } catch (NullPointerException e) {
            pDialog.dismiss();
            e.printStackTrace();
        }
    }

    private void showCustomDialog1decline(String s, String user_photo) {
        final AlertDialog alertDialog = new AlertDialog.Builder(EditUpdateProfileActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("" + s);
        alertDialog.setIcon(R.drawable.tick);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (ProfileActivity.mInstance != null) {
                    ProfileActivity.mInstance.updateProfilePic(user_photo);
                }
                finish();

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void getSavedAddress() {
        apiInterface = RFClient.getClient().create(ApiInterface.class);
        Observable<GetProfileModel> observable = apiInterface.getProfileData(prefsHelper.getPref(Constants.CUSTOMER_ID));
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetProfileModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetProfileModel searchResult) {
                        showProgress();
                        String fstName = searchResult.getFirstName();
                        String lstName = searchResult.getLastName();
                        String usrName = searchResult.getUserName();
                        String houseNu = searchResult.getCustomerFloorHouseNumber();
                        String flatName = searchResult.getCustomerFlatName();
                        String streetName = searchResult.getCompanyStreet();
                        String zipcode = searchResult.getUserPostcode();
                        String userpgone = searchResult.getUserPhone();
                        String userphoto = searchResult.getUserPhoto();
                        setData(fstName, lstName, houseNu, flatName, streetName, zipcode, userpgone, userphoto);
                        //  setAddAdapter(searchResult.getAddressModel().getDeliveryaddress());
                        hideProgress();
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
        progressDialog.dismiss();

    }

    private void setData(String fstName, String lstName, String houseNu, String flatName, String streetName, String zipcode, String userpgone, String userphoto) {
        edtFirstName.setText(fstName);
        edtLastName.setText(lstName);
        edt_house_no.setText(houseNu);
        edt_flat_name.setText(flatName);
        edtStreetName.setText(streetName);
        edtZipCode.setText(zipcode);
        edtPhoneNumber.setText(userpgone);
    }
}
