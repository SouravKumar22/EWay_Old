package ctd.example.anuj.newewaybill.AccessApi;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;


public class NewAccessApiTesting extends BaseApiTesting {

    public NewAccessApiTesting(Context context, JSONObject header, String url, String task, boolean bool) {
        Log.d("===task", task);
        postJsonApi(context, url, header, task, bool);
    }

    public NewAccessApiTesting(Context context, String url, String task) {
        Log.d(">>>task", task);
        getJsonArrayRequestApi(context, url, task);
    }

    public NewAccessApiTesting(Context context, String url, String task, String TAG) {
        getJsonObjectRequestApi(context, url, task);
    }

    public NewAccessApiTesting(Context context, String url) {
        getJsonArrayRequestApiWithoutLoader(context, url);
    }


   /* public NewAccessApiTesting(Context context, JSONObject header, String url, String task, String fcmCall) {
        Log.d("===task", task);
        postJsonApi(context, url, header, task, false, fcmCall);
    }*/

    @Override
    public void responseApi(JSONObject response) {

    }

    @Override
    public void responseJsonArrayApi(JSONArray responseArray) {

    }

    @Override
    public void errorApi(VolleyError error) {

    }
}