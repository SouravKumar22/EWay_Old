package ctd.example.anuj.newewaybill;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }



    public void saveLoginDetails(String username) {
        editor.putString("username", username).commit();


    }

    public String[] getLoginDetails() {
        return new String[]{sharedPreferences.getString("username", "")
                };
    }
    public void saveLatitude(String latitude) {
        editor.putString("latitude", latitude).commit();
    }

    public String getLatitude() {
        return sharedPreferences.getString("latitude", "");
    }

    public void saveApp_version(String app_version) {
        editor.putString("App_version", app_version).commit();
    }

    public String getApp_version() {
        return sharedPreferences.getString("App_version", "");
    }
    public void saveLongitude(String longitude) {
        editor.putString("Longitude", longitude).commit();
    }

    public String getLongitude() {
        return sharedPreferences.getString("Longitude", "");
    }

    public void savecurrentPlace(String Place) {
        editor.putString("Place", Place).commit();
    }

    public String getcurrentPlace() {
        return sharedPreferences.getString("Place", "");
    }

    public String getUserId() {
        return sharedPreferences.getString("User_id", "");
    }

    public void saveUserId(String User_id) {
        editor.putString("User_id", User_id).commit();
    }

    public String getPassword() {
        return sharedPreferences.getString("Password", "");
    }

    public void savePassword(String User_id) {
        editor.putString("Password", User_id).commit();
    }

    public void setLogin(boolean value) {
        editor.putBoolean("isLoggedIn", value).commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

/*     passwrd=obj.getString("encryptpassword");
                    u_name=obj.getString("username");
                    zone=obj.getString("zone");
                    range=obj.getString("range");
                    auth_sec=obj.getString("auth_sect");
                    location=obj.getString("location");
                    ofc_name=obj.getString("OfficeName");*/

    public void saveBasicDetails(String u_id,String username, String zone, String range,String location,String ofc_name,String auth_sect) {
        editor.putString("user_id", username).commit();
        editor.putString("username", username).commit();
        editor.putString("zone", zone).commit();
        editor.putString("range", range).commit();
        editor.putString("location", location).commit();
        editor.putString("office_name", ofc_name).commit();
        editor.putString("auth_sect", auth_sect).commit();
    }

    public String[] getBasicDetails() {
        return new String[]{sharedPreferences.getString("user_id", ""),sharedPreferences.getString("username", ""), sharedPreferences.getString("zone", ""),
                sharedPreferences.getString("range", ""),    sharedPreferences.getString("location", ""),
                sharedPreferences.getString("office_name", ""),sharedPreferences.getString("auth_sect", "")};
    }





}
