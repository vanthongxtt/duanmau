package vn.sefviapp.asm_ps09105.View.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import vn.sefviapp.asm_ps09105.R;

public class ChaoActivity extends AppCompatActivity {
    ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);
        imgLogo = findViewById(R.id.imgLogo);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        Animation animFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        imgLogo.startAnimation(animFade);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ChaoActivity.this, LoginActivity.class));
                finish();
            }
        },2000);
    }
}
