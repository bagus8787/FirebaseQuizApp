package com.naman.firebasequizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.naman.firebasequizapp.admin.CreateActivity;

import javax.xml.transform.Result;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private com.facebook.ads.AdView adView;
    TextView tq,ca,wa,hu, ni;
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        adView = new AdView(this, getResources().getString(R.string.banner), AdSize.BANNER_HEIGHT_90);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        // Request an ad
        adView.loadAd();
        btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(this);

        tq = findViewById(R.id.tq);
        ca=findViewById(R.id.ca);
        ni=findViewById(R.id.ni);
        wa=findViewById(R.id.wa);
        hu=findViewById(R.id.hu);

        Intent i=getIntent();
        String questions=i.getStringExtra("total");
        String nilai=i.getStringExtra("nilai");
        String correct=i.getStringExtra("correct");
        String hint=i.getStringExtra("hint");
        String wrong=i.getStringExtra("wrong");

        ni.setText(nilai);
        tq.setText(questions);
        ca.setText(correct);
        hu.setText(hint);
        wa.setText(wrong);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {

            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
