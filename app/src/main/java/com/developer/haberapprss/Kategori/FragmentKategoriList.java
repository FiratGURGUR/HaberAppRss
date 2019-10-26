package com.developer.haberapprss.Kategori;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.developer.haberapprss.CustomItemClickListener;
import com.developer.haberapprss.HaberDetay;
import com.developer.haberapprss.MainActivity;
import com.developer.haberapprss.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class FragmentKategoriList extends Fragment {
    RecyclerView recyclerView;
    KategoriListAdapter katAdapter;
    List<KategoriListModel> katModelList;
    String Kategori="";
    TextView section;
    Button back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kategori_list,container,false);
        recyclerView = view.findViewById(R.id.recycler_kategori_list);
        katModelList = new ArrayList<>();

        section = view.findViewById(R.id.textView25);

        back = view.findViewById(R.id.button8);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            Kategori  = extras.getString("kategori");
            section.setText(Kategori);
            if (Kategori.equals("Son Dakika")){
                getData("https://www.mynet.com/haber/rss/sondakika");
            }else if(Kategori.equals("Dünya")){
                getData("https://www.mynet.com/haber/rss/kategori/dunya");
            }else if(Kategori.equals("Gündem")){
                getData("https://www.mynet.com/haber/rss/kategori/guncel/");
            }else if(Kategori.equals("Saglik")){
                getData("https://www.mynet.com/haber/rss/kategori/saglik/");
            }else if(Kategori.equals("Teknoloji")){
                getData("https://www.mynet.com/haber/rss/kategori/teknoloji/");
            }else if(Kategori.equals("Yaşam")){
                getData("https://www.mynet.com/haber/rss/kategori/yasam/");
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }


    public void getData(String url){
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
                            for (int i=0;i<k;i++){
                                JSONObject jsonObject2 = entityArray.getJSONObject(i);
                                String title = jsonObject2.getString("title");
                                String pubDate = jsonObject2.getString("pubDate");
                                String iplink = jsonObject2.getString("iplink");
                                String description = jsonObject2.getString("description");
                                String image = jsonObject2.getString("img300x300");
                                String imagedetay = jsonObject2.getString("img640x360");

                                Log.i("firat" , "IMAGEE " + image);

                                katModelList.add(new KategoriListModel(image,title,pubDate,imagedetay,iplink));

                            }
                            katAdapter =new KategoriListAdapter(katModelList, getActivity(), new CustomItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {

                                    String haberTitle = ""+ katModelList.get(position).getHaberBaslik();
                                    String haberTarih = ""+ katModelList.get(position).getHaberTarih();
                                    String haberDetayResim = katModelList.get(position).getHaberDetayResim();
                                    String habericerik = katModelList.get(position).getIplink();

                                    Intent intent = new Intent(getActivity(), HaberDetay.class);
                                    intent.putExtra("haberTitle",haberTitle);
                                    intent.putExtra("haberTarih",haberTarih);
                                    intent.putExtra("haberDetayResim",haberDetayResim);
                                    intent.putExtra("habericerik",habericerik);
                                    startActivity(intent);

                                }
                            });
                            StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                            linearLayoutManager.scrollToPosition(0);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(katAdapter);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    private void passOtherFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


}
