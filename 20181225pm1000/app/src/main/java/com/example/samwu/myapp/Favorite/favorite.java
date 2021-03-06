package com.example.samwu.myapp.Favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.LoginUI.SharesPreferencesConfig;
import com.example.samwu.myapp.LoginUI.SignIn;
import com.example.samwu.myapp.Model.getUploadedProductsData;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.ShowProductPage.all.AllProductPage;
import com.example.samwu.myapp.ShowProductPage.search.SearchAllProductPage;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class favorite extends AppCompatActivity implements favoriteListAdapter.OnItemClickListener {

    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    RecyclerView mResultList;
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private List<getfavorite> getfavoriteList;
    private List<getUploadedProductsData> getUploadedProductsDataList;
    private favoriteListAdapter favoriteListAdapter;

    private SharesPreferencesConfig preferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        /********************************LoginOrNot********************************/
        preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
        if (preferencesConfig.readLoginStatus() == false) {
            Toast.makeText(this, "You've to login first. ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SignIn.class));
            finish();
        }
        /**------------------------------LoginOrNot------------------------------**/

        getfavoriteList = new ArrayList<>();

        getUploadedProductsDataList = new ArrayList<>();

        favoriteListAdapter = new favoriteListAdapter(getfavoriteList);

        mResultList = findViewById(R.id.result_list);

        getfavoriteList.clear();
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        //favoriteListAdapter.setOnItemClickListener(favorite.this);

        final CollectionReference favoriteCollection = mDB.collection("UserProfiles").document(mAuth.getEmail())
                .collection("Favorite");

        favoriteCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                //取得favoriteCollection路徑之所有商品
                for (DocumentChange document : querySnapshot.getDocumentChanges()) {
                  //  Log.d("WYYYYYYY", String.valueOf(document.getDocument()));

                    //取得每一項商品之狀態
                    String status = document.getDocument().getString("status");

                    //狀態為true則代表為喜好商品，false則為非喜好商品
                    if (status.equals("true")) {

                        Log.d("Error : ", document.getDocument().getString("producttitle"));
                        final String producttitle = document.getDocument().getString("producttitle");
                        final String email = document.getDocument().getString("email");
                        Log.d("EMAAAAA", String.valueOf(email));

                        CollectionReference getProductsDetail = mDB.collection("Products");
                        getProductsDetail.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                                    //確定該商品之帳號及商品名稱是否相符
                                    if (dc.getDocument().getString("email").equals(email) && dc.getDocument().getString("producttitle").equals(producttitle)) {
                                        //  String productsPhoto = task.getResult().getString("a");
                                        //  String productsDetails = task.getResult().getString("details");
                                        /* 將該商品存入getter中 */
                                        getfavorite saveData = dc.getDocument().toObject(getfavorite.class);
                                        getUploadedProductsData getAllofData = dc.getDocument().toObject(getUploadedProductsData.class);

                                        Log.d("DATATTATATA", String.valueOf(saveData.getEmail()));

                                        favoriteListAdapter.notifyDataSetChanged();
                                        getfavoriteList.add(saveData);
                                        getUploadedProductsDataList.add(getAllofData);
                                        //將喜好商品於清單中列出
                                        mResultList.setAdapter(favoriteListAdapter);


                                    }
                                }
                            }
                        });
                    }

                }

            }
        });



        favoriteListAdapter.setOnItemClickListener(favorite.this);
    }


    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this, cloudUploadProducts.class);
        startActivity(touploadproduct);
        finish();
    }

    /********ImageButtonAndButtonEvent*********/
    public void tohome(View view) {
        Intent tohome = new Intent(this, home.class);
        startActivity(tohome);
        finish();
    }

    public void tofavorite(View view) {
        Intent tofavorite = new Intent(this, favorite.class);
        startActivity(tofavorite);
    }

    public void tosearch(View view) {
        Intent tosearch = new Intent(this, cloudSearch.class);
        startActivity(tosearch);
        finish();

    }

    public void tomembercentre(View view) {
        Intent tologinpage = new Intent(this, cloudProfile.class);
        startActivity(tologinpage);
        finish();
    }

    @Override
    public void onItemClick(int position) {

        Log.d("CLoick", "*******");
        Intent intent = new Intent(this, ProductPagebackfavorite.class);

        String a = getUploadedProductsDataList.get(position).getA();
        String b = getUploadedProductsDataList.get(position).getB();
        String c = getUploadedProductsDataList.get(position).getC();
        String d = getUploadedProductsDataList.get(position).getD();
        String e = getUploadedProductsDataList.get(position).getE();
        String f = getUploadedProductsDataList.get(position).getF();
        String producttitle = getUploadedProductsDataList.get(position).getProducttitle();
        String email = getUploadedProductsDataList.get(position).getEmail();
        String details = getUploadedProductsDataList.get(position).getDetails();
        String phone = getUploadedProductsDataList.get(position).getPhone();
        String uploadtime = getUploadedProductsDataList.get(position).getUploadtime();

        String[] i = new String[]{a, b, c, d, e, f, producttitle, email, details, phone};
        String nopic = "https://ppt.cc/fchLDx";
        for (int j = 0; j <= 5; j++) {
            Log.d("SSSSSSGGGGGGGGGGS", j + "--------" + i[j]);
            if (i[j] == null) {

                Log.d("SSSSSSS", j + "---------------------" + i[j]);
                i[j] = nopic;

            }

        }
        if (i[8] == null) i[8] = "沒有內容...";
        if (i[9] == null) i[9] = "這位同學很懶沒有留電話...";

        intent.putExtra("a", i[0]);
        intent.putExtra("b", i[1]);
        intent.putExtra("c", i[2]);
        intent.putExtra("d", i[3]);
        intent.putExtra("e", i[4]);
        intent.putExtra("f", i[5]);
        intent.putExtra("producttitle", i[6]);
        intent.putExtra("email", i[7]);
        intent.putExtra("details", i[8]);
        intent.putExtra("phone", i[9]);
        intent.putExtra("uploadtime", uploadtime);

        startActivity(intent);
    }
}
