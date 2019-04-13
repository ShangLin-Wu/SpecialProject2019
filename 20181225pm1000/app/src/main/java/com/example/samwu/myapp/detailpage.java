package com.example.samwu.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.ShowProductPage.all.AllProductPage;

import java.net.URISyntaxException;

public class detailpage extends AppCompatActivity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);

        details=findViewById(R.id.detailtext);

        Intent intent = getIntent();
        intent.getExtras();

        String detailstext= intent.getStringExtra("details");
       details.setText(detailstext);
    }
    public void detailpage(View view)
    {
        Intent detailpage = new Intent(this, super.getClass());
        startActivity(detailpage);
        finish();
    }


    public void clickback(View view) {
        Intent clickback = new Intent(this, favorite.class);

        startActivity(clickback);
        finish();
    }
}
