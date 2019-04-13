package com.example.samwu.myapp.Category.ClothesPackage.Clothes_Pants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samwu.myapp.Model.getUploadedProductsData;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.categoryClothes;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class Clothes_Pants_Uploaded extends AppCompatActivity implements Clothes_Pants_ListAdapter.OnItemClickListener {

    CollectionReference mDBPath;
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();

    private List<getUploadedProductsData> getUploadedProductsDataList;
    private Clothes_Pants_ListAdapter ClothesPantsAdapter;

    RecyclerView mResultList;
    String searchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes__pants_uploaded);

        getUploadedProductsDataList = new ArrayList<>();

        ClothesPantsAdapter = new Clothes_Pants_ListAdapter(getUploadedProductsDataList);

        mResultList = findViewById(R.id.result_list);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        firebaseUserSearch();
        mResultList.setAdapter(ClothesPantsAdapter);
        ClothesPantsAdapter.setOnItemClickListener(Clothes_Pants_Uploaded.this);
        UIUtil.hideKeyboard(Clothes_Pants_Uploaded.this);

        mDBPath = mDB.collection("Products");

    }

    private void firebaseUserSearch() {

        searchText = "Clothes/Pants";

        mDB.collection("Products").whereEqualTo("classify", searchText).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mDBPath.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@android.support.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @android.support.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            Log.d("Error : ", e.getMessage());

                        }

                        getUploadedProductsDataList.clear();
                        ClothesPantsAdapter.notifyDataSetChanged();

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {


                                if (dc.getDocument().getData().get("classify").toString().contains(searchText)) {
                                    getUploadedProductsData cloudSearchProduct = dc.getDocument().toObject(getUploadedProductsData.class);

                                    getUploadedProductsDataList.add(cloudSearchProduct);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, Clothes_Pants_Page.class);

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

    public void clickback(View view) {
        Intent clickback = new Intent(this, categoryClothes.class);
        startActivity(clickback);
        finish();
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


}
