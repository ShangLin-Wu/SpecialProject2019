package com.example.samwu.myapp.Category.FurniturePackage.Furniture_Used;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samwu.myapp.R;
import com.squareup.picasso.Picasso;

public class Furniture_Used_SwipeAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    Furniture_Used_ViewPager s=new Furniture_Used_ViewPager();

    String[] image_resorces = new String[6];

    public Furniture_Used_SwipeAdapter(Context ctx)
    {

        this.ctx=ctx;
    }
    @Override
    public int getCount() {
        return image_resorces.length;

    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view ==object);
    }
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide,container,false);

        ImageView imgslide = view.findViewById(R.id.slideimg);

        image_resorces[0] = s.getA();
        image_resorces[1] = s.getB();
        image_resorces[2] = s.getC();
        image_resorces[3] = s.getD();
        image_resorces[4] = s.getE();
        image_resorces[5] = s.getF();

        Picasso.with(ctx).load(image_resorces[position]).resize(480,257).into(imgslide);

        container.addView(view);
        return  view;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
