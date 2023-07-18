package ctd.example.anuj.newewaybill.Activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ctd.example.anuj.newewaybill.Adapter.AdapterViewVechileDetails;
import ctd.example.anuj.newewaybill.R;
import ctd.example.anuj.newewaybill.SetGet.SetGetVehicleDetails;
import ctd.example.anuj.newewaybill.Utils.Utils;
import ctd.example.anuj.newewaybill.app.AppConfig;

public class ActivityViewVehicleDetails extends AppCompatActivity {
    RecyclerView recyclerview;
    TextView _tv_nodata;
    Context context=ActivityViewVehicleDetails.this;
    ArrayList<SetGetVehicleDetails> setGetVehicleDetailsArrayList;
    AdapterViewVechileDetails adapter;
    EditText _et_search_vechileno;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle_details);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(ActivityViewVehicleDetails.this, 1));
        _tv_nodata = findViewById(R.id._tv_nodata);
        _et_search_vechileno = findViewById(R.id._et_search_vechileno);
        setGetVehicleDetailsArrayList=new ArrayList<>();
        adapter = new AdapterViewVechileDetails();

        _et_search_vechileno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (_et_search_vechileno.getRight() - _et_search_vechileno.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (!TextUtils.isEmpty(_et_search_vechileno.getText().toString())) {
                            if (Utils.isNetConnected(ActivityViewVehicleDetails.this)) {
                                Log.d(">>>>WhoCall", "Touch");

                                methodApiGetVehicleDetails();

                            } else {
                                Utils.showToast(ActivityViewVehicleDetails.this.getResources().getString(R.string.str_no_internet), ActivityViewVehicleDetails.this);
                            }

                        } else {
                            Utils.showToast("Search Vehicle Number empty!", ActivityViewVehicleDetails.this);
                            _et_search_vechileno.setError("!");
                        }
                        return true;
                    }
                }
                return false;
            }
        });



    }

    public void methodApiGetVehicleDetails() {
        progressDialog = ProgressDialog.show(ActivityViewVehicleDetails.this, "", "Loading...");
        String url_verify = AppConfig.URL_VIEW_VEHICLE_DETAILS;
        Log.d(">>>>URL", url_verify);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url_verify, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                Log.d(">>>>>responsesaveFiles", response);
                try {
                    JSONArray jsonArrayResponse = new JSONArray(response);
                    setGetVehicleDetailsArrayList.clear();
                    if(jsonArrayResponse.length()>0)
                    {
                        progressDialog.dismiss();
                        _tv_nodata.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArrayResponse.length(); i++) {
                            SetGetVehicleDetails setGetVehicleDetails = new SetGetVehicleDetails();
                            setGetVehicleDetails.setVehicleNo(jsonArrayResponse.getJSONObject(i).getString("vehicleNo"));
                            setGetVehicleDetails.setAddDate(jsonArrayResponse.getJSONObject(i).getString("addDate"));
                            setGetVehicleDetails.setChasisNo(jsonArrayResponse.getJSONObject(i).getString("chasisNo"));
                            setGetVehicleDetails.setEngineNo(jsonArrayResponse.getJSONObject(i).getString("engineNo"));

                            setGetVehicleDetailsArrayList.add(setGetVehicleDetails);
                            adapter = new AdapterViewVechileDetails(ActivityViewVehicleDetails.this, setGetVehicleDetailsArrayList);
                            recyclerview.setAdapter(adapter);
                        }
                    }
                    else
                    {
                        progressDialog.dismiss();
                        _tv_nodata.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.d(">>>>JSONException", e + "");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("VehicleNo", _et_search_vechileno.getText().toString());
                return parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/x-www-form-urlencoded");
                //params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        queue.add(sr);
    }
}