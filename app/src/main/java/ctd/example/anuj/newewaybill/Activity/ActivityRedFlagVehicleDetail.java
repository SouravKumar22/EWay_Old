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

import ctd.example.anuj.newewaybill.R;
import ctd.example.anuj.newewaybill.Utils.Utils;
import ctd.example.anuj.newewaybill.app.AppConfig;


public class ActivityRedFlagVehicleDetail extends AppCompatActivity {
    Context context=ActivityRedFlagVehicleDetail.this;
    EditText _et_search_vechileno;
    TextView _tv_status;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_flag_vehicle_detail);

        _et_search_vechileno = findViewById(R.id._et_search_vechileno);
        _tv_status = findViewById(R.id._tv_status);
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
                            if (Utils.isNetConnected(ActivityRedFlagVehicleDetail.this)) {
                                Log.d(">>>>WhoCall", "Touch");
                                methodApiGetRedFlagVehicleDetails();

                            } else {
                                Utils.showToast(ActivityRedFlagVehicleDetail.this.getResources().getString(R.string.str_no_internet), ActivityRedFlagVehicleDetail.this);
                            }
                        } else {
                            Utils.showToast("Search Vehicle Number empty!", ActivityRedFlagVehicleDetail.this);
                            _et_search_vechileno.setError("!");
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public void methodApiGetRedFlagVehicleDetails() {
        progressDialog = ProgressDialog.show(ActivityRedFlagVehicleDetail.this, "", "Loading...");
        String url_verify = AppConfig.URL_VIEW_RED_FLAG_VEHICLE_DETAILS;
        Log.d(">>>>URL", url_verify);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url_verify, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                Log.d(">>>>>responsesaveFiles", response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArrayResponse = new JSONArray(response);
                    if(jsonArrayResponse.length()>0)
                    {
                        _tv_status.setText("RED FLAG STATUS:  "+"Yes");
                        _tv_status.setTextColor(ActivityRedFlagVehicleDetail.this.getResources().getColor(R.color.green));
                    }
                    else
                    {
                        _tv_status.setText("RED FLAG STATUS:  "+"No");
                        _tv_status.setTextColor(ActivityRedFlagVehicleDetail.this.getResources().getColor(R.color.red));
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