package com.example.samwu.myapp.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.FurniturePackage.Furniture_New.Furniture_New_Uploaded;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Category.FurniturePackage.Furniture_Used.Furniture_Used_Uploaded;

public class categoryFurniture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_furnitures);

    }


    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
        startActivity(touploadproduct);
        finish();
    }
    public void clickback(View view) {
        Intent clickback = new Intent(this, home.class);
        startActivity(clickback);
        finish();
    }
    public void clickNEW(View view) {
        Intent clickNEW = new Intent(this, Furniture_New_Uploaded.class);
        startActivity(clickNEW);
        finish();
    }
    public void clickUSED(View view) {
        Intent clickUSED = new Intent(this, Furniture_Used_Uploaded.class);
        startActivity(clickUSED);
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
