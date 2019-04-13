package com.example.samwu.myapp.ShowProductPage.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.samwu.myapp.R;
import com.example.samwu.myapp.ShowProductPage.personal.PersonalUploaded;

public class SearchProduct_detailpage extends AppCompatActivity {

    TextView details;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);

        back=findViewById(R.id.signinback);
        details = findViewById(R.id.detailtext);

        final Intent intent = getIntent();
        intent.getExtras();

        String detailstext = intent.getStringExtra("details");
        details.setText(detailstext);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clickback = new Intent(SearchProduct_detailpage.this, SearchAllProductPage.class);
                clickback.putExtras(intent);
                startActivity(clickback);
                finish();
            }
        });

    }

}
