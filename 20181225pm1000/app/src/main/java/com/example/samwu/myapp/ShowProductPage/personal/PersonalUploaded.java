package com.example.samwu.myapp.ShowProductPage.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Model.getUploadedProductsData;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class PersonalUploaded extends AppCompatActivity implements PersonalProduct_ListAdapter.OnItemClickListener {

    CollectionReference mDBPath;
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

    RecyclerView mResultList;
    private List<getUploadedProductsData> getUploadedProductsDataList;
    private PersonalProduct_ListAdapter PersonalProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_product_page);
        getUploadedProductsDataList = new ArrayList<>();

        PersonalProductListAdapter = new PersonalProduct_ListAdapter(getUploadedProductsDataList);

        mResultList = findViewById(R.id.result_list);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("AllProduct");

        firebaseUserSearch();
        mResultList.setAdapter(PersonalProductListAdapter);
        PersonalProductListAdapter.setOnItemClickListener(PersonalUploaded.this);
        UIUtil.hideKeyboard(PersonalUploaded.this);


    }

    private void firebaseUserSearch() {

        mDBPath.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                mDBPath.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@android.support.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @android.support.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d("Error : ", e.getMessage());
                        }
                        getUploadedProductsDataList.clear();
                        PersonalProductListAdapter.notifyDataSetChanged();

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                getUploadedProductsData cloudSearchProduct = dc.getDocument().toObject(getUploadedProductsData.class);

                                getUploadedProductsDataList.add(cloudSearchProduct);

                            }
                        }
                    }
                });
            }
        });
    }


    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this, cloudUploadProducts.class);
        startActivity(touploadproduct);
        finish();
    }

    public void clickback(View view) {
        Intent clickback = new Intent(this, cloudProfile.class);
        startActivity(clickback);
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
        Log.d("CLick", "*******");
        Intent intent = new Intent(this, PersonalProduct_Page.class);
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
        String classify = getUploadedProductsDataList.get(position).getClassify();
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
        intent.putExtra("classify",classify);
        startActivity(intent);
    }
}