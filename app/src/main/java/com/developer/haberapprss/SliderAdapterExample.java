package com.developer.haberapprss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private int mCount;
    private List<SliderModel> user_list;
    public SliderAdapterExample(Context context,List<SliderModel> user_list) {
        this.context = context;
        this.user_list = user_list;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String haberTitle = ""+ user_list.get(position).getHaberBaslik();
                String haberTarih = ""+ user_list.get(position).getHaberTarih();
                String haberDetayResim = user_list.get(position).getHaberDetayResim();
                String habericerik = user_list.get(position).getIplink();

                Intent intent = new Intent(context, HaberDetay.class);
                intent.putExtra("haberTitle",haberTitle);
                intent.putExtra("haberTarih",haberTarih);
                intent.putExtra("haberDetayResim",haberDetayResim);
                intent.putExtra("habericerik",habericerik);
                context.startActivity(intent);
            }
        });

        viewHolder.textViewDescription.setText(user_list.get(position).getHaberBaslik());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.imageGifContainer.setVisibility(View.GONE);
        Glide.with(viewHolder.itemView)
                .load(user_list.get(position).getHaberResim())
                .fitCenter()
                .into(viewHolder.imageViewBackground);



        /*""*/


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }


}