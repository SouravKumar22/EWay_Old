package ctd.example.anuj.newewaybill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=SplashActivity.this;
        setContentView(R.layout.splash_activity);
        setTitle("");
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                goToNextActivity(R.anim.slide_in_from_right, R.anim.fade_out);
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);


        ImageView proceed= (ImageView) findViewById(R.id.proceed);
        LinearLayout ll_proceed = (LinearLayout) findViewById(R.id.ll_proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });
        ll_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    private void goToNextActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(context, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
    }
}
