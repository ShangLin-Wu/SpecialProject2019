package com.example.samwu.myapp.ShowProductPage.search;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samwu.myapp.R;

public class SearchViewpager extends AppCompatActivity {

    ViewPager viewPager;
    SearchProductSwipeAdapter adapter;

    //the Photo th
    private TextView[] mDots;
    private LinearLayout mDotLayout;

    public Intent intent=getIntent() ;

    public static String a,b,c,d,e,f,producttitle,email,details,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        mDotLayout = findViewById(R.id.mDotLayout);

        viewPager =  findViewById(R.id.viewpager);

        adapter=new SearchProductSwipeAdapter(this);

        viewPager.setAdapter(adapter);

        intent=getIntent();

         a=intent.getStringExtra("a");
         b=intent.getStringExtra("b");
         c=intent.getStringExtra("c");
         d=intent.getStringExtra("d");
         e=intent.getStringExtra("e");
         f=intent.getStringExtra("f");

         addDotsIndicator(0);
         viewPager.addOnPageChangeListener(viewListener);
    }


    public void btnclose(View view)
    {
        Log.d("AAAAAAAAA",a+"***"+b);

        Intent mBackToProductPage = new Intent(this,SearchAllProductPage.class);
        mBackToProductPage.putExtras(intent);

        String sd = mBackToProductPage.getStringExtra("a");
        String producttitle = mBackToProductPage.getStringExtra("producttitle");
        String email = mBackToProductPage.getStringExtra("email");
        String details = mBackToProductPage.getStringExtra("details");
        String phone = mBackToProductPage.getStringExtra("phone");

        Log.d("VVVVV",producttitle+"*/*"+email+"*/*"+details+"*/*"+phone+"\n\n\n"+sd);

        startActivity(mBackToProductPage);

    }

    public  void addDotsIndicator(int position)
    {

        mDots = new TextView[6];
        mDotLayout.removeAllViews();

        for(int i =0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(SearchViewpager.this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.grey));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // For SearchProductSwipeAdapter.class
    public  String getA() {
        return a;
    }
    public  String getB() {
        return b;
    }
    public  String getC() {
        return c;
    }
    public  String getD() {
        return d;
    }
    public  String getE() {
        return e;
    }
    public  String getF() {
        return f;
    }
    public  String getProducttitle() {
        return producttitle;
    }
    public  String getEmail() {
        return email;
    }
    public  String getDetails() {
        return details;
    }
    public  String getPhone() {
        return phone;
    }
}
