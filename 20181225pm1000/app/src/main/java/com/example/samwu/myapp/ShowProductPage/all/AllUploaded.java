package com.example.samwu.myapp.ShowProductPage.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.samwu.myapp.Category.BooksPackage.BooksNew.NewBooks_Page;
import com.example.samwu.myapp.Favorite.ProductPagebackfavorite;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Model.getUploadedProductsData;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class AllUploaded extends AppCompatActivity implements AllProductListAdapter.OnItemClickListener {

    CollectionReference mDBPath;
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    private List<getUploadedProductsData> ProductList;

    RecyclerView mResultList;
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private List<getAllProduct> getAllProducts;
    private List<getUploadedProductsData> getUploadedProductsDataList;
    private AllProductListAdapter AllProductListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_of_uploaded_product_page);

        ProductList = new ArrayList<>();
        mDBPath = mDB.collection("Products");
        final CollectionReference mAllProductPath = mDBPath;

        getAllProducts = new ArrayList<>();

        getUploadedProductsDataList = new ArrayList<>();

        AllProductListAdapter= new AllProductListAdapter(getAllProducts);

        mResultList = findViewById(R.id.result_list);

        getAllProducts.clear();
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference AllProductCollection = mDB.collection("Products");

        AllProductCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange document : querySnapshot.getDocumentChanges()) {

                    Log.d("WYYYYYYY", String.valueOf(document.getDocument()));


                        Log.d("Error : ", document.getDocument().getString("producttitle")+"////");
                        final String producttitle = document.getDocument().getString("producttitle");
                        final String email = document.getDocument().getString("email");

                        Log.d("EMAAAAA", String.valueOf(email));

                        CollectionReference getProductsDetail = mDB.collection("Products");
                        getProductsDetail.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                                    if (dc.getDocument().getString("email").equals(email) && dc.getDocument().getString("producttitle").equals(producttitle)) {
                                        //  String productsPhoto = task.getResult().getString("a");
                                        //  String productsDetails = task.getResult().getString("details");
                                        getAllProduct saveData = dc.getDocument().toObject(getAllProduct.class);

                                        getUploadedProductsData getAllofData = dc.getDocument().toObject(getUploadedProductsData.class);

                                        Log.d("DATATTATATA", String.valueOf(saveData.getEmail()));

                                        AllProductListAdapter.notifyDataSetChanged();
                                        getAllProducts.add(saveData);
                                        getUploadedProductsDataList.add(getAllofData);
                                        mResultList.setAdapter(AllProductListAdapter);


                                    }
                                }
                            }
                        });


                }

            }
        });
        AllProductListAdapter.setOnItemClickListener(AllUploaded.this);


    }




    public void clickback(View view) {
        Intent clickback = new Intent(this, home.class);
        startActivity(clickback);
        finish();
    }

    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this, cloudUploadProducts.class);
        startActivity(touploadproduct);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    /********ImageButtonAndButtonEvent*********/
    public void tohome(View view) {
        Intent tohome = new Intent(this, home.class);
        startActivity(tohome);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    public void tofavorite(View view) {
        Intent tofavorite = new Intent(this, favorite.class);
        startActivity(tofavorite);
    }

    public void tosearch(View view) {
        Intent tosearch = new Intent(this, cloudSearch.class);
        startActivity(tosearch);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }

    public void tomembercentre(View view) {
        Intent tologinpage = new Intent(this, cloudProfile.class);
        startActivity(tologinpage);
        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onItemClick(int position) {

        Log.d("CLoick", "*******");
        Intent intent = new Intent(this, AllProductPage.class);

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
        intent.putExtra("uploadtime",uploadtime);

        startActivity(intent);
    }


}