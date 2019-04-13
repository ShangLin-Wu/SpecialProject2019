package com.example.samwu.myapp.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.BooksPackage.BooksNew.NewBooks_Uploaded;
import com.example.samwu.myapp.Category.BooksPackage.BooksUsed.UsedBooks_Uploaded;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;

public class categoryBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_books);
    }




    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
        startActivity(touploadproduct);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
    public void clickNEW(View view) {
        Intent clickNEW = new Intent(this, NewBooks_Uploaded.class);
        startActivity(clickNEW);
        finish();
    }
    public void clickUSED(View view) {
        Intent clickUSED = new Intent(this, UsedBooks_Uploaded.class);
        startActivity(clickUSED);
        finish();
    }
    public void clickback(View view) {
        Intent clickback = new Intent(this, home.class);
        startActivity(clickback);
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
