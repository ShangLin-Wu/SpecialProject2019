package com.example.samwu.myapp.Profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.LoginUI.cloudfirestoreSignUp;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class cloudUpdateProfile extends AppCompatActivity {

    private FirebaseFirestore mDB = FirebaseFirestore.getInstance();

    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    private EditText StdPhone;
    private EditText StdClass;
    private EditText reg_pass;
    private EditText reg_confirm_pass;
    private Button updatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_update_profile);

        reg_pass = findViewById(R.id.reg_pass);
        reg_confirm_pass = findViewById(R.id.reg_confirm_pass);
        StdClass = findViewById(R.id.StdClass);
        StdPhone = findViewById(R.id.StdPhone);
        updatebtn = findViewById(R.id.reg_btn);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void update() {
        String password = reg_pass.getText().toString();
        String confirmpass = reg_confirm_pass.getText().toString();
        String stdclass = StdClass.getText().toString();
        String stdphone = StdPhone.getText().toString();
        try {
            if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpass) & !TextUtils.isEmpty(stdclass) & !TextUtils.isEmpty(stdphone)) {
                if (TextUtils.equals(password, confirmpass)) {
                    getPosition();
                } else
                    Toast.makeText(this, "Password and ConfirmPassword doesn't match.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, " wrong ", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPosition() {
        final String password = reg_pass.getText().toString();
        final String stdclass = StdClass.getText().toString();
        final String stdphone = StdPhone.getText().toString();
        final Intent intent = new Intent(this, cloudProfile.class);

        final DocumentReference mEmailDocumentRef = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ProfileInfo").document("X");
        final DocumentReference ChatProfiles = mDB.collection("ChatProfiles").document(mAuth.getEmail());
        if ( password.length() >= 4 && password.length() <= 15 && StdPhone.length() == 10)
        {
        mEmailDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().getString("email").equals(mAuth.getEmail())) {
                    final Map<String, Object> NewInfo = new HashMap<>();
                    NewInfo.put("password", password);
                    NewInfo.put("stdclass", stdclass);
                    NewInfo.put("phone", stdphone);
                    mAuth.updatePassword(password);
                    //將新的個人資料更新至個人資料路徑
                    mEmailDocumentRef.update(NewInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //將新的個人資料更新至聊天路徑
                            ChatProfiles.update(NewInfo);
                            Toast.makeText(cloudUpdateProfile.this, "Profile info updated to CloudFireStore Successfully ...", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String message = e.getMessage();
                            Toast.makeText(cloudUpdateProfile.this, "Error Occurred :" + message, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        }
        else
        {
            Toast.makeText(cloudUpdateProfile.this, "尚有欄位未填寫完畢或有誤，請確認後再進行上傳。\n" +
                    "密碼字元限制為4 ~ 15之間!\n" +
                    "手機號碼固定為10碼!", Toast.LENGTH_LONG).show();
        }
    }

    public void clickback(View view) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUpdateProfile.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_leave_page_yesno, null);
        final Button Yes = mView.findViewById(R.id.yes);
        final Button No = mView.findViewById(R.id.no);


        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clickback = new Intent(cloudUpdateProfile.this, home.class);
                startActivity(clickback);
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
    public void touploadproduct(View view) {
        Intent touploadproduct = new Intent(this , cloudUploadProducts.class);
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