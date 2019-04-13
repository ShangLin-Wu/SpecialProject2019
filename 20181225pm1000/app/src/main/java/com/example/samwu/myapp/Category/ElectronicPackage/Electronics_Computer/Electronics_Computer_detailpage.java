package com.example.samwu.myapp.Category.ElectronicPackage.Electronics_Computer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.samwu.myapp.Category.BooksPackage.BooksNew.NewBooks_Page;
import com.example.samwu.myapp.Category.BooksPackage.BooksNew.NewBooks_Uploaded;
import com.example.samwu.myapp.R;

public class Electronics_Computer_detailpage extends AppCompatActivity {

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
        Intent detailpage = new Intent(this, NewBooks_Page.class);
        startActivity(detailpage);
        finish();
    }


    public void clickback(View view) {
        Intent clickback = new Intent(this, NewBooks_Uploaded.class);

        startActivity(clickback);
        finish();
    }
}