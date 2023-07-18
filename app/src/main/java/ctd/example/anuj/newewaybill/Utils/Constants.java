package ctd.example.anuj.newewaybill.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ABHILASH
 */

public class Constants {
    public static int EXTERNAL_STORAGE_PERMISSION = 9999;
    public static int READ_PHONE_STATE = 568;

    public static void setSharedPrefrenceString(Activity activity, String TAG, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putString(TAG, value).apply();
    }

    public static String getSharedPrefrenceString(Activity activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return (sharedPreferences.getString(TAG, ""));
    }

    public static void setSharedPrefrenceStringContext(Context activity, String TAG, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putString(TAG, value).apply();
    }

    public static String getSharedPrefrenceStringContext(Context activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return (sharedPreferences.getString(TAG, ""));
    }


    public static void setSharedPrefrenceInt(Activity activity, String TAG, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putInt(TAG, Integer.parseInt(value)).apply();
    }

    public static int getSharedPrefrenceInt(Activity activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return (sharedPreferences.getInt(TAG, 0));
    }

    public static void setSharedPrefrenceIntContext(Context context, String TAG, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(TAG, Integer.parseInt(value)).apply();
    }

    public static int getSharedPrefrenceIntContext(Context context, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return (sharedPreferences.getInt(TAG, 0));
    }


    public static void setSharedPrefrenceBool(Activity activity, String TAG, boolean bool) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putBoolean(TAG, bool).apply();
    }

    public static boolean getSharedPrefrenceBool(Activity activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getBoolean(TAG, false);
    }

    public static void setSharedPrefrenceBoolContext(Context activity, String TAG, boolean bool) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.edit().putBoolean(TAG, bool).apply();
    }

    public static boolean getSharedPrefrenceBoolContext(Context activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getBoolean(TAG, false);
    }

    public static String getSharedPrefrenceFloat(Activity activity, String TAG) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getString(TAG, "0.0");
    }


}
