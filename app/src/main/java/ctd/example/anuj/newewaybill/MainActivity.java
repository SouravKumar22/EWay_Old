package ctd.example.anuj.newewaybill;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ctd.example.anuj.newewaybill.Activity.ActivityRedFlagVehicleDetail;
import ctd.example.anuj.newewaybill.Activity.ActivityViewVehicleDetails;
import ctd.example.anuj.newewaybill.app.AppConfig;
import ctd.example.anuj.newewaybill.app.AppController;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {


    EditText EWB_no, EWB_By, EWB_date_time, Supplier_gst, Recipient_gst, hsn_no, Item_nm, Quntity, total_amt, vehicle_num, eway_validity;
    CardView cv_vehicle_num, cv_s_gst, cv_r_gst, cv_hsn, cv_item_name, cv_quantity, cv_amt;
    LinearLayout ll_img, ll_view;
    ImageView scanner1, camera, img_bill;
    Button btn_submit;
    RadioGroup rdgr;
    ProgressDialog progressDialog;

    TextView current_place;
    ImageView img_inv_date;

    TextView textViewVersionInfo;

    String radio_type = "EXUP to EXUP Transactions";

    int click = 1;

    JSONArray data, arr1;
    double lat;
    double lng;
    String latitude = "", longitude = "";

    DatePickerDialog picker;

    String reslt;
    private static final int PHOTO_REQUEST = 10;
    private static final int REQUEST_WRITE_PERMISSION = 20;

    Bitmap bitmap = null;
    RadioButton rb;

    int counter = 0;
    private GPSTracker gpsTracker;
    Map<String, String> params;
    double e_lat, e_longi;

    String app_version;
    String current_version;

    JSONObject obj;

    String eway_bill_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eway Bill Collection App");

        EWB_no = (EditText) findViewById(R.id.EWB_no);
        EWB_By = (EditText) findViewById(R.id.EWB_By);
        EWB_date_time = (EditText) findViewById(R.id.EWB_date_time);
        vehicle_num = (EditText) findViewById(R.id.vehicle_num);
        eway_validity = (EditText) findViewById(R.id.eway_validity);
        Supplier_gst = (EditText) findViewById(R.id.Supplier_gst);
        Recipient_gst = (EditText) findViewById(R.id.Recipient_gst);
        hsn_no = (EditText) findViewById(R.id.hsn_no);
        Item_nm = (EditText) findViewById(R.id.Item_name);
        Quntity = (EditText) findViewById(R.id.Quantity);
        total_amt = (EditText) findViewById(R.id.total_amt);

        current_place = (TextView) findViewById(R.id.current_place);

        scanner1 = (ImageView) findViewById(R.id.scanner1);
        camera = (ImageView) findViewById(R.id.camera);
        img_bill = (ImageView) findViewById(R.id.img_bill);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        rdgr = (RadioGroup) findViewById(R.id.rdgr);

        img_inv_date = (ImageView) findViewById(R.id.img_inv_date);


        cv_vehicle_num = (CardView) findViewById(R.id.cv_vehicle_num);
        cv_s_gst = (CardView) findViewById(R.id.cv_s_gstin);
        cv_r_gst = (CardView) findViewById(R.id.cv_r_gstin);
        cv_hsn = (CardView) findViewById(R.id.cv_hsn);
        cv_item_name = (CardView) findViewById(R.id.cv_item_name);
        cv_quantity = (CardView) findViewById(R.id.cv_quantity);
        cv_amt = (CardView) findViewById(R.id.cv_amt);
        ll_img = (LinearLayout) findViewById(R.id.ll_img);
        ll_view = (LinearLayout) findViewById(R.id.ll_view);

        textViewVersionInfo = (TextView) findViewById(R.id.textview_version_info);

        getVersionInfo();

        //getCurrentVersionInfo(new Session(MainActivity.this).getUserId(), new Session(MainActivity.this).getPassword(), app_version);


        gpsTracker = new GPSTracker(MainActivity.this);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DisplayInfo();
        if (gpsTracker.canGetLocation()) {
            lat = gpsTracker.getLatitude();
            lng = gpsTracker.getLongitude();

            latitude = String.valueOf(lat);
            longitude = String.valueOf(lng);

            new Session(MainActivity.this).saveLatitude(latitude);
            new Session(MainActivity.this).saveLongitude(longitude);


        } else {

            latitude = "N/A";
            longitude = "N/A";

        }

        e_lat = Double.parseDouble(new Session(MainActivity.this).getLatitude());
        e_longi = Double.parseDouble(new Session(MainActivity.this).getLongitude());

        LocationAddress.getAddressFromLocation(e_lat, e_longi,
                getApplicationContext(), new GeocoderHandler());

        scanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });


        img_inv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if (monthOfYear < 9 && dayOfMonth >= 10) {
                                    // eway_validity  .setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    eway_validity.setText("" + dayOfMonth + "-0" + (monthOfYear+1) + "-" + year);
                                }

                                else if (monthOfYear < 9 && dayOfMonth < 10) {
                                    // eway_validity  .setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    eway_validity.setText("0" + dayOfMonth + "-0" + (monthOfYear+1) + "-" + year);
                                }

                                else if (monthOfYear > 9 && dayOfMonth >= 10) {
                                    // eway_validity.setText(year + "-" + (mo2thOfYear + 1) + "-" + dayOfMonth);
                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                                }

                                else if (monthOfYear > 9 && dayOfMonth < 10) {
                                    // eway_validity.setText(year + "-" + (mo2thOfYear + 1) + "-" + dayOfMonth);
                                    eway_validity.setText("0" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                                }

                                else if (monthOfYear == 9 && dayOfMonth < 10) {
                                    // eway_validity.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                    eway_validity.setText("0" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                                }

                                else if (monthOfYear == 9 && dayOfMonth >= 10) {
                                    // eway_validity.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear+1) + "-" + year);
                                }






//                                if (monthOfYear < 9 && dayOfMonth >= 12) {
//                                    // eway_validity  .setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//
//
//                                    eway_validity.setText("0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
//                                } else if (monthOfYear < 12 && dayOfMonth >= 12) {
//                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                } else if (monthOfYear > 12 && dayOfMonth >= 12) {
//                                    // eway_validity.setText(year + "-" + (mo2thOfYear + 1) + "-" + dayOfMonth);
//                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                } else if (monthOfYear > 12 && dayOfMonth < 12) {
//                                    //  eway_validity.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                } else if (monthOfYear == 12 && dayOfMonth < 12) {
//                                    // eway_validity.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                                    eway_validity.setText("" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                }
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(MainActivity.this, new
                        String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSION);
            }
        });

        Supplier_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click == 1 && !EWB_By.getText().toString().equals("")) {
                    showDailog();
                    click++;

                } else if (EWB_By.getText().toString().equals("")) {

                    Toast.makeText(MainActivity.this, "Please Scan The QR Code First", Toast.LENGTH_SHORT).show();
                }
            }
        });


        rdgr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rb = (RadioButton) group.findViewById(checkedId);
                radio_type = rb.getText().toString();
                Log.d(">>>>>check", radio_type);
                if (radio_type.equals("EXUP to EXUP Transactions")) {

                    EWB_no.setVisibility(View.VISIBLE);
                    EWB_By.setVisibility(View.VISIBLE);
                    EWB_date_time.setVisibility(View.VISIBLE);
                    vehicle_num.setVisibility(View.VISIBLE);
                    eway_validity.setVisibility(View.VISIBLE);
                    Supplier_gst.setVisibility(View.VISIBLE);
                    Recipient_gst.setVisibility(View.VISIBLE);
                    hsn_no.setVisibility(View.VISIBLE);
                    Item_nm.setVisibility(View.VISIBLE);
                    Quntity.setVisibility(View.VISIBLE);
                    total_amt.setVisibility(View.VISIBLE);
                    cv_vehicle_num.setVisibility(View.VISIBLE);
                    cv_s_gst.setVisibility(View.VISIBLE);
                    cv_r_gst.setVisibility(View.VISIBLE);
                    cv_hsn.setVisibility(View.VISIBLE);
                    cv_item_name.setVisibility(View.VISIBLE);
                    cv_quantity.setVisibility(View.VISIBLE);
                    cv_amt.setVisibility(View.VISIBLE);
                    ll_img.setVisibility(View.VISIBLE);

                } else {

                    // ll_view             .setVisibility(View.VISIBLE);

                    EWB_no.setVisibility(View.VISIBLE);
                    EWB_By.setVisibility(View.VISIBLE);
                    EWB_date_time.setVisibility(View.VISIBLE);
                    eway_validity.setVisibility(View.VISIBLE);
                    cv_vehicle_num.setVisibility(View.VISIBLE);
                    cv_s_gst.setVisibility(View.GONE);
                    cv_r_gst.setVisibility(View.GONE);
                    cv_hsn.setVisibility(View.VISIBLE);
                    cv_item_name.setVisibility(View.GONE);
                    cv_quantity.setVisibility(View.GONE);
                    cv_amt.setVisibility(View.GONE);
                    ll_img.setVisibility(View.GONE);


                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Eway_bill_number, place, generated_by, Date_and_time, Vehicle_number, Validity, Supplier_GSTIN, Recepient_GSTIN, HSN_code, Item_name, Quantity, Amount;


                Eway_bill_number = EWB_no.getText().toString();
                generated_by = EWB_By.getText().toString();
                Date_and_time = EWB_date_time.getText().toString();
                Vehicle_number = vehicle_num.getText().toString();
                Validity = eway_validity.getText().toString();
                Supplier_GSTIN = Supplier_gst.getText().toString();
                Recepient_GSTIN = Recipient_gst.getText().toString();
                HSN_code = hsn_no.getText().toString();
                Item_name = Item_nm.getText().toString();
                Quantity = Quntity.getText().toString();
                Amount = total_amt.getText().toString();
                place = current_place.getText().toString();

                if (place.equals("")) {
                    place = "N/A";
                }
                if (radio_type.equals("EXUP to EXUP Transactions")) {
                    if (!Eway_bill_number.equals("") && !generated_by.equals("") && !Date_and_time.equals("") && !Vehicle_number.equals("")
                            && !Validity.equals("") && !Supplier_GSTIN.equals("") && !Recepient_GSTIN.equals("") && !HSN_code.equals("")
                            && !Item_name.equals("") && !Quantity.equals("") && !Amount.equals("") && bitmap != null) {

                        if (!Eway_bill_number.contains("/") && !generated_by.contains("/")) {
                            submit_details(Eway_bill_number, generated_by, Date_and_time, Vehicle_number, Validity, Supplier_GSTIN, Recepient_GSTIN, HSN_code, Item_name, Quantity, Amount, "EXUP to EXUP");
                        } else if (Eway_bill_number.contains("/")) {
                            Toast.makeText(MainActivity.this, "Please check your EWay bill no.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your Generated by GSTIN no.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (bitmap == null) {

                        Toast.makeText(MainActivity.this, "Please Click The Image Of The Bill", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MainActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    if (!Eway_bill_number.equals("") && !generated_by.equals("") && !Date_and_time.equals("")
                            && !Validity.equals("")&& !Vehicle_number.equals("")&& !HSN_code.equals("")) {

                        // Log.e("Details",Eway_bill_number+"\n"+generated_by+"\n"+ Date_and_time+"\n"+Validity+"\n"+place+"\n"+new Session(MainActivity.this).getLatitude()+"\n"+new Session(MainActivity.this).getLongitude()+"\n"+new Session(MainActivity.this).getUserId());
                        if (!Eway_bill_number.contains("/") && !generated_by.contains("/")) {
                            submit_details_type_2(Eway_bill_number, generated_by, Date_and_time, Validity,Vehicle_number,HSN_code);
                        } else if (Eway_bill_number.contains("/")) {
                            Toast.makeText(MainActivity.this, "Please check your EWay bill no.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your Generated by GSTIN no.", Toast.LENGTH_SHORT).show();
                        }


                    } else {

                        Toast.makeText(MainActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }


    private void getCurrentVersionInfo(final String userId, final String password, final String app_version) {

        String tag_string_req = "req_login";

        WifiManager wifiMgr = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.e("Nimisha", "Response for service: " + response);
                try {
                    Log.e("json object", response);

                    JSONArray array = new JSONArray(response);
                    Log.e("json object", array.toString());
                    obj = array.getJSONObject(0);
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            try {
                                current_version = obj.getString("Appver");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);


                            if (!current_version.equalsIgnoreCase(app_version)) {

                                //notification for the app update

                                new Session(MainActivity.this).setLogin(false);
                                new Session(MainActivity.this).saveApp_version(current_version);

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.putExtra("current_version", current_version);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "New Version Available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                params = new HashMap<String, String>();
                params.put("LoginName", userId);
                return params;
            }

        };
        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(MainActivity.this);
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_REQUEST);

    }

    private void submit_details(final String Eway_bill_number, final String generated_by, final String Date_and_time, final String Vehicle_number, final String Validity, final String Supplier_GSTIN, final String Recepient_GSTIN, final String HSN_code, final String Item_name, final String Quantity, final String Amount, final String type) {
        String tag_string_req = "req_login";


        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BILL_DETAIL_TYPE1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.e("Nimisha", "Response for service: " + response);
                try {
                    final JSONObject object = new JSONObject(response);
                    reslt = object.getString("result");
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);

                            Toast.makeText(MainActivity.this, "" + reslt, Toast.LENGTH_LONG).show();
                            Log.e("place", new Session(MainActivity.this).getcurrentPlace());

                            if (reslt.equals("E-way Bill Details Uploaded")) {
                                finish();
                                startActivity(getIntent());
                            }


                        }
                    }.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // Log.e("Error",error.getMessage());
                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();

                Log.e("Details", Eway_bill_number + "\n" + generated_by + "\n" + Date_and_time + "\n" + Vehicle_number + "\n" + Validity + "\n" + Supplier_GSTIN +
                        "\n" + Recepient_GSTIN + "\n" + HSN_code + "\n" + Item_name + "\n" + Quantity + "\n" + Amount + "\n" + new Session(MainActivity.this).getcurrentPlace() + "\n" + new Session(MainActivity.this).getLatitude() + "\n" + new Session(MainActivity.this).getLongitude() + "\n" + new Session(MainActivity.this).getUserId());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();

                String temp;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                byte[] b = baos.toByteArray();
                temp = Base64.encodeToString(b, Base64.NO_WRAP);


                params.put("Eway_bill_number", Eway_bill_number);
                params.put("generated_by", generated_by);
                params.put("Date_and_time", Date_and_time);
                params.put("Vehicle_number", Vehicle_number);
                params.put("Validity", Validity);
                params.put("Supplier_GSTIN", Supplier_GSTIN);
                params.put("Recepient_GSTIN", Recepient_GSTIN);
                params.put("HSN_code", HSN_code);
                params.put("Item_name", Item_name);
                params.put("Quantity", Quantity);
                params.put("Amount", Amount);
                params.put("User_id", new Session(MainActivity.this).getUserId());
                params.put("Image", temp);
                params.put("latitude", new Session(MainActivity.this).getLatitude());
                params.put("longitude", new Session(MainActivity.this).getLongitude());
                params.put("place", new Session(MainActivity.this).getcurrentPlace());
                params.put("ofc_code", new Session(MainActivity.this).getBasicDetails()[6]);
                params.put("user_app_version", textViewVersionInfo.getText().toString());
                return params;
            }

        };

        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(MainActivity.this);
        appController.addToRequestQueue(strReq, tag_string_req);


    }

    private void submit_details_type_2(final String eway_bill_number, final String generated_by,
                                       final String date_and_time, final String validity,final String Vehicle_number,final String hsnno) {

        String tag_string_req = "req_login";


        progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...");


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_BILL_DETAIL_TYPE2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("Nimisha", "Response for service: " + response);
                try {
                    Log.e("json object", response);
                    JSONObject object = new JSONObject(response);
                    reslt = object.getString("result");
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);

                            Toast.makeText(MainActivity.this, "" + reslt, Toast.LENGTH_SHORT).show();
                            Log.e("latitude", new Session(MainActivity.this).getLatitude());
                            Log.e("longitude", new Session(MainActivity.this).getLongitude());
                            Log.e("place", new Session(MainActivity.this).getcurrentPlace());
                            if (reslt.equals("E-way Bill Uploaded")) {
                                finish();
                                startActivity(getIntent());
                            }
                        }
                    }.execute();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                Log.e("Details", eway_bill_number + "\n" + generated_by + "\n" + date_and_time + "\n" + validity + "\n" + new Session(MainActivity.this).getcurrentPlace() + "\n" + new Session(MainActivity.this).getLatitude() + "\n" + new Session(MainActivity.this).getLongitude() + "\n" + new Session(MainActivity.this).getUserId());
                Log.e("mmmm", textViewVersionInfo.getText().toString() + "\n" + new Session(MainActivity.this).getBasicDetails()[6]);


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                params = new HashMap<String, String>();

                params.put("Eway_bill_number", eway_bill_number);
                params.put("generated_by", generated_by);
                params.put("Date_and_time", date_and_time);
                params.put("Validity", validity);
                params.put("User_id", new Session(MainActivity.this).getUserId());
                params.put("latitude", new Session(MainActivity.this).getLatitude());
                params.put("longitude", new Session(MainActivity.this).getLongitude());
                params.put("place", new Session(MainActivity.this).getcurrentPlace());
                params.put("ofc_code", new Session(MainActivity.this).getBasicDetails()[6]);
                params.put("user_app_version", textViewVersionInfo.getText().toString());

                params.put("vehicle_number", Vehicle_number);
                params.put("hsn_num", hsnno);

                return params;
            }

        };


        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(MainActivity.this);
        appController.addToRequestQueue(strReq, tag_string_req);


    }


    private void showDailog() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("")
                .setMessage("Same GSTIN as Generated by?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Supplier_gst.setText(EWB_By.getText().toString());

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                        Supplier_gst.setFocusable(true);
                    }
                })
                .show();


        Supplier_gst.setClickable(false);

    }

    public void DisplayInfo() {

        String u_name, zone, range, location, ofc;
        u_name = new Session(MainActivity.this).getBasicDetails()[1];
        zone = new Session(MainActivity.this).getBasicDetails()[2];
        range = new Session(MainActivity.this).getBasicDetails()[3];
        location = new Session(MainActivity.this).getBasicDetails()[4];
        ofc = new Session(MainActivity.this).getBasicDetails()[5];
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("User Details")
                .setMessage("User Name   :" + u_name
                        + "\nZone              :" + zone
                        + "\nRange            :" + range
                        + "\nLocation        :" + location
                        + "\nOffice Name :" + ofc)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })


                .show();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result


                showResult(result.getContents());
            }
        } else if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

            img_bill.setImageBitmap(bitmap);

        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    //method to construct dialogue with scan results
    public void showResult(final String result) {


        //  result.toString().trim().split("\\|");
        // String[] res=result.toString().trim().split("\\/");

     /*  Log.e("eway_bill",res[0]);
        Log.e("eway_generated_by",res[1]);
        Log.e("eway_bill_date_time",res[2]+"/"+res[3]+"/"+res[4]);
       Log.e("length",res.length+"");*/


      /*  String eway_billNo=res[0];
        String eway_generated_by=res[1];



        String eway_bill_date_time=res[2]+"/"+res[3]+"/"+res[4];
*/
        String eway_billNo = result.substring(0, 12);
        String eway_generated_by = result.substring(13, 28);
        String eway_bill_date_time = result.substring(29, result.length());

        //eway_bill_no

        if (eway_billNo.contains("/")) {


            Toast.makeText(this, "Please enter the valid eway bill number", Toast.LENGTH_SHORT).show();


        }
        if (eway_billNo.contains(" ")) {

            eway_bill_no = eway_billNo.replaceAll("\\s{2,}", " ");
        }


        EWB_no.setText(eway_billNo);


        EWB_By.setText(eway_generated_by);


        EWB_date_time.setText(eway_bill_date_time);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Logout:

                new Session(MainActivity.this).setLogin(false);
                startActivity(new Intent(this, Login.class));

                return true;

            case R.id.view_uploaded_bill:

                startActivity(new Intent(this, View_uploaded_bill.class));

                return true;

            case R.id.view_user_profile:

                DisplayInfo();

                return true;
            case R.id.view_GetVehicleDetails: {

                startActivity(new Intent(this, ActivityViewVehicleDetails.class));

                return true;
            } case R.id.view_GetRedFlagVehicleDetails: {

                startActivity(new Intent(this, ActivityRedFlagVehicleDetail.class));

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class GeocoderHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {


                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;


                default:
                    locationAddress = null;
            }


            current_place.setText(locationAddress);

            new Session(MainActivity.this).savecurrentPlace(locationAddress);
            Log.e("place", locationAddress);
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        counter++;
        if (counter % 2 == 0) {

            Intent start = new Intent(Intent.ACTION_MAIN);
            start.addCategory(Intent.CATEGORY_HOME);
            start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(start);
        }


    }


    private void getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        app_version = versionName;
        textViewVersionInfo.setText("Version : " + versionName);
        // textViewVersionInfo.setText(String.format("Version name = %s \nVersion code = %d", versionName, versionCode));
    }
}
