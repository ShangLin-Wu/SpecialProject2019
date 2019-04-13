package com.example.samwu.myapp.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.LoginUI.SharesPreferencesConfig;
import com.example.samwu.myapp.LoginUI.SignIn;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.example.samwu.myapp.ShowProductPage.personal.PersonalUploaded;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class cloudProfile extends AppCompatActivity {


    private FirebaseFirestore mDB = FirebaseFirestore.getInstance();

    //UploadImage
    private CircleImageView circleImageView;
    private Button mSelectImage, noncrop;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog mProgressDialog;

    //ProfileInfo
    private SharesPreferencesConfig preferencesConfig;
    private EditText editEmail;
    private EditText StdPhone;
    private EditText StdID;
    private EditText StdClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_profile);


        LoginOrNot();
        getProfileInfo();

        //UploadImage
        circleImageView = findViewById(R.id.circle_profile);
        mSelectImage = findViewById(R.id.SelectImage);

        //       noncrop = findViewById(R.id.selectImage);

        mStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

//***NonCrop
        /*
        noncrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 71);
            }
        });
        */
//---NonCrop


        editEmail = findViewById(R.id.email);
        StdID = findViewById(R.id.StdID);
        StdClass = findViewById(R.id.StdClass);
        StdPhone = findViewById(R.id.StdPhone);
    }

    //UploadImage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

    //***CropImg
            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
            {
                final Uri uri = data.getData();
                CropImage.activity(uri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
            // Used For Getting The Correct User Email
            FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

            // Path:Photos/UserEmail/ProfilePicture/0/ProfilePicture.jpg
            final StorageReference FilePath = mStorage.child("Photos").child(mAuth.getEmail()).child("0").child("ProfilePicture");

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    mProgressDialog.setTitle("Uploading ...");
                    mProgressDialog.show();
                    final Uri resultUri = result.getUri();

                    // Put File To Firebase then Create SuccessListener from upload process to the end of the upload
                    FilePath.putFile(resultUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    circleImageView.setImageURI(resultUri);
                                    FilePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri a = task.getResult();
                                            String uridownload = a.toString();
                                            Log.d("MWWWWWWWWWWWWWWWMMMMMM", String.valueOf(uridownload));

                                            FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                                            DocumentReference mEmailDocumentRef = mDB.collection("UserProfiles").document(mAuth.getEmail())
                                                    .collection("ProfileInfo").document("X");

                                            final DocumentReference ChatProfileImg = mDB.collection("ChatProfiles").document(mAuth.getEmail());

                                            final Map<String, Object> profileimgPath = new HashMap<>();

                                            profileimgPath.put("profileimg", uridownload.toString());

                                            mEmailDocumentRef.update(profileimgPath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    ChatProfileImg.update(profileimgPath);

                                                    Toast.makeText(cloudProfile.this, "Profile image stored to Firebase Database Successfully ...", Toast.LENGTH_LONG).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    String message = e.getMessage();
                                                    Toast.makeText(cloudProfile.this, "Error Occured :" + message, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });

                                    mProgressDialog.dismiss();
                                    Toast.makeText(cloudProfile.this, "Upload Done", Toast.LENGTH_LONG).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressDialog.setMessage("Upload Done" + (int) progress + "%");

                        }
                    });
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    error.toString();
                }
            }
        } catch (Exception e) {
            Log.d("CropImageError", e.getMessage());
        }
    }

    private void getProfileInfo() {

        //建立mAuth以getCurrentUser()來取得當前使用者之帳號
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

        //依照取得之使用者帳號進而到Firebase Database 取得使用者個人資料
        mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ProfileInfo").document("X").addSnapshotListener(
                this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                        String a = documentSnapshot.getString("email");
                        String b = documentSnapshot.getString("stdclass");
                        String c = documentSnapshot.getString("stdid");
                        String d = documentSnapshot.getString("phone");
                        String img = documentSnapshot.getString("profileimg");

                        //SetProfileImage
                        Picasso.with(cloudProfile.this).load(img)
                                .into(circleImageView);
                        //SetProfileImage

                        //SetProfileInfo
                        editEmail.setText(a);
                        StdClass.setText(b);
                        StdID.setText(c);
                        StdPhone.setText(d);
                        //SetProfileInfo
                    }

                });
    }

    private void LoginOrNot() {
        preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
        if (preferencesConfig.readLoginStatus() == false) {
            Toast.makeText(this, "You've to login first. ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SignIn.class));
            finish();
        }
    }

    public void userLogOut(View view) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudProfile.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_logoutyesno, null);
        final Button Yes = mView.findViewById(R.id.yes);
        final Button No = mView.findViewById(R.id.no);

        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        final AlertDialog dialog = mBuilder.create();

        dialog.show();

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferencesConfig = new SharesPreferencesConfig(getApplicationContext());
                preferencesConfig.writeLoginStatus(false);
                startActivity(new Intent(cloudProfile.this, SignIn.class));
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


    public void uploadedproductpage(View view) {
        Intent uploadedproductpage = new Intent(this, PersonalUploaded.class);
        startActivity(uploadedproductpage);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    public void updateprofile(View view) {
        Intent updateprofile = new Intent(this, cloudUpdateProfile.class);
        startActivity(updateprofile);
        //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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

}


//***NonCrop
            /*
            if (requestCode == 71 && resultCode == RESULT_OK && data != null && data.getData() != null) {

                final Uri uri = data.getData();

                try {
                    // Used For Getting The Correct User Email
                    FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    // Path:Photos/UserEmail/ProfilePicture/0/ProfilePicture.jpg
                    final StorageReference FilePath = mStorage.child("Photos").child(mAuth.getEmail()).child("0").child("ProfilePicture");

                    FilePath.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                            {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    circleImageView.setImageURI(uri);
                                    FilePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            Uri a=task.getResult();
                                            String uridownload=a.toString();
                                            Log.d("MWWWWWWWWWWWWWWWMMMMMM", String.valueOf(uridownload));

                                            FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                                            DocumentReference mEmailDocumentRef=mDB.collection("UserProfiles").document(mAuth.getEmail())
                                                    .collection("ProfileInfo").document("X");

                                            final DocumentReference ChatProfileImg = mDB.collection("ChatProfiles").document(mAuth.getEmail());

                                            final Map<String,Object> profileimgPath=new HashMap<>();

                                            profileimgPath.put("profileimg",uridownload.toString());


                                            mEmailDocumentRef.update(profileimgPath).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    ChatProfileImg.update(profileimgPath);

                                                    Toast.makeText(cloudProfile.this, "Profile image stored to Firebase Database Successfully ...", Toast.LENGTH_LONG).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    String message =e.getMessage();
                                                    Toast.makeText(cloudProfile.this, "Error Occured :" + message, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    });

                                    mProgressDialog.dismiss();
                                    Toast.makeText(cloudProfile.this,"Upload Done",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                    mProgressDialog.setMessage("Upload Done"+(int)progress+"%");

                                }
                            });

                } catch (Exception e) {
                    e.getMessage();
                }


            }
            */
//---NonCrop

