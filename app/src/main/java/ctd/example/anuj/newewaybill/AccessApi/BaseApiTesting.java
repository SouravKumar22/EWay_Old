
package ctd.example.anuj.newewaybill.AccessApi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import ctd.example.anuj.newewaybill.AppControllers.AppController;
import ctd.example.anuj.newewaybill.R;
import ctd.example.anuj.newewaybill.Utils.Utils;


public abstract class BaseApiTesting {
    private ProgressDialog progressDoalog;

    public abstract void responseApi(JSONObject response);

    public abstract void responseJsonArrayApi(JSONArray responseArray);

    public abstract void errorApi(VolleyError error);

    public void postJsonApi(final Context context, String url, final JSONObject headerObj, final String tag, final boolean bool) {


        if (Utils.isNetConnected(context)) {

            if (bool) {
                callProgressDialog(context);
            }

            final Request.Priority mPriority = Request.Priority.HIGH;


            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, headerObj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e(tag, response.toString());
                    if (bool) {
                        progressDoalog.dismiss();
                    }
                    responseApi(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, "Error: " + error.getMessage());
                    if (bool) {
                        progressDoalog.dismiss();
                    }
                    if (error.getMessage() == null) {
                        Log.e(tag, "Please Try Again");
                        progressDoalog.dismiss();
                        // Utils.showDialog(context.getResources().getString(R.string.str_please_try_after_sometimes),context);
                    }
//                    else {
//                        Log.e(tag, "Error: " + error.getMessage() + " " + error.networkResponse.statusCode);
//                    }
                    errorApi(error);
                }
            }) {
                @Override
                public Priority getPriority() {
                    return mPriority;
                }

            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }

    public void postJsonObjectRequestApi(final Context context, String url, final JSONObject headerObj, final String tag, final boolean bool, String fcmCall) {


        if (Utils.isNetConnected(context)) {

            if (bool) {
                callProgressDialog(context);
            }

            final Request.Priority mPriority = Request.Priority.HIGH;


            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, headerObj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e(tag, response.toString());
                    if (bool) {
                        progressDoalog.dismiss();
                    }
                    responseApi(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, "Error: " + error.getMessage());
                    if (bool) {
                        progressDoalog.dismiss();
                    }
                    if (error.getMessage() == null) {
                        Log.e(tag, "Please Try Again");
                    }
//                    else {
//                        Log.e(tag, "Error: " + error.getMessage() + " " + error.networkResponse.statusCode);
//                    }
                    errorApi(error);
                }
            }) {


                @Override
                public Priority getPriority() {
                    return mPriority;
                }

            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }

    void callProgressDialog(Context context) {
//        progressDoalog = new ProgressDialog(context,R.style.MyTheme);
//        progressDoalog.setCancelable(false);
//        progressDoalog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        progressDoalog.show();

        progressDoalog = new ProgressDialog(context, R.style.DialogTheme);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
        View v = LayoutInflater.from(context).inflate(R.layout.custom_progress_view, null, false);
        progressDoalog.setContentView(v);
    }

    public void getJsonObjectRequestApi(final Context context, String url, final String tag) {

        if (Utils.isNetConnected(context)) {

            final ProgressDialog pDialog = new ProgressDialog(context, R.style.DialogTheme);
            pDialog.setCancelable(false);
            pDialog.show();
            View v = LayoutInflater.from(context).inflate(R.layout.custom_progress_view, null, false);
            pDialog.setContentView(v);

            final Request.Priority mPriority = Request.Priority.HIGH;

            Log.e("URL", url);
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.e(tag, response.toString());
                    pDialog.dismiss();
                    responseApi(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(tag, "Error: " + error.getMessage());
                    pDialog.dismiss();
                    if (error.getMessage() == null) {
                        Log.e(tag, "Please Try Again");
                    } else {
//                        Log.e(tag, "Error: " + error.getMessage() + " " + error.networkResponse.statusCode);
                    }
                    errorApi(error);
                }
            }) {


                @Override
                public Priority getPriority() {
                    return mPriority;
                }

            };

            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }
    }

    public void getJsonArrayRequestApi(final Context context, String url, final String tag) {

        if (Utils.isNetConnected(context)) {

            final ProgressDialog pDialog = new ProgressDialog(context, R.style.DialogTheme);
            pDialog.setCancelable(false);
            pDialog.show();
            View v = LayoutInflater.from(context).inflate(R.layout.custom_progress_view, null, false);
            pDialog.setContentView(v);

            final Request.Priority mPriority = Request.Priority.HIGH;
            Log.e(">>>URL", url);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(tag, response.toString());
                            pDialog.dismiss();
                            responseJsonArrayApi(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pDialog.dismiss();
                            Log.d(">>>VolleyError:", error + "");
                            Utils.showToast("VolleyError:" + error, context);
                            //Utils.showDialog(context.getResources().getString(R.string.str_please_try_after_sometimes),context);
                        }
                    }
            ) {
                @Override
                public Priority getPriority() {
                    return mPriority;
                }

            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }
    }

    public void getJsonArrayRequestApiWithoutLoader(final Context context, String url) {

        if (Utils.isNetConnected(context)) {
            final Request.Priority mPriority = Request.Priority.HIGH;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(">>>ResponseRequestApiWithoutLoader", response.toString());
                            responseJsonArrayApi(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(">>>VolleyError:", error + "");
                            //Utils.showToast("VolleyError:" + error, context);
                            //Utils.showDialog(context.getResources().getString(R.string.str_please_try_after_sometimes),context);
                        }
                    }
            ) {
                @Override
                public Priority getPriority() {
                    return mPriority;
                }

            };

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }
    }
}