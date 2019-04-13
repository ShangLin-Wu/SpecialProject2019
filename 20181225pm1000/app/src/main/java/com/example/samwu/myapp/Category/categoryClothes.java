package com.example.samwu.myapp.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.ClothesPackage.Clothes_Pants.Clothes_Pants_Uploaded;
import com.example.samwu.myapp.Category.ClothesPackage.Clothes_Shirts.Clothes_Shirts_Uploaded;
import com.example.samwu.myapp.Category.ClothesPackage.Clothes_Shoes.Clothes_Shoes_Uploaded;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;

public class categoryClothes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_clothes);


    }



    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
        startActivity(touploadproduct);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
    public void clickShirts(View view) {
        Intent clickShirts= new Intent(this, Clothes_Shirts_Uploaded.class);
        startActivity(clickShirts);
    }
    public void clickPants(View view) {
        Intent clickPants= new Intent(this, Clothes_Pants_Uploaded.class);
        startActivity(clickPants);
    }
    public void clickShoes(View view) {
        Intent clickShoes= new Intent(this, Clothes_Shoes_Uploaded.class);
        startActivity(clickShoes);
    }
    public void clickback(View view) {
        Intent clickback= new Intent(this, home.class);
        startActivity(clickback);
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
