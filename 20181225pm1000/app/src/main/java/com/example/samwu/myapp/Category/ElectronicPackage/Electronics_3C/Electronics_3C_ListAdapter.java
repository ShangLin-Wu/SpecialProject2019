package com.example.samwu.myapp.Category.ElectronicPackage.Electronics_3C;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samwu.myapp.Model.getUploadedProductsData;
import com.example.samwu.myapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Electronics_3C_ListAdapter extends RecyclerView.Adapter<Electronics_3C_ListAdapter.ViewHolder>{

    private Electronics_3C_ListAdapter.OnItemClickListener listener;

    public List<getUploadedProductsData> getUploadedProductsData;

    public Electronics_3C_ListAdapter(List<getUploadedProductsData> getUploadedProductsData) {
        this.getUploadedProductsData = getUploadedProductsData;

    }

    public Electronics_3C_ListAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allproductresult, parent, false);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(animation);

        return new Electronics_3C_ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.producttitle_text.setText(getUploadedProductsData.get(position).getProducttitle());


        Uri uri = Uri.parse(getUploadedProductsData.get(position).getA());

        Glide.with(getApplicationContext()).load(uri).into(holder.product_image);

        holder.status_text.setText(getUploadedProductsData.get(position).getDetails());

        Log.d("ProPPPPPPPPPPP", getUploadedProductsData.get(position).getProducttitle());

    }



    @Override
    public int getItemCount() {
        return getUploadedProductsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView producttitle_text;
        public TextView status_text;
        public CircleImageView product_image;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            producttitle_text = mView.findViewById(R.id.producttitle_text);
            status_text = mView.findViewById(R.id.status_text);
            product_image = mView.findViewById(R.id.product_image);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {

                        listener.onItemClick(position);

                    }
                }
            });

        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
