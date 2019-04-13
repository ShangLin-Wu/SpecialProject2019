package com.example.samwu.myapp.LoginUI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Model.User;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.UploadPage.cloudUploadProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Nullable;

public class cloudfirestoreSignUp extends AppCompatActivity {

    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    SharesPreferencesConfig mPreferencesConfig;
    FirebaseAuth mAuth;

    EditText editTextemail, editTextPassword, edConfirmPass, StdID, StdClass, StdPhone;
    Button buttonadd, regloginbtn, signinback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudfirestore_sign_up);

        /*********************************FindViewById*******************************/
        editTextemail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_pass);
        edConfirmPass = findViewById(R.id.reg_confirm_pass);
        StdID = findViewById(R.id.StdID);
        StdClass = findViewById(R.id.StdClass);
        StdPhone = findViewById(R.id.StdPhone);
        buttonadd = findViewById(R.id.reg_btn);
        regloginbtn = findViewById(R.id.reg_login_btn);
        signinback = findViewById(R.id.signinback);
        /**-------------------------------FindViewById-----------------------------**/

        mAuth = FirebaseAuth.getInstance();

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData();  //Register Account
                clearEditText();//Registered After Initial EditText
            }
        });
    }


    private void addData() {
        final String Email = editTextemail.getText().toString();
        final String Password = editTextPassword.getText().toString();
        String Confirmpass = edConfirmPass.getText().toString();
        final String Stdid = StdID.getText().toString();
        final String Stdclass = StdClass.getText().toString();
        final String Stdphone = StdPhone.getText().toString();
        final String profileimg = "https://goo.gl/YXDKYk";

        Calendar calendar;
        SimpleDateFormat simpleDateFormat;
        final String Date;
        // Now Time
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date = simpleDateFormat.format(calendar.getTime());
        final HashMap<String,Object>map=new HashMap<>();
        map.put("createtime",Date);


        try {
            if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password) & !TextUtils.isEmpty(Confirmpass)) {
                if (TextUtils.equals(Password, Confirmpass)) {
                    /*　將帳號(密碼)長度（不包含@以後）設定在長度4以上15以下　*/
                    /* 手機號碼規定為十碼 */
                    if (Email.substring(0,Email.indexOf("@")).length() >= 4 && Email.substring(0,Email.indexOf("@")).length() <= 15 && Password.length() >= 4 && Password.length() <= 15
                            && StdPhone.length() == 10)
                    {
                        mAuth.fetchProvidersForEmail(editTextemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                try {
                                    boolean check = !task.getResult().getProviders().isEmpty();
                                    /* 帳號已被申請過 */
                                    if (check) {
                                        Toast.makeText(getApplicationContext(), "Email already exists.", Toast.LENGTH_SHORT).show();
                                    /* 帳號可用 */
                                    } else {
                                        Log.d("TAG", " written!");
                                        /* 將帳號密碼寫入FireBaseAuthentication中 */
                                        mAuth.createUserWithEmailAndPassword(Email, Password);

                                        /* 將個人資料寫入FireBaseDatabase中 */
                                        User users = new User(Email, Password, Stdid, Stdclass, Stdphone, profileimg);

                                        /* 設定Database資料擺放路徑 */
                                        final DocumentReference mEmailDocumentRef = mDB.collection("UserProfiles").document(Email)
                                                .collection("ProfileInfo").document("X");

                                        /* 建立將來使用聊天功能之路徑 */
                                        DocumentReference ChatProfiles = mDB.collection("ChatProfiles").document(Email);

                                        //建立聊天功能之路徑
                                        ChatProfiles.set(users);
                                        ChatProfiles.update(map);

                                        mEmailDocumentRef.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        });
                                        //建立個人資料之路徑
                                        mEmailDocumentRef.update(map);

                                        mEmailDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                Log.d("QWEEEE",mEmailDocumentRef.getPath());
                                                //確認剛才建立之路徑是否已正確被建立及存在
                                                if(task.getResult().exists())
                                                {
                                                    //將data寫入DB成功後則跳轉至登入畫面
                                                    Intent intent = new Intent(cloudfirestoreSignUp.this, SignIn.class);
                                                    startActivity(intent);
                                                    finish();

                                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                                    Toast.makeText(cloudfirestoreSignUp.this, "Your account was established successfully!", Toast.LENGTH_LONG).show();

                                                    Log.d("TAG", "DocumentSnapshot successfully written!");
                                                }
                                            }
                                        });




                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(cloudfirestoreSignUp.this, "Your email pattern may be wrong. ", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(cloudfirestoreSignUp.this, "尚有欄位未填寫完畢或有誤，請確認後再進行上傳。\n" +
                                "帳號字元限制為4 ~ 15之間!(不含信箱 P.S :務必輸入有效信箱)\n" +
                                "密碼字元限制為4 ~ 15之間!\n" +
                                "手機號碼固定為10碼!", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, "Password and ConfirmPassword doesn't match.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Your Email or Password/Confirm Password pattern may be wrong. ", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkemail() {
        mAuth.fetchProvidersForEmail(editTextemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                try {
                    boolean check = !task.getResult().getProviders().isEmpty();

                    if (check) {
                        Toast.makeText(getApplicationContext(), "Email already exists.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(cloudfirestoreSignUp.this, "Your account was established successfully!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(cloudfirestoreSignUp.this, "Your email pattern may be wrong. ", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    //Initial EditText
    public void clearEditText() {
        editTextemail.setText("");
        editTextPassword.setText("");
        edConfirmPass.setText("");
        StdID.setText("");
        StdClass.setText("");
        StdPhone.setText("");
    }

    public void tologin(View view) {
        Intent tologinpage = new Intent(this, SignIn.class);
        startActivity(tologinpage);
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
    public void clickback(View view) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudfirestoreSignUp.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_leave_page_yesno, null);
        final Button Yes = mView.findViewById(R.id.yes);
        final Button No = mView.findViewById(R.id.no);



        //若在同個page點選兩次selectprodcatebtn會crash
        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clickback = new Intent(cloudfirestoreSignUp.this, home.class);
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

}
