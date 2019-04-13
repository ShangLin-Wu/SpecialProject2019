package com.example.samwu.myapp.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.ElectronicPackage.Electronics_3C.Electronics_3C_Uploaded;
import com.example.samwu.myapp.Category.ElectronicPackage.Electronics_Cellphone.Electronics_Cellphone_Uploaded;
import com.example.samwu.myapp.Category.ElectronicPackage.Electronics_Computer.Electronics_Computer_Uploaded;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;

public class category3C extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category3_c);
    }





    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
        startActivity(touploadproduct);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
    public void clickback(View view) {
        Intent clickback= new Intent(this, home.class);
        startActivity(clickback);
        finish();

    }
    public void clickHOME(View view) {
        Intent clickHOME= new Intent(this, Electronics_3C_Uploaded.class);
        startActivity(clickHOME);
        finish();
    }
    public void clickCELLPHONE(View view) {
        Intent clickCELLPHONE = new Intent(this, Electronics_Cellphone_Uploaded.class);
        startActivity(clickCELLPHONE);
        finish();
    }
    public void clickCOUMPUTER(View view) {
        Intent clickCOUMPUTER= new Intent(this, Electronics_Computer_Uploaded.class);
        startActivity(clickCOUMPUTER);
        finish();
    }
    /********ImageButtonAndButtonEvent*********/
    public void tohome(View view) {
        Intent tohome = new Intent(this , home.class);
        startActivity(tohome);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
    public void tofavorite(View view) {
        Intent tofavorite = new Intent(this , favorite.class);
        startActivity(tofavorite);
    }
    public void tosearch(View view) {
        Intent tosearch = new Intent(this , cloudSearch.class);
        startActivity(tosearch);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }
    public void tomembercentre(View view) {
        Intent tologinpage = new Intent(this , cloudProfile.class);
        startActivity(tologinpage);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

}
