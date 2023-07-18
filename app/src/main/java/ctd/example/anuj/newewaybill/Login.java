package ctd.example.anuj.newewaybill;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import ctd.example.anuj.newewaybill.app.AppConfig;
import ctd.example.anuj.newewaybill.app.AppController;

public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText user_name, password;
    Button login_btn;
    ImageView view_password;
    Context context;

    String uid, pswd, res;
    String passwrd;
    JSONObject obj;
    ProgressDialog progressDialog;
    Map<String, String> params;
    JSONArray arr;
    String u_name, zone, range, auth_sec, location, ofc_name, app_currentversion;
    String app_version;
    String user_id_to_lowercase;

    android.app.AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = Login.this;
        setContentView(R.layout.login);
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_button);


        login_btn.setOnClickListener(this);
//   view_password.setOnClickListener(this);

        getVersionInfo();

        if (new Session(this).isLoggedIn()) {
            // goToNextActivity(R.anim.slide_in_from_right, R.anim.fade_out);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        /*//update your app notification
        builder = new android.app.AlertDialog.Builder(this);

        //  String current_version=new Session(Login.this).getApp_version();
        String current_version = getIntent().getStringExtra("current_version");

        Log.d(">>>>>current_version", current_version + "==" + app_version);
        if (current_version != null) {
            if (!current_version.equalsIgnoreCase(app_version)) {
                //notification for app uodate
                //check the session for the current version
                final int[] count = {0};
                builder.setMessage("Update your app with the current version available")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Update Available");
                alert.show();

            }
        }*/
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
        // textViewVersionInfo.setText(String.format("Version name = %s \nVersion code = %d", versionName, versionCode));
    }

    private void Login_user(final String uid, final String pswd) {

        String tag_string_req = "req_login";

       // WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        progressDialog = ProgressDialog.show(Login.this, "", "Loading...");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Nimisha", "Response for service: " + response);
                try {
                    Log.d(">>>json object", response);
                    JSONArray array = new JSONArray(response);
                    Log.d("json object", array.toString());

                    obj = array.getJSONObject(0);

                    passwrd = obj.getString("encryptpassword");
                    u_name = obj.getString("username");
                    zone = obj.getString("zone");
                    range = obj.getString("range");
                    auth_sec = obj.getString("auth_sect");
                    location = obj.getString("location");
                    ofc_name = obj.getString("OfficeName");
                    app_currentversion = obj.getString("Appver");

                    Log.d("Login password", passwrd);
                    Log.d("Login username", u_name);
                    Log.d("Login zone", zone);
                    Log.d("Login range", range);
                    Log.d("Login auth_sect", auth_sec);
                    Log.d("Login Location", location);
                    Log.d("Login office_code", ofc_name);

                    Toast.makeText(Login.this,"Incorrect Username and Password",Toast.LENGTH_SHORT).show();

                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            try {
                                passwrd = obj.getString("encryptpassword");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            super.onPostExecute(o);
                            user_id_to_lowercase = uid.toLowerCase();
                            String str = md5(pswd + "" + user_id_to_lowercase);
                            Log.d("user password", str);
                            if (str.equals(passwrd)) {
                                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show();
                                new Session(Login.this).setLogin(true);
                                new Session(Login.this).saveUserId(uid);
                                new Session(Login.this).savePassword(pswd);
                                //new Session(Login.this).saveApp_version(app_currentversion);
                                new Session(Login.this).saveBasicDetails(uid, u_name, zone, range, location, ofc_name, auth_sec);
                                goToMainActivity(R.anim.slide_in_from_right, R.anim.fade_out);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Incorrect Password", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(Login.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                params = new HashMap<String, String>();
                params.put("LoginName", uid);
                //params.put("password",pswd);
                return params;
            }

        };
        // Adding request to request queue
        AppController appController = new AppController();
        appController.setContext(Login.this);
        appController.addToRequestQueue(strReq, tag_string_req);
    }

    private String md5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.login_button:
                uid = user_name.getText().toString();
                if (uid.matches("")) {
                    Toast.makeText(this, "You did not enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                pswd = password.getText().toString();
                if (pswd.matches("")) {
                    Toast.makeText(this, "You did not enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login_user(uid, pswd);
                break;


        }
    }

    private void goToMainActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
    }


    @Override
    public void onBackPressed() {
        finish();
        //  super.onBackPressed();
    }
}
