package com.example.samwu.myapp.Category.ElectronicPackage.Electronics_Cellphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samwu.myapp.Category.ElectronicPackage.Electronics_3C.Electronics_3C_detailpage;
import com.example.samwu.myapp.Chatroom.MessageActivity;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.Category.ElectronicPackage.Electronics_3C.Electronics_3C_Uploaded;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class Electronics_Cellphone_Page extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

    //**for favoritebtn
    ImageButton favorite_btn;
    String status;
    boolean favorite_switch;

    //**for showbtn
    String chatemail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product_page);

        final CircleImageView prod_image = findViewById(R.id.profile_image2);

        Intent intent = getIntent();
        intent.getExtras();

        favorite_btn = findViewById(R.id.favorite_btn);

        ImageButton imageView1 = findViewById(R.id.xProductPageImageButton1);
        ImageButton imageView2 = findViewById(R.id.xProductPageImageButton2);
        ImageButton imageView3 = findViewById(R.id.xProductPageImageButton3);
        ImageButton imageView4 = findViewById(R.id.xProductPageImageButton4);
        ImageButton imageView5 = findViewById(R.id.xProductPageImageButton5);
        ImageButton imageView6 = findViewById(R.id.xProductPageImageButton6);
        TextView mproducttitle = findViewById(R.id.xProductPageProductTitle);
        TextView memail = findViewById(R.id.xProductPageEmail);
        TextView mphone = findViewById(R.id.xProductPagePhone);
        TextView mdetails = findViewById(R.id.xProductPageDetails);
        TextView muploadtime = findViewById(R.id.xUploadTime);

        Picasso.with(this).load(intent.getStringExtra("a")).fit().centerInside().into(imageView1);
        Picasso.with(this).load(intent.getStringExtra("b")).fit().centerInside().into(imageView2);
        Picasso.with(this).load(intent.getStringExtra("c")).fit().centerInside().into(imageView3);
        Picasso.with(this).load(intent.getStringExtra("d")).fit().centerInside().into(imageView4);
        Picasso.with(this).load(intent.getStringExtra("e")).fit().centerInside().into(imageView5);
        Picasso.with(this).load(intent.getStringExtra("f")).fit().centerInside().into(imageView6);

        final String email = intent.getStringExtra("email");

        returnChatemail(email);

        DocumentReference mDBProfileimg;

        //get uploader profile image
        mDBProfileimg = mDB.collection("UserProfiles").document(email).collection("ProfileInfo").document("X");
        mDBProfileimg.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String ProfileImgPath = documentSnapshot.getString("profileimg").toString();
                Picasso.with(Electronics_Cellphone_Page.this).load(ProfileImgPath).fit().centerInside().into(prod_image);
            }


        });
        //get uploader profile image

        mproducttitle.setText(intent.getStringExtra("producttitle"));
        memail.setText(intent.getStringExtra("email"));
        mdetails.setText(intent.getStringExtra("details"));
        mphone.setText((mphone.getText() + intent.getStringExtra("phone")));
        muploadtime.setText(intent.getStringExtra("uploadtime"));

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);

        IntentToViewpager = new Intent(this, Electronics_Cellphone_ViewPager.class);
        IntentToViewpager.putExtras(intent);

//***favorite btn


        final String producttitle = intent.getStringExtra("producttitle");

        final DocumentReference favoriteDocumentRef = mDB.collection("UserProfiles").document(mAuth.getEmail())
                .collection("Favorite").document(producttitle);


        Map<String, Object> NewInfo = new HashMap<>();
        NewInfo.put("producttitle", producttitle);  //name
        NewInfo.put("status", status);  //favorite status
        NewInfo.put("email", email);  //favorite email

        //   Log.d("ssssss",status);

        favoriteDocumentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                favoriteDocumentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        favoriteDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    favoriteDocumentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                            if (documentSnapshot.exists()) {
                                                Log.d("TTTTTTTTTTTTT", "TTTTTTTTTTTT");

                                                status = documentSnapshot.getString("status").toString();

                                                Log.d("status ", status.toString());

                                                judgment(status);
                                            } else {
                                                status = "false";

                                                Map<String, Object> NewInfo = new HashMap<>();
                                                NewInfo.put("producttitle", producttitle);  //name
                                                NewInfo.put("status", status);  //favorite status
                                                NewInfo.put("email", email);  //favorite email

                                                favoriteDocumentRef.set(NewInfo);


                                                judgment(status);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
//---favorite btn

    }


    Intent IntentToViewpager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xProductPageImageButton1:
                startActivity(IntentToViewpager);
                break;
            case R.id.xProductPageImageButton2:
                startActivity(IntentToViewpager);
                break;
            case R.id.xProductPageImageButton3:
                startActivity(IntentToViewpager);
                break;
            case R.id.xProductPageImageButton4:
                startActivity(IntentToViewpager);
                break;
            case R.id.xProductPageImageButton5:
                startActivity(IntentToViewpager);
                break;
            case R.id.xProductPageImageButton6:
                startActivity(IntentToViewpager);
                break;
        }
    }


    //***favoritebtn
    public void favorite_btn(View view) {


        if (favorite_switch == false) {


            favorite_btn.setImageResource(R.drawable.favorite_on);
            addfavorite();
            favorite_switch = true;


        } else if (favorite_switch == true) {


            favorite_btn.setImageResource(R.drawable.favorite_off);
            removefavorite();
            favorite_switch = false;


        }
    }

    private void judgment(String a) {

        Log.d("a  ", a);
        String judgment = a;

        Log.d("judgment  ", judgment);


        if (judgment.equals("true")) {
            favorite_btn.setImageResource(R.drawable.favorite_on);
            favorite_switch = true;
            Log.d("favorite1", String.valueOf(favorite_switch));

        } else {
            favorite_btn.setImageResource(R.drawable.favorite_off);
            favorite_switch = false;
            Log.d("favorite2", String.valueOf(favorite_switch));
        }

    }

    private void addfavorite() {

        Intent intent = getIntent();

        final String producttitle = intent.getStringExtra("producttitle");

        final String status = "true";

        final String email = intent.getStringExtra("email");


        final DocumentReference favoriteDocumentRef = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Favorite").document(producttitle);

        favoriteDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> NewInfo = new HashMap<>();
                NewInfo.put("producttitle", producttitle);  //name
                NewInfo.put("status", status);  //favorite status
                NewInfo.put("email", email);  //favorite email

                favoriteDocumentRef.update(NewInfo);

                Toast.makeText(Electronics_Cellphone_Page.this, "已加入我的最愛", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void removefavorite() {

        Intent intent = getIntent();

        final String producttitle = intent.getStringExtra("producttitle");

        final String ststus = "false";

        final String email = intent.getStringExtra("email");


        final DocumentReference favoriteDocumentRef = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Favorite").document(producttitle);

        favoriteDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Map<String, Object> NewInfo = new HashMap<>();
                NewInfo.put("producttitle", producttitle);  //name
                NewInfo.put("status", ststus);  //favorite status
                NewInfo.put("email", email);  //favorite email

                favoriteDocumentRef.update(NewInfo);

                Toast.makeText(Electronics_Cellphone_Page.this, "已移除", Toast.LENGTH_LONG).show();

            }
        });
    }
//---favoritebtn


    //***ShowBtn
    private void returnChatemail(String email) {
        this.chatemail = email;
    }

    public void showbtn(View v) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();

        CollectionReference mDBPath = mDB.collection("UserProfiles").document(chatemail).collection("ProfileInfo");
        mDBPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                String s = "", t = "";
                for (QueryDocumentSnapshot document : task.getResult()) {

                    s = document.getString("email");
                    t = document.getString("profileimg");


                    if (!mAuth.getEmail().equals(s)) {
                        Log.d("SSSSMMMMM", document.getReference().getId() + " => " + document.getData());

                        Intent intent = new Intent(Electronics_Cellphone_Page.this, MessageActivity.class);
                        intent.putExtra("email", s);
                        intent.putExtra("profileimg", t);
                        startActivity(intent);
                    }
                }
                Log.d("EEEEE", "Error getting documents: " + task.getException());

            }
        });

    }
//---ShowBtn



    public void detailpage(View view)
    {
        Intent detailpage = new Intent(this, Electronics_Cellphone_detailpage.class);

        startActivity(detailpage);
        finish();
    }



    public void clickback(View view) {
        Intent clickback = new Intent(this, Electronics_Cellphone_Uploaded.class);
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