package com.example.samwu.myapp.Search;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.ShowProductPage.search.SearchAllProductPage;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;
import java.util.ArrayList;
import java.util.List;

public class cloudSearch extends AppCompatActivity implements ProductListAdapter.OnItemClickListener {

    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    CollectionReference mDBPath;

    EditText mSearchField;
    ImageButton mSearchBtn;
    RecyclerView mResultList;
    String searchText;
    private List<cloudSearchProduct> cloudSearchProductList;
    private ProductListAdapter productListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_search);

        mSearchField = findViewById(R.id.search_field);
        mSearchBtn = findViewById(R.id.searchbtn);

        cloudSearchProductList = new ArrayList<>();

        productListAdapter = new ProductListAdapter(cloudSearchProductList);

        mResultList = findViewById(R.id.result_list);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
                mResultList.setAdapter(productListAdapter);
                productListAdapter.setOnItemClickListener(cloudSearch.this);
                UIUtil.hideKeyboard(cloudSearch.this);

            }
        });
        mDBPath = mDB.collection("Products");

    }

    private void firebaseUserSearch(final String searchText) {

        Toast.makeText(cloudSearch.this, "Started Search", Toast.LENGTH_LONG).show();

        /* mDBPath為所有上傳商品之路徑，將searchText(關鍵字)送到資料庫做查詢*/
        Query firebaseSearchQuery = mDBPath.whereEqualTo("producttitle", searchText);

        firebaseSearchQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mDBPath.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.d("Error : ", e.getMessage());
                                }

                        cloudSearchProductList.clear();
                        productListAdapter.notifyDataSetChanged();

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {

                            /* 若dc之資料與已加入之資料相互匹配到之情況下　*/
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                // String test = null;
                                // test = dc.getDocument().getString("producttitle");
                                if (dc.getDocument().getData().get("producttitle").toString().contains(searchText)) {
                                    /* 若searchText(關鍵字)有出現在資料庫當中，則將此商品加入List當中 */
                                    cloudSearchProduct cloudSearchProduct = dc.getDocument().toObject(cloudSearchProduct.class);

                                    cloudSearchProductList.add(cloudSearchProduct);
                                }
                                //Log.d("TEST", String.valueOf(dc.getDocument().contains(searchText)));
                                Log.d("cloudSearch*Searched(?)", String.valueOf(dc.getDocument().getData().get("producttitle").toString().contains(searchText)));
                                //Log.d("TEST", cloudSearchProductList.toString());
                                //productListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });
    }

    //***Click result change to Product Page.
    @Override
    public void onItemClick(int position) {


        Intent intent = new Intent(this, SearchAllProductPage.class);


        String a = cloudSearchProductList.get(position).getA();
        String b = cloudSearchProductList.get(position).getB();
        String c = cloudSearchProductList.get(position).getC();
        String d = cloudSearchProductList.get(position).getD();
        String e = cloudSearchProductList.get(position).getE();
        String f = cloudSearchProductList.get(position).getF();
        String producttitle = cloudSearchProductList.get(position).getProducttitle();
        String email = cloudSearchProductList.get(position).getEmail();
        String details = cloudSearchProductList.get(position).getDetails();
        String phone = cloudSearchProductList.get(position).getPhone();
        String uploadtime =cloudSearchProductList.get(position).getUploadtime();

        String[] i = new String[]{a, b, c, d, e, f, producttitle, email, details, phone};
        String nopic = "https://ppt.cc/fchLDx";
        for (int j = 0; j <=5; j++) {Log.d("SSSSSSGGGGGGGGGGS",j+"--------"+i[j]);
            if (i[j]==null) {

                Log.d("SSSSSSS",j+"---------------------"+i[j]);
                i[j] = nopic;

            }

        }
        if(i[8]==null)i[8]="沒有內容...";
        if(i[9]==null)i[9]="這位同學很懶沒有留電話...";

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
//---Click result change to Product Page.

    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
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

}