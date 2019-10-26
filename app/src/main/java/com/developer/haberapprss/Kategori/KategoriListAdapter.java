package com.developer.haberapprss.Kategori;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developer.haberapprss.CustomItemClickListener;
import com.developer.haberapprss.R;

import java.util.List;

public class KategoriListAdapter extends RecyclerView.Adapter<KategoriListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView haberbasligi;
        public ImageView haberresmi;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            haberbasligi = itemView.findViewById(R.id.listDetail);
            haberresmi = itemView.findViewById(R.id.listPhoto);


        }
    }

    private List<KategoriListModel> user_list;
    private Context context;
    private CustomItemClickListener listener;
    KategoriListAdapter(List<KategoriListModel> user_list, Context context, CustomItemClickListener listener){
        this.user_list = user_list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KategoriListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_kategori_list_item,parent,false);
        final KategoriListAdapter.ViewHolder view_holder = new KategoriListAdapter.ViewHolder(vr);

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,view_holder.getPosition());
            }
        });

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriListAdapter.ViewHolder holder, int position) {

        holder.haberbasligi.setText(user_list.get(position).getHaberBaslik());


        Glide.with(context)
                .load(user_list.get(position).getHaberResim())
                .error(R.drawable.appicon)
                .placeholder(R.drawable.appicon)
                .into(holder.haberresmi);




    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }




}