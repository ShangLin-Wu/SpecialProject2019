package com.example.samwu.myapp.ShowProductPage.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.samwu.myapp.R;
import com.example.samwu.myapp.ShowProductPage.all.AllUploaded;

public class PersonalProduct_detailpage extends AppCompatActivity {

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
        Intent clickback = new Intent(this, PersonalUploaded.class);

        startActivity(clickback);
        finish();
    }
}
