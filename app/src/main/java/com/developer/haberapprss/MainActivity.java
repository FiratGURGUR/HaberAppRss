package com.developer.haberapprss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.developer.haberapprss.Kategori.KategoriList;
import com.developer.haberapprss.Kategori.KategoriListAdapter;
import com.developer.haberapprss.Kategori.KategoriListModel;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView cardDunya;
    CardView cardFinans;
    CardView cardGundem;
    CardView cardSondakika;
    CardView cardSpor;
    CardView cardSaglik;


    TextView t_dolar;
    TextView t_euro;
    TextView t_altin;
    TextView t_gbp;

    SliderView sliderView;
    List<SliderModel> katModelList;
    SliderAdapterExample adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_dolar = findViewById(R.id.textView4);
        t_euro = findViewById(R.id.textView6);
        t_altin = findViewById(R.id.textView8);
        t_gbp = findViewById(R.id.textView10);


        cardDunya= findViewById(R.id.cardDunya);
        cardFinans= findViewById(R.id.cardFinans);
        cardGundem= findViewById(R.id.cardGundem);
        cardSondakika= findViewById(R.id.cardSondakika);
        cardSpor= findViewById(R.id.cardSpor);
        cardSaglik= findViewById(R.id.cardSaglik);


        cardDunya.setOnClickListener(this);
        cardFinans.setOnClickListener(this);
        cardGundem.setOnClickListener(this);
        cardSondakika.setOnClickListener(this);
        cardSpor.setOnClickListener(this);
        cardSaglik.setOnClickListener(this);


        sliderView = findViewById(R.id.imageSlider);
        katModelList = new ArrayList<>();

        getDatatoSlider("https://www.mynet.com/haber/rss/sondakika");




    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
             case R.id.cardDunya:
                Intent intent =  new Intent(MainActivity.this, KategoriList.class);
                intent.putExtra("kategori","Dünya");
                startActivity(intent);
                break;
            case R.id.cardFinans:
                Intent intent2 =  new Intent(MainActivity.this, KategoriList.class);
                 intent2.putExtra("kategori","Yaşam");
                 startActivity(intent2);
                break;
            case R.id.cardGundem:
                Intent intent3 =  new Intent(MainActivity.this, KategoriList.class);
                intent3.putExtra("kategori","Gündem");
                startActivity(intent3);
                break;
            case R.id.cardSondakika:
                Intent intent4 =  new Intent(MainActivity.this, KategoriList.class);
                intent4.putExtra("kategori","Son Dakika");
                startActivity(intent4);
                break;
            case R.id.cardSpor:
                Intent intent5 =  new Intent(MainActivity.this, KategoriList.class);
                intent5.putExtra("kategori","Teknoloji");
                startActivity(intent5);
                break;
            case R.id.cardSaglik:
                Intent intent6 =  new Intent(MainActivity.this, KategoriList.class);
                intent6.putExtra("kategori","Saglik");
                startActivity(intent6);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vericek();
    }

    public void vericek(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://finans.truncgil.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        DovizApiInterface client = retrofit.create(DovizApiInterface.class);

        Call<ResponseBody> call = client.OgrencileriAl();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject =new JSONObject(response.body().string());


                    String dolar = jsonObject.getString("ABD DOLARI");
                    JSONObject Amerikandolar = new JSONObject(dolar);
                    String dolarAlis = Amerikandolar.getString("Alış");
                    String dolarSatis = Amerikandolar.getString("Satış");


                    String euro = jsonObject.getString("EURO");
                    JSONObject europara = new JSONObject(euro);
                    String euroAlis = europara.getString("Alış");
                    String euroSatis = europara.getString("Satış");


                    String altin = jsonObject.getString("Gram Altın");
                    JSONObject altinceyrek = new JSONObject(altin);
                    String altinAlis = altinceyrek.getString("Alış");
                    String altinSatis = altinceyrek.getString("Satış");


                    String sterlin = jsonObject.getString("İNGİLİZ STERLİNİ");
                    JSONObject sterlinpara = new JSONObject(sterlin);
                    String sterlinAlis = sterlinpara.getString("Alış");
                    String sterlinSatis = sterlinpara.getString("Satış");


                    t_dolar.setText(dolarSatis);
                    t_euro.setText(euroSatis);
                    t_altin.setText(altinSatis);
                    t_gbp.setText(sterlinSatis);


                    Log.i("firat" , "DOLAR : "+ dolar + " Satış : " + dolarSatis);
                    Log.i("firat" , "EURO : "+ euro + " Satış : " + euroSatis);
                    Log.i("firat" , "Gram Altın : "+ altin + " Satış : " + altinSatis);
                    Log.i("firat" , "İNGİLİZ STERLİNİ : "+ sterlin + " Satış : " + sterlinSatis);



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("firat" , t.toString());
            }
        });

    }



    public void getDatatoSlider(String url){
        AndroidNetworking.get(url)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String xmlString = response;  // some XML String previously created
                            XmlToJson xmlToJson = new XmlToJson.Builder(xmlString).build();
                            JSONObject jsonObject = xmlToJson.toJson();

                            String rss = jsonObject.getString("rss");
                            JSONObject jsonObjectrss = new JSONObject(rss);
                            String channel = jsonObjectrss.getString("channel");
                            JSONObject jsonObjectitems = new JSONObject(channel);
                            Log.i("firat" , " ************ "+ jsonObjectitems.toString());


                            String entity = jsonObjectitems.getString("item");
                            JSONArray entityArray = new JSONArray(entity);
                            int k = entityArray.length();
                            for (int i=0;i<11;i++){
                                JSONObject jsonObject2 = entityArray.getJSONObject(i);
                                String title = jsonObject2.getString("title");
                                String pubDate = jsonObject2.getString("pubDate");
                                String iplink = jsonObject2.getString("iplink");
                                String description = jsonObject2.getString("description");
                                String image = jsonObject2.getString("img300x300");
                                String imagedetay = jsonObject2.getString("img640x360");

                                Log.i("firat" , "IMAGEE " + image);

                                katModelList.add(new SliderModel(image,title,pubDate,imagedetay,iplink));

                            }
                            adapter= new SliderAdapterExample(MainActivity.this,katModelList);
                            adapter.setCount(katModelList.size());
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView.setAutoCycleDirection(SliderView.SCREEN_STATE_ON);
                            sliderView.setIndicatorSelectedColor(Color.WHITE);
                            sliderView.setIndicatorUnselectedColor(Color.GRAY);
                            sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
                                @Override
                                public void onIndicatorClicked(int position) {
                                    sliderView.setCurrentPagePosition(position);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }





}
