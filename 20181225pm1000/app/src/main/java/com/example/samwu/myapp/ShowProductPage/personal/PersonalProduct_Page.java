package com.example.samwu.myapp.ShowProductPage.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samwu.myapp.Chatroom.MessageActivity;
import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.LoginUI.SharesPreferencesConfig;
import com.example.samwu.myapp.LoginUI.SignIn;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.ShowProductPage.all.AllProduct_detailpage;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.example.samwu.myapp.detailpage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalProduct_Page extends AppCompatActivity implements View.OnClickListener {

    StorageReference mStorage;
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    ImageButton delete;

    ImageButton detail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_product_page);

        final CircleImageView prod_image = findViewById(R.id.profile_image2);


        delete = findViewById(R.id.deletebtn);
        mStorage = FirebaseStorage.getInstance().getReference("Photos").child(mAuth.getEmail());

        Intent intent = getIntent();
        intent.getExtras();

        detail=findViewById(R.id.detailbtn);

        final Intent W=new Intent(this,PersonalProduct_detailpage.class);
        W.putExtras(intent.getExtras());
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(W);
                finish();
            }
        });


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

        DocumentReference mDBProfileimg;
        //get uploader profile image
        mDBProfileimg = mDB.collection("UserProfiles").document(email).collection("ProfileInfo").document("X");
        mDBProfileimg.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String ProfileImgPath = documentSnapshot.getString("profileimg").toString();
                Log.d("ProfileIQQQQQmgPath", ProfileImgPath);

                Picasso.with(PersonalProduct_Page.this).load(ProfileImgPath).fit().centerInside().into(prod_image);
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

        IntentToViewpager = new Intent(this, PersonalProduct_ViewPager.class);
        IntentToViewpager.putExtras(intent);


        final String title = intent.getStringExtra("producttitle");
        final String time = intent.getStringExtra("uploadtime");
        final String classify = intent.getStringExtra("classify");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(PersonalProduct_Page.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_deleteyesno, null);
                final Button Yes = mView.findViewById(R.id.yes);
                final Button No = mView.findViewById(R.id.no);

                mBuilder.setView(mView);
                mBuilder.setCancelable(true);
                final AlertDialog dialog = mBuilder.create();

                dialog.show();

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//---Products

                        final CollectionReference mAllProduct = mDB.collection("Products");

                        mAllProduct.whereEqualTo("producttitle", title).whereEqualTo("uploadtime", time).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                                for (DocumentSnapshot dc : task.getResult()) {
                                    if (dc.getString("producttitle").equals(title) && dc.getString("uploadtime").equals(time)) {
                                        String documentUID = dc.getId();

                                        Log.d("DeletedAllProduct", title + "-----" + time + "%%%%%" + documentUID);

                                        DocumentReference KillmAllProduct = mDB.collection("Products").document(documentUID);

                                        KillmAllProduct.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                    }
                                }

                            }
                        });


//***Products


//---PersonalAllProductProducts

                        final CollectionReference mPersonalProduct = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("AllProduct");

                        mPersonalProduct.whereEqualTo("producttitle", title).whereEqualTo("uploadtime", time).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for (DocumentSnapshot dc : task.getResult()) {
                                    if (dc.getString("producttitle").equals(title) && dc.getString("uploadtime").equals(time)) {
                                        String documentUID = dc.getId();

                                        Log.d("DeletedPersonalProduct", title + "-----" + time + "%%%%%" + documentUID);

                                        DocumentReference KillmPersonalAllProduct = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("AllProduct").document(documentUID);

                                        KillmPersonalAllProduct.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                    }
                                }
                            }
                        });

//***PersonalAllProductProducts


//---ClassifyProducts

                        final String newclassify = classify.substring(0, classify.indexOf("/"));
                        Log.d("newclassify", "" + newclassify);

                        final CollectionReference mClassifyProduct = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection(newclassify);

                        mClassifyProduct.whereEqualTo("producttitle", title).whereEqualTo("uploadtime", time).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                for (DocumentSnapshot dc : task.getResult()) {
                                    if (dc.getString("producttitle").equals(title) && dc.getString("uploadtime").equals(time)) {
                                        String documentUID = dc.getId();

                                        Log.d("DeletedClassify", title + "-----" + time + "%%%%%" + documentUID);

                                        DocumentReference KillmClassifyProduct = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection(newclassify).document(documentUID);

                                        KillmClassifyProduct.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                                    }
                                }
                            }
                        });

//***ClassifyProducts


//---Storage

                        final StorageReference NewStorage = mStorage.child(classify.substring(0, classify.indexOf("/"))).child(String.valueOf(classify.substring(classify.indexOf("/"))))
                                .child(title + "-----" + time);

                        final StorageReference A = NewStorage.child("A");
                        final StorageReference B = NewStorage.child("B");
                        final StorageReference C = NewStorage.child("C");
                        final StorageReference D = NewStorage.child("D");
                        final StorageReference E = NewStorage.child("E");
                        final StorageReference F = NewStorage.child("F");

                        A.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                        B.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                        C.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                        D.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                        E.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                        F.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });



//***Storage


                        startActivity(new Intent(PersonalProduct_Page.this, cloudProfile.class));
                        finish();
                        dialog.cancel();
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });
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

    public void detailpage(View view)
    {
        Intent detailpage = new Intent(this, PersonalProduct_Page.class);
        startActivity(detailpage);
        finish();
    }
    public void clickback(View view) {
        Intent clickback = new Intent(this, PersonalUploaded.class);

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