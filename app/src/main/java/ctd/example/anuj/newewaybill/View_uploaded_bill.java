package ctd.example.anuj.newewaybill;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import ctd.example.anuj.newewaybill.Models.Uploaded_bill_Model;
import ctd.example.anuj.newewaybill.app.AppConfig;
import ctd.example.anuj.newewaybill.app.AppController;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_uploaded_bill extends AppCompatActivity {

    TextView tv_view_bill;
    ListView lv_bill;
    ArrayList<Uploaded_bill_Model> bill = new ArrayList<>();
    LinearLayout ll_list;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeLayout;
    JSONArray arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_uploaded_bill);
        setTitle("Eway Bill Collection App");

        tv_view_bill = (TextView) findViewById(R.id.tv_view_bill);
        lv_bill = (ListView) findViewById(R.id.lv_bill);
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
       /* swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                                    android.R.color.holo_green_light,
                                    android.R.color.holo_orange_light,
                                    android.R.color.holo_red_light);
                */
        //ViewBills();
        ViewBillsNew();
    }

    private void ViewBills() {
        String tag_string_req = "req_login";
        bill.clear();
        progressDialog = ProgressDialog.show(View_uploaded_bill.this, "", "Loading...");
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_VIEW_BILL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.d(">>>>>", "Response for service: " + response);
                try {
                    final JSONObject object = new JSONObject(response);
                    Log.d("response ", response.toString());
                    String array = object.getString("Table");
                    arr = new JSONArray(array);
                    Log.d("response Array", arr.toString());
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {

                            if (arr.length() > 0) {
                                try {
                                    for (int i = 0; i < arr.length(); i++) {
                                        JSONObject obj = arr.getJSONObject(i);
                                        bill.add(new Uploaded_bill_Model(
                                                obj.getString("checking_date"),
                                                obj.getString("vehicle_num"),
                                                obj.getString("EWB_num"),
                                                obj.getString("EWB_date_and_time"),
                                                obj.getString("validity"),
                                                obj.getString("s_gstin"),
                                                obj.getString("r_gstin"),
                                                obj.getString("hsn"),
                                                obj.getString("item_name"),
                                                obj.getString("quntity"),
                                                obj.getString("amount"),
                                                obj.getString("Latitude"),
                                                obj.getString("Longitude"),
                                                obj.getString("Checking_place")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);

                            Log.d("nimisha ", "No. of bills uploaded=" + bill.size());
                            if (arr.length() > 0) {
                                lv_bill.setVisibility(View.VISIBLE);
                                ll_list.setVisibility(View.VISIBLE);
                                tv_view_bill.setVisibility(View.GONE);
                                BillAdapter bill_ad = new BillAdapter(View_uploaded_bill.this, bill);

                                setListViewHeightBasedOnChildren(lv_bill);
                                lv_bill.setAdapter(bill_ad);
                                tv_view_bill.setText("Total No. of Bills Uploaded:  " + bill.size());
                                tv_view_bill.setVisibility(View.VISIBLE);

                            } else {
                                lv_bill.setVisibility(View.GONE);
                                ll_list.setVisibility(View.GONE);
                                tv_view_bill.setVisibility(View.VISIBLE);
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
                Log.d("Error", error.getMessage());
                Toast.makeText(View_uploaded_bill.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", new Session(View_uploaded_bill.this).getUserId());
                return params;
            }
        };

        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(View_uploaded_bill.this);
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    private void ViewBillsNew() {
        String tag_string_req = "req_login";


        progressDialog = ProgressDialog.show(View_uploaded_bill.this, "", "Loading...");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_VIEW_BILL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.d(">>>>>", "Response for service: " + response);
                try {
                    final JSONObject object = new JSONObject(response);
                    Log.d("response ", response.toString());
                    String array = object.getString("Table");
                    arr = new JSONArray(array);
                    Log.d("response Array", arr.toString());
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {

                            if (arr.length() > 0) {
                                try {
                                    for (int i = 0; i < arr.length(); i++) {
                                        JSONObject obj = arr.getJSONObject(i);
                                        bill.add(new Uploaded_bill_Model(
                                                obj.getString("checking_date"),
                                                obj.getString("vehicle_num"),
                                                obj.getString("EWB_num"),
                                                obj.getString("EWB_date_and_time"),
                                                obj.getString("validity"),
                                                obj.getString("s_gstin"),
                                                obj.getString("r_gstin"),
                                                obj.getString("hsn"),
                                                obj.getString("item_name"),
                                                obj.getString("quntity"),
                                                obj.getString("amount"),
                                                obj.getString("Latitude"),
                                                obj.getString("Longitude"),
                                                obj.getString("Checking_place")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);

                            Log.d("nimisha ", "No. of bills uploaded=" + bill.size());
                            if (arr.length() > 0) {
                                lv_bill.setVisibility(View.VISIBLE);
                                ll_list.setVisibility(View.VISIBLE);
                                tv_view_bill.setVisibility(View.GONE);
                                BillAdapter bill_ad = new BillAdapter(View_uploaded_bill.this, bill);

                                setListViewHeightBasedOnChildren(lv_bill);
                                lv_bill.setAdapter(bill_ad);
                                tv_view_bill.setText("Total No. of Bills Uploaded:  " + bill.size());
                                tv_view_bill.setVisibility(View.VISIBLE);

                            } else {
                                lv_bill.setVisibility(View.GONE);
                                ll_list.setVisibility(View.GONE);
                                tv_view_bill.setVisibility(View.VISIBLE);
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
                Toast.makeText(View_uploaded_bill.this, "Connection Error", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", new Session(View_uploaded_bill.this).getUserId());
                params.put("From_date", "");
                params.put("To_Date", "");
                /*params.put("From_date", "01/09/21");
                params.put("To_Date", "02/09/21");*/
                return params;
            }
        };

        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(View_uploaded_bill.this);
        appController.addToRequestQueue(strReq, tag_string_req);


    }

    public class BillAdapter extends BaseAdapter {

        private final ArrayList<Uploaded_bill_Model> ml;

        private LayoutInflater lf = null;
        Context ctx = null;
        String type;

        public BillAdapter(Activity activity, ArrayList<Uploaded_bill_Model> l) {
            ctx = activity.getApplicationContext();
            ml = l;
            lf = LayoutInflater.from(activity);
        }

        @Override
        public int getCount() {
            return ml.size();
        }

        @Override
        public Object getItem(int position) {
            return ml.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            TextView checking_date, vehicle_num, EWB_num, EWB_date_and_time, validity, S_gstin, r_gstin, hsn, item_name, quantity, amount, place, longi;


            final CardView cv_bill_child;
            //Toast.makeText(ctx, "view created", Toast.LENGTH_LONG).show();

            if (convertView == null) {
                convertView = lf.inflate(R.layout.upload_bill_child, parent, false);
            }

            checking_date = (TextView) convertView.findViewById(R.id.checking_date);
            vehicle_num = (TextView) convertView.findViewById(R.id.vehicle_num);
            EWB_num = (TextView) convertView.findViewById(R.id.EWB_no);
            EWB_date_and_time = (TextView) convertView.findViewById(R.id.EWB_Date_and_time);
            validity = (TextView) convertView.findViewById(R.id.validity);
            S_gstin = (TextView) convertView.findViewById(R.id.s_gstin);
            r_gstin = (TextView) convertView.findViewById(R.id.r_gstin);
            hsn = (TextView) convertView.findViewById(R.id.hsn);
            item_name = (TextView) convertView.findViewById(R.id.item_name);
            quantity = (TextView) convertView.findViewById(R.id.quantity);
            amount = (TextView) convertView.findViewById(R.id.amount);
            place = (TextView) convertView.findViewById(R.id.place);


            cv_bill_child = (CardView) convertView.findViewById(R.id.cv_bill_child);


            if (position % 2 == 0) {
                cv_bill_child.setCardBackgroundColor(Color.parseColor("#FF9EC5F2"));
            }
            final Uploaded_bill_Model bill = ml.get(position);
            checking_date.setText(bill.getChecking_date());
            vehicle_num.setText(bill.getVehicle_num());
            EWB_num.setText(bill.getEWB_num());
            EWB_date_and_time.setText(bill.getEWB_date_and_time());
            validity.setText(bill.getValidity());
            S_gstin.setText(bill.getS_gstin());
            r_gstin.setText(bill.getR_gstin());
            hsn.setText(bill.getHsn());
            item_name.setText(bill.getItem_name());
            quantity.setText(bill.getQuantity());
            amount.setText(bill.getAmount());
            place.setText(bill.getPlace());


            String str = bill.getChecking_date() + "\n" + bill.getVehicle_num() + "\n" + bill.getEWB_num() + "\n" + bill.getEWB_date_and_time()
                    + "\n" + bill.getValidity() + "\n" + bill.getS_gstin() + "\n" + bill.getR_gstin() + "\n" + bill.getHsn() + "\n" + bill.getItem_name()
                    + "\n" + bill.getQuantity() + "\n" + bill.getAmount() + "\n" + bill.getPlace();

            Log.d("data", str);

            return convertView;

        }


    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
}
