package com.example.samwu.myapp.ShowProductPage.all;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.samwu.myapp.Category.BooksPackage.BooksNew.NewBooks_Page;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.R;

public class AllProduct_detailpage extends AppCompatActivity {

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

    public void clickback(View view) {
        Intent clickback = new Intent(this, AllUploaded.class);

        startActivity(clickback);
        finish();
    }
}
