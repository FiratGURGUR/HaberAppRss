package com.developer.haberapprss;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

public class HaberDetay extends AppCompatActivity {
    Button back;
    TextView title;
    TextView tarih;
    ImageView detayResim;
    WebView haberContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haber_detay);

        back = findViewById(R.id.button3);
        title = findViewById(R.id.textView13);
        tarih = findViewById(R.id.textView14);
        detayResim = findViewById(R.id.imageView4);
        haberContent = findViewById(R.id.webviewContent);


        Bundle extras = getIntent().getExtras();


        if (extras != null)
        {
            String haberTitle = extras.getString("haberTitle");
            String haberTarih = extras.getString("haberTarih");
            String haberDetayResim = extras.getString("haberDetayResim");
            String habericerik = extras.getString("habericerik");

            String newDate = haberTarih.substring(0,20);

            title.setText(haberTitle);
            tarih.setText(newDate);




            Glide.with(this)
                    .load(haberDetayResim)
                    .into(detayResim);
            ContentYukle(habericerik);


        }





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void ContentYukle(String url){
        haberContent.getSettings().setJavaScriptEnabled(true);
        //wv.setWebChromeClient(new MyChrome());
        haberContent.loadUrl(url);

    }

}
