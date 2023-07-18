package ctd.example.anuj.newewaybill.Utils;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import ctd.example.anuj.newewaybill.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;


public class Utils {

    public static Dialog dialog;
    byte[] accImage;

    byte[] logoImage;
    byte[] photo;
    public static int check = 0;
    public static String returnlSmsVal = "success";

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText())
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showProgressDialog(Context context, String msg) {
        try {

            if (dialog == null) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.custom_progress_dialog);
            }
            final TextView txtView = (TextView) dialog.findViewById(R.id.txt_di);
            txtView.setText(msg);

            if (!dialog.isShowing())
                dialog.show();

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convert from bitmap to byte array
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        bitmap=getResizedBitmap(bitmap,1000,1000);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] bytearray = stream.toByteArray();
         bytearray = Arrays.copyOf(bytearray, bytearray.length);
        return bytearray;
    }
    public static Bitmap getResizedBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        float scale = Math.min(((float)maxHeight / bitmap.getWidth()), ((float)maxWidth / bitmap.getHeight()));

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }


    public static void showProgressDialog(Context context) {
        showProgressDialog(context, "Please Wait..");
    }
    /* public static byte[] urlToImageBLOB(String url) throws IOException {
     *//*try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost method = new HttpPost(url);
            HttpResponse response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                return EntityUtils.toString(entity);
            }
            else{
                return "No string.";
            }
        }
        catch(Exception e){
            return "Network problem";
        }*//*
        Log.d("@@@###IMAGEURL",url);

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpEntity entity = null;
            HttpUriRequest httpGet = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                entity = response.getEntity();
            }
            return EntityUtils.toByteArray(entity);
    }*/
   /*public byte[] getLogoImage(String url) {
       try {


           URL imageUrl = new URL(url);
           URLConnection ucon = imageUrl.openConnection();
           System.out.println("11111");
           InputStream is = ucon.getInputStream();
           System.out.println("12121");

           BufferedInputStream bis = new BufferedInputStream(is);
           System.out.println("22222");

           ByteArrayBuffer baf = new ByteArrayBuffer(500);
           int current = 0;
           System.out.println("23333");

           while ((current = bis.read()) != -1) {
               baf.append((byte) current);

           }
           photo = baf.toByteArray();
           Log.d("####@@@@photo",photo+"");


       } catch (Exception e) {
           Log.d("ImageManager", "Error: " + e.toString());
       }
       return accImage;
   }*/

    public static void hideProgressDialog() {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearBackStack(FragmentManager manager) {

        if (manager.getBackStackEntryCount() > 1) {
            for (int i = 0; i > (manager.getBackStackEntryCount() - 1); i++) {
                FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(i);
                manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static boolean isNetworkAvailable(Context activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            //    showToast("Please connect to Internet", activity);

            return false;
        } else {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static long milliseconds(String date) {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    public static void setDatePicker(Context context,
                                     final TextView tv_date,
                                     final String TAG) {

        final int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.d("@@@@SystemYEAR", mYear + "");
        Log.d("@@@@SystemmMonth", (mMonth + 1) + "");
        Log.d("@@@@System mDay", mDay + "");

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date newDate = null;

                        // String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Log.d(TAG + "@@@@selectedDate", selectedDate);
                        try {
                            newDate = format.parse(selectedDate);
                            Log.d("@@@@newDate", newDate + "");
                            selectedDate = format.format(newDate);
                            tv_date.setText(selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            tv_date.setHint(TAG);
                            tv_date.setText("");
                            tv_date.setText(Utils.currentDate());
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint(TAG);
                tv_date.setText("");
                tv_date.setText(Utils.currentDate());
            }
        });
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }



    public static void setDatePickerTemp(Context context,
                                         final TextView tv_date, final LinearLayout linearLayout,
                                         final String TAG) {

        final int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.d("@@@@SystemYEAR", mYear + "");
        Log.d("@@@@SystemmMonth", (mMonth + 1) + "");
        Log.d("@@@@System mDay", mDay + "");

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date newDate = null;

                        //String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Log.d(TAG + "@@@@selectedDate", selectedDate);
                        try {
                            newDate = format.parse(selectedDate);
                            Log.d("@@@@newDate", newDate + "");
                            selectedDate = format.format(newDate);
                            tv_date.setText(selectedDate);
                            linearLayout.setVisibility(View.VISIBLE);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint("Select Date");
                tv_date.setText("");
                linearLayout.setVisibility(View.GONE);
            }
        });
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


    public static String currentDate() {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String addDayFromCurrentDate(int day) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        cal.add(Calendar.DAY_OF_MONTH, day);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public static long addDayFromCurrentDateLong(int day) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTimeInMillis();
    }

    public static String dateCalculate(String dateString, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        try {
            cal.setTime(s.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, days);
        return s.format(cal.getTime());
    }

    public static void dismissProgressDialog(Context context, ProgressDialog progressDoalog) {
        progressDoalog.dismiss();
    }

    public static void showToast(String msg, final Context ctx) {
       /* try {
            new AlertDialog.Builder(ctx)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        final Dialog dialogmain = new Dialog(ctx, R.style.MyTheme);

        dialogmain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogmain.setContentView(R.layout.dialog_error);
        Window window = dialogmain.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        final TextView _tv_error = (TextView) dialogmain.findViewById(R.id._tv_error);
        _tv_error.setText(msg);
        final Button _btn_ok = (Button) dialogmain.findViewById(R.id._btn_ok);
        _btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogmain.dismiss();
            }
        });
        dialogmain.show();
    }

    public static boolean showDialog(String Message, Context context) {

        final Dialog dialogmain = new Dialog(context, R.style.MyTheme);
        dialogmain.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogmain.setContentView(R.layout.dialog_error);
        Window window = dialogmain.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        final TextView _tv_error = (TextView) dialogmain.findViewById(R.id._tv_error);
        _tv_error.setText(Message);
        final Button _btn_ok = (Button) dialogmain.findViewById(R.id._btn_ok);
        _btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogmain.dismiss();
            }
        });
        dialogmain.show();
        return false;
    }

    public static int showToastOk(String msg, final Context ctx, String okButtonText, String cancelButtonText) {

        try {
            new AlertDialog.Builder(ctx)
                    .setMessage(msg)
                    .setPositiveButton(okButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check = 1;

                            Log.d("@@@@?", check + "");
                        }
                    })
                    .setNegativeButton(cancelButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check = 0;
                            Log.d("@@@@??", check + "");
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkPhoneStatePermission(Context ct) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(ct, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions((Activity) ct, new String[]{Manifest.permission.READ_PHONE_STATE},
                        Constants.EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public static boolean checkStoragePermission(Context ct) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(ct, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions((Activity) ct, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public static String changeDateToMDY(String input) {

        String outputDateStr = "";
        try {
            DateFormat outputFormat = new SimpleDateFormat("MM-dd-yyyy");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // String input="2013-06-24";
            Date date = inputFormat.parse(input);
            outputDateStr = outputFormat.format(date);
            Log.i("output", outputDateStr);

        } catch (ParseException p) {
            p.printStackTrace();

        }
        return outputDateStr;
    }

    public static String changeDateToY(String input) {

        String outputDateStr = "";
        try {
            DateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            // String input="2013-06-24";
            Date date = inputFormat.parse(input);
            outputDateStr = outputFormat.format(date);
            Log.i("output", outputDateStr);

        } catch (ParseException p) {
            p.printStackTrace();

        }
        return outputDateStr;
    }

    public static int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        // Constant.Log("No of Camera", "" + numberOfCameras);
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Log.d(DEBUG_TAG, "Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    /**
     * Hides the soft keyboard
     */
    public static void hidekeyboard(Context context, View view) {
        //((Activity)context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Context ctx) {
        //  Constant.Sop("Hide keyboard=====");
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) c.getSystemService(c.INPUT_METHOD_SERVICE);
        view.requestFocus();
//        inputMethodManager.showSoftInput(view, 0);
    }

    public static boolean isNetConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;
        Log.d(">>>>picturePath", picturePath);
        Log.d(">>>>getScaledBitmap", "thumbnail" + BitmapFactory.decodeFile(picturePath, sizeOptions));
        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    public Bitmap changeOrientationBitmap(String picPath, int x, int y) {
//        File f = new File(picPath);
        Matrix mat = new Matrix();
        mat.postRotate(Orientation(picPath));
        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        bmp = getScaledBitmap(picPath, x, y);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        Bitmap correctBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), mat, true);

        Bitmap circleBitmap = Bitmap.createBitmap(correctBmp.getWidth(), correctBmp.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(correctBmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c1 = new Canvas(circleBitmap);
        c1.drawCircle(correctBmp.getWidth() / 2, correctBmp.getHeight() / 2, correctBmp.getWidth() / 2, paint);


        return circleBitmap;
    }

    public static int Orientation(String filePath) {
        int angle = 0;
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return angle;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String generateUniqueName(String filename) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        filename = filename + timeStamp;
        return filename;
    }


    /*public static void callFragmentReplaceBackStack(Context context, Fragment fragment) {
        //THIS IS FOR ADDING FRAME IN A STACK AND PERFORM BACK OPTION AND REPLACE TWO SAME FRAGMENT IN A STACK PARALLEL
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left);
        fragmentManager.popBackStack();
        transaction.replace(R.id.frame, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();

    }

    //back fragment
    //call inner fragment child for back press
    public static void callFragmentNoBackStack(Context context, Fragment fragment) {

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left);
        transaction.remove(fragmentManager.findFragmentById(R.id.frame)); // resolves to A_Fragment instance
        transaction.add(R.id.frame, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
*/
    // //back fragment
  /*  public static void addFragmentFromMenu(Context context, Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        clearBackStack(context);
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        Log.d(">>>>check1", manager.getBackStackEntryCount() + "");
        if (manager.getBackStackEntryCount() > 0) {
            Log.d(">>>>check2", "PIF");
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            Log.d(">>>>checktest", manager.getBackStackEntryCount() + "");
            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
                //fragment not in back stack, create it.
                Log.d(">>>>check3", "CIF");
                addFragment(fragment, manager, backStateName);
            }
        } else // no fragments
        {
            Log.d(">>>>check4", "PELSE");
            addFragment(fragment, manager, backStateName);
        }
    }
*/
    //back fragment
   /* public static void addFragment(Fragment fragment, FragmentManager manager, String backStateName) {
        Log.d(">>>>check5", "addFragment");
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame, fragment, backStateName);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(backStateName);
        ft.commit();
    }*/

    //back fragment
    public static void clearBackStack(Context context) {
        ((FragmentActivity) context).getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

   /* public static void callFragmentChildNoBackStack(Context context, Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left);
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    public static void callFragmentRemoveAll(Context context) {
        if (((FragmentActivity) context).getSupportFragmentManager().getFragments() != null && ((FragmentActivity) context).getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < ((FragmentActivity) context).getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = ((FragmentActivity) context).getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

   /* public static void setDateForAgeCal(Context context, final TextView tv_date, final EditText editText, final String TAG) {

        final int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.d("@@@@SystemYEAR", mYear + "");
        Log.d("@@@@SystemmMonth", (mMonth + 1) + "");
        Log.d("@@@@System mDay", mDay + "");

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date newDate = null;

                        // String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Log.d(TAG + "@@@@selectedDate", selectedDate);
                        try {
                            newDate = format.parse(selectedDate);
                            Log.d("@@@@newDate", newDate + "");
                            selectedDate = format.format(newDate);
                            tv_date.setText(selectedDate);
                            if (getAge(selectedDate)==0)
                            {
                                editText.setText("Less Than a Years");
                            }
                            else
                            {
                                editText.setText(String.valueOf(getAge(selectedDate)));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint("Select Date");
                tv_date.setText("");
                editText.setText("");
            }
        });
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public static int getAge(String date) {

        int age = 0;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(date);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(">>>age", age + "");
        return age;
    }*/

    public static void setDateForAgeCal(Context context, final TextView tv_date, final EditText editText, final String TAG) {

        final int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.d("@@@@SystemYEAR", mYear + "");
        Log.d("@@@@SystemmMonth", (mMonth + 1) + "");
        Log.d("@@@@System mDay", mDay + "");

        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date DateDOB = null;
                        Date DateCurrent = null;

                        // String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Log.d(TAG + "@@@@selectedDate", selectedDate);
                        try {
                            DateDOB = format.parse(selectedDate);
                            Log.d("@@@@newDate", DateDOB + "");
                            selectedDate = format.format(DateDOB);
                            tv_date.setText(selectedDate);

                            DateCurrent = format.parse(currentDate());
                            int yearAge = monthsBetween(DateDOB, DateCurrent) / 12;
                            int monthAge = monthsBetween(DateDOB, DateCurrent) % 12;
                            if (Utils.currentDate().equals(selectedDate)) {
                                editText.setText("0.0");
                            } else {
                                editText.setText(String.valueOf(yearAge + "." + monthAge));
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d(">>>cancelDate", "LOL");
                tv_date.setHint("Select Date");
                tv_date.setText("");
                editText.setText("");
            }
        });
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    static int monthsBetween(Date a, Date b) {
        Calendar cal = Calendar.getInstance();
        if (a.before(b)) {
            cal.setTime(a);
        } else {
            cal.setTime(b);
            b = a;
        }
        int c = 0;
        while (cal.getTime().before(b)) {
            cal.add(Calendar.MONTH, 1);
            c++;
        }
        return c - 1;
    }

    public static String readJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("village_master.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void transparentWindow(Activity context) {
        context.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    public static void blink(final TextView textView) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView.getVisibility() == View.VISIBLE) {
                            textView.setVisibility(View.INVISIBLE);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                        }
                        Utils.blink(textView);
                    }
                });
            }
        }).start();
    }

    public static String sendTextLocalSms(Context context, String str_msg, String str_number) {
        try {
            // Construct data
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String apiKey = "apikey=" + URLEncoder.encode("QLyOtSNuqQE-KOxG9RRZGFVVJCFOI6riWOKOSyiC8x", "UTF-8");
            String message = "&message=" + URLEncoder.encode(str_msg, "UTF-8");
            String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
            String numbers = "&numbers=" + URLEncoder.encode(str_number, "UTF-8");

            // Send data
            String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
            Log.d(">>>>sms uri", data);
            URL url = new URL(data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult = "";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult = sResult + line + " ";
            }
            rd.close();

            return sResult;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    public static long methodConvertDateIntoMili(String date) {
        long milliseconds = 0;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

    public static boolean methodIsThisDateValid(String dateToValidate) {

        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String methodGenerateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public static TranslateAnimation methodshakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public static String methodConvertFirstCharInUpper(String str) {

        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char) (ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char) (ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
    }

    public static void methodObjectAnim(Context context, Button button, TextView textView) {
        ValueAnimator colorAnim = ObjectAnimator.ofInt(button, "backgroundColor", RED, BLACK);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();
    }


    public static void methodByteArrayStringIntoImage(Context context, String str_byte_array, ImageView imageView) {
        byte[] byteArray = Base64.decode(str_byte_array, Base64.DEFAULT);
        Bitmap bmp1 = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Log.d(">>>>bitmap", bmp1 + "");
        imageView.setImageBitmap(bmp1);
    }

    public static void disableInput(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    public static Bitmap resizeImageAspectRatio(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = 120;
            int height = 120;
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static String methodDecimalLastTwoPoint(Double value) {
        return String.format("%.2f", value);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String methodConvertDateFormatter(String paramdate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        // SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, h:mm a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(paramdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        System.out.println(formattedDate);
        return formattedDate;
    }

    public static void methodOpenUrlOnBrowser(Context context, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static String methodGetDayShift(Context context) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            return "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            return "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            return "Good Night";
        }
        return "";
    }


    public static String toBase64(String message) {
        byte[] data;
        try {
            data = message.getBytes("UTF-8");
            String base64Sms = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Sms;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMd5(Context context, String input) {
        // Static getInstance method is called with hashing MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // digest() method is called to calculate message digest
        //  of an input digest() return array of byte
        byte[] messageDigest = md.digest(input.getBytes());

        // Convert byte array into signum representation
        BigInteger no = new BigInteger(1, messageDigest);

        // Convert message digest into hex value
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    public static byte[] convertImageToByte(Context context, Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
