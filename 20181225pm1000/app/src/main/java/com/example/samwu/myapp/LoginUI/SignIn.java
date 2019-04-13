package com.example.samwu.myapp.LoginUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Home.home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SharesPreferencesConfig preferencesConfig;

    private EditText edtemail, edtPassword;
    private Button signinbtn,signupbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtemail =  findViewById(R.id.edt_email);
        edtPassword =  findViewById(R.id.edt_password);
        signinbtn =  findViewById(R.id.signinbtn);
        signupbtn= findViewById(R.id.signupbtn);
        preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
        loginornot();
        /********************************LoginButtonEvent********************************/
        signinbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String loginEmail = edtemail.getText().toString();
                String loginPass = edtPassword.getText().toString();
                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass))
                {
                    /* mAuth 做為firebase中帳號密碼之確認正確與否之用途 */
                    mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                /* 取得當前之使用者 */
                                FirebaseUser currentUser = mAuth.getCurrentUser();

                                /* 若為非空則正確取得使用者，若為空則取得失敗 */
                                if (currentUser != null) {

                                    /* 登入成功則跳轉至主畫面 */
                                    Intent signin = new Intent(SignIn.this, home.class);

                                    Toast.makeText(SignIn.this, "Login Successfully ! ", Toast.LENGTH_LONG).show();

                                    startActivity(signin);

                                    /* 寫入登入狀態True:已登入 False:未登入*/
                                    preferencesConfig.writeLoginStatus(true);
                                    finish();
                                }
                            } else {
                                String ErrorMessage = task.getException().getMessage();
                                Toast.makeText(SignIn.this, "Error : " + ErrorMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        /**----------------------------LoginButtonEvent----------------------------**/

        /******************************RegisterButtonEvent******************************/
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* 按下申請帳號後，跳轉至填寫個人資料畫面 */
                Intent signup = new Intent(SignIn.this, cloudfirestoreSignUp.class);
                startActivity(signup);
            }
        });
        /**----------------------------RegisterButtonEvent----------------------------****/
        }

        private void loginornot()
        {
            /*********************************ReadLoginStatusTrueOrFalse*******************************/
            preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
            if(preferencesConfig.readLoginStatus()==true)
            {
                Toast.makeText(SignIn.this, "You've already logged in!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignIn.this,home.class ));
                finish();
            }
            /**----------------------------ReadLoginStatusTrueOrFalse----------------------------**/
        }
}
