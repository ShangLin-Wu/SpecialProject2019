package com.example.samwu.myapp.Search;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samwu.myapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public List<cloudSearchProduct> cloudSearchProductList;

    public ProductListAdapter(List<cloudSearchProduct> cloudSearchProduct) {

        this.cloudSearchProductList = cloudSearchProduct;
    }



    public ProductListAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_searchresult, parent, false);


        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(animation);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.producttitle_text.setText(cloudSearchProductList.get(position).getProducttitle());
        holder.details_text.setText(cloudSearchProductList.get(position).getDetails());

        Log.d("ProductListAdapterUri", cloudSearchProductList.get(position).getA());

        Uri uri = Uri.parse(cloudSearchProductList.get(position).getA());

        Glide.with(getApplicationContext()).load(uri).into(holder.prod_image);


    }

    @Override
    public int getItemCount() {
        return cloudSearchProductList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView producttitle_text;
        public TextView details_text;
        public CircleImageView prod_image;
        // public ImageView prod_image ;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            producttitle_text = mView.findViewById(R.id.producttitle_text);
            details_text = mView.findViewById(R.id.status_text);
            prod_image = mView.findViewById(R.id.product_image);


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
