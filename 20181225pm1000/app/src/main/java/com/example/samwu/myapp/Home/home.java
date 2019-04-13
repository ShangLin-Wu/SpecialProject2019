package com.example.samwu.myapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.samwu.myapp.Chatroom.ShowChatActivity;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.LoginUI.SharesPreferencesConfig;
import com.example.samwu.myapp.LoginUI.SignIn;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.category3C;
import com.example.samwu.myapp.Category.categoryBooks;
import com.example.samwu.myapp.Category.categoryClothes;
import com.example.samwu.myapp.Category.categoryFurniture;
import com.example.samwu.myapp.ShowProductPage.all.AllUploaded;
import com.example.samwu.myapp.Chatroom.chatpage;

public class home extends AppCompatActivity {


    private SharesPreferencesConfig preferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /********************************LoginOrNot********************************/
        preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
        if(preferencesConfig.readLoginStatus()==false)
        {
            Toast.makeText(this, "You've to login first. ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(home.this,SignIn.class ));
            finish();
        }
        /**------------------------------LoginOrNot------------------------------**/

    }



    public void clickCloth(View view) {
        Intent clickClothes = new Intent(this, categoryClothes.class);
        startActivity(clickClothes);
        finish();
    }
    public void click3C(View view) {
        Intent click3C = new Intent(this, category3C.class);
        startActivity(click3C);
        finish();
    }
    public void clickBook(View view) {
        Intent clickBooks = new Intent(this, categoryBooks.class);
        startActivity(clickBooks);
        finish();
    }
    public void clickFurniture(View view) {
        Intent clickFurnitures = new Intent(this, categoryFurniture.class);
        startActivity(clickFurnitures);
        finish();
    }
    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
        startActivity(touploadproduct);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    public void toChatroom(View view) {
        Intent tohome = new Intent(this , chatpage.class);
        startActivity(tohome);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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



    public void chatSaved(View view)
    {
        Intent tohome = new Intent(this , ShowChatActivity.class);
        startActivity(tohome);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    public void allproduct(View view) {
        Intent tofavorite = new Intent(this , AllUploaded.class);
        startActivity(tofavorite);

    }
}
