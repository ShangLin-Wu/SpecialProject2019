package com.example.samwu.myapp.UploadPage;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.samwu.myapp.Favorite.favorite;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Search.cloudSearch;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static com.example.samwu.myapp.R.id.btnPants;

public class cloudUploadProducts extends AppCompatActivity {

    /*******UploadProductImage*******/
    public ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    ImageButton imageButton5;
    ImageButton imageButton6;
    static final int GALLERY_INTENT = 1;
    ProgressDialog mProgressDialog;
    ImageButton x;
    String z;
    Button productimgbtn;
    Button selectprodcate;
    Button button;
    EditText ProductTitle;
    EditText Details;
    EditText personalPhone;
    char mchar = 65;


    /**
     * -----UploadProductImage-----
     **/

    StorageReference mStorage1, Storagepath;
    String A, B, C, D, E, F;
    DocumentReference mProdPath, mUserPath, mDBPath, mUserAllProductPath;
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    FirebaseFirestore mUser = FirebaseFirestore.getInstance();

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    String phone;
    String Classify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_upload_products);

        // Used For Getting The Correct User Email
        final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mStorage1 = FirebaseStorage.getInstance().getReference("Photos").child(mAuth.getEmail());
        Storagepath = FirebaseStorage.getInstance().getReference();

        // Now Time
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date = simpleDateFormat.format(calendar.getTime());

        imageButton1 = findViewById(R.id.uploadimg1);
        imageButton2 = findViewById(R.id.uploadimg2);
        imageButton3 = findViewById(R.id.uploadimg3);
        imageButton4 = findViewById(R.id.uploadimg4);
        imageButton5 = findViewById(R.id.uploadimg5);
        imageButton6 = findViewById(R.id.uploadimg6);
        productimgbtn = findViewById(R.id.SelectProductImageBtn);
        selectprodcate = findViewById(R.id.SelectProductCategoryBtn);
        mProgressDialog = new ProgressDialog(this);
        button = findViewById(R.id.button2);
        ProductTitle = findViewById(R.id.ProductTitle);
        Details = findViewById(R.id.detail);
        personalPhone = findViewById(R.id.productTitle2);


//***getUserPhone
        mUserPath = mUser.collection("UserProfiles").document(mAuth.getEmail()).collection("ProfileInfo").document("X");
        mUserPath.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String phone = documentSnapshot.getData().get("phone").toString();
                returnPhone(phone);
            }
        });
//---getUserPhone


        //------------------------------------------------------------------------------------.collection("分類").document("商品名");
        // DocumentReference mDBPath=mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Products").document("X");

        selectprodcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefinePathButton();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_uploadyesno, null);
                final Button Yes = mView.findViewById(R.id.yes);
                final Button No = mView.findViewById(R.id.no);

                //若在同個page點選兩次selectprodcatebtn會crash
                mBuilder.setView(mView);
                mBuilder.setCancelable(true);
                final AlertDialog dialog = mBuilder.create();

                final String productTitle = ProductTitle.getText().toString();
                final String details = Details.getText().toString();
                final String personalPhone1 = personalPhone.getText().toString();

                dialog.show();

                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d(String.valueOf(mchar), "&&&&&&&&&&&&&&&&&&&&");
                        if (productTitle.length() > 3 && productTitle.length() <= 28 && details.length() > 3 && details.length() <= 390 && personalPhone.length() == 10 && mchar != 'A') {

                            mProdPath = mDB.collection("Products").document(ProductTitle.getText() + "-----" + Date);
                            mUserAllProductPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("AllProduct").document();
                            Map<String, Object> setdata = new HashMap<>();

                            setdata.put("a", A);
                            setdata.put("b", B);
                            setdata.put("c", C);
                            setdata.put("d", D);
                            setdata.put("e", E);
                            setdata.put("f", F);
                            setdata.put("classify", Classify);
                            setdata.put("producttitle", productTitle);
                            setdata.put("details", details);
                            setdata.put("email", mAuth.getEmail());
                            setdata.put("uploadtime", Date);
                            setdata.put("phone", personalPhone1);
                            setdata.put("userProfilesPath", String.valueOf(mDBPath.getPath()));
                            setdata.put("productsPath", String.valueOf(mProdPath.getPath()));

                            mDBPath.set(setdata);//將商品建立到個人資料庫之商品類別路徑中
                            Log.d("UserProfiles", String.valueOf(mDBPath.getPath()));
                            mProdPath.set(setdata);//將商品建立到所有商品資料庫路徑中
                            Log.d("Products", String.valueOf(mProdPath.getPath()));
                            mUserAllProductPath.set(setdata);//將商品建立到個人資料庫之路徑中(無區分商品類別)
                            Log.d("UserAllProductPath", String.valueOf(mUserAllProductPath.getPath()));

                            dialog.cancel();

                            Intent T = new Intent(cloudUploadProducts.this, home.class);
                            startActivity(T);
                            finish();

                            Toast.makeText(cloudUploadProducts.this, "商品已上傳完畢!", Toast.LENGTH_LONG).show();
                        } else if (personalPhone.length() != 10) {
                            Toast.makeText(cloudUploadProducts.this, "請輸入正確手機號碼。", Toast.LENGTH_LONG).show();
                        } else if (category == false) {
                            Toast.makeText(cloudUploadProducts.this, "請先選擇商品類別。", Toast.LENGTH_LONG).show();
                        } else if (mchar == 65) {
                            Toast.makeText(cloudUploadProducts.this, "請選擇照片後再進行上傳。", Toast.LENGTH_LONG).show();
                        } else if (productTitle.length() < 4 || productTitle.length() > 28 || details.length() < 4 || details.length() > 390 || phone.length() != 10) {
                            Toast.makeText(cloudUploadProducts.this, "尚有欄位未填寫完畢或有誤，請確認後再進行上傳。\n" +
                                    "商品名稱字元限制為4 ~ 28之間!\n" +
                                    "商品內容字元限制為4 ~ 390之間!\n" +
                                    "手機號碼固定為10碼!", Toast.LENGTH_LONG).show();
                        }
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

        productimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category) {
                    UploadProductImage(mchar);
                } else {
                    Toast.makeText(cloudUploadProducts.this, "請先選擇商品類別。", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void returnPhone(String phone) {
        this.phone = phone;
    }

    private void UploadProductImage(char i) {
        if (i == 65) {
            A = z;
            x = imageButton1;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        } else if (i == 66) {
            B = z;
            x = imageButton2;

            //  x.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);

        } else if (i == 67) {
            C = z;
            x = imageButton3;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        } else if (i == 68) {
            D = z;
            x = imageButton4;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        } else if (i == 69) {
            E = z;
            x = imageButton5;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        } else if (i == 70) {
            F = z;
            x = imageButton6;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(String.valueOf(mDBPath), "  ''UploadImageDBPath''  ");

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            final Uri uri = data.getData();
            /* 裁減照片 */
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        //Path:Photos/UserEmail/ProfilePicture/ProfilePicture.jpg
        //StorageReference FilePath=mStorage.child("Photos").child(mAuth.getEmail()).child(String.valueOf(nownode)).child(String.valueOf(mchar));

        String P = String.valueOf(ProductTitle.getText());
        final StorageReference FilePath = Storagepath.child(P + "-----" + Date).child(String.valueOf(mchar));
        Log.d("FFFFFF", String.valueOf(FilePath));

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            /* 取得照片裁減過後之結果 */
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                /* 取得結果之Uri(用以儲存至Firebase Storage當中) */
                final Uri resultUri = result.getUri();
                mProgressDialog.setTitle("Uploading ...");
                mProgressDialog.show();
                // Put File To Firebase Storage then Create SuccessListener from upload process to the end of the upload
                FilePath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                x.setImageURI(resultUri);
                                x.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                FilePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        final Uri downloaduripath = task.getResult();
                                        z = downloaduripath.toString();
                                      /*
                                       switch (mchar)
                                       {
                                           case 65:A(z);break;
                                           case 66:B(z);break;
                                           case 67:C(z);break;
                                           case 68:D(z);break;
                                           case 69:E(z);break;
                                           case 70:F(z);break;
                                       }
                                       */

                                        if (mchar == 65) A(z);
                                        else if (mchar == 66) B(z);
                                        else if (mchar == 67) C(z);
                                        else if (mchar == 68) D(z);
                                        else if (mchar == 69) E(z);
                                        else if (mchar == 70) F(z);
                                        mchar++;
                                        if (mchar == 71) mchar = 65;

                                        mProgressDialog.dismiss();
                                        Toast.makeText(cloudUploadProducts.this, "Upload Done", Toast.LENGTH_LONG).show();

                                    }
                                });
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
    }

    private void F(String z) {
        F = z;
    }

    private void E(String z) {
        E = z;
    }

    private void D(String z) {
        D = z;
    }

    private void C(String z) {
        C = z;
    }

    private void B(String z) {
        B = z;
    }

    private void A(String z) {
        A = z;
    }

    boolean category = false;

    public void DefinePathButton() {

        try {

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
            final View mView = getLayoutInflater().inflate(R.layout.dialog_enter, null);
            final Button Clothes = mView.findViewById(R.id.Clothes);
            final Button ElectronicsProducts = mView.findViewById(R.id.ElectronicsProducts);
            final Button Books = mView.findViewById(R.id.Books);
            final Button Furnitures = mView.findViewById(R.id.Furnitures);


            //若在同個page點選兩次selectprodcatebtn會crash
            mBuilder.setView(mView);
            mBuilder.setCancelable(true);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            Clothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
                    View mView = getLayoutInflater().inflate(R.layout.category_clothes, null);
                    final Button button8 = mView.findViewById(btnPants);
                    final Button button9 = mView.findViewById(R.id.btnShirts);
                    final Button button10 = mView.findViewById(R.id.btnShoes);

                    final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    mBuilder.setView(mView);
                    final AlertDialog dialogcloth = mBuilder.create();
                    dialogcloth.show();
                    button8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //建立Firebase Storage 路徑
                            Storagepath = mStorage1.child("Clothes").child("Pants");

                            //建立Firebase Database 路徑
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Clothes").document();

                            //將類別路徑存入Database中
                            String classify = "Clothes/Pants";

                            //確認是否已正確選取類別
                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogcloth.cancel();
                            dialog.cancel();

                        }
                    });

                    button9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Clothes").child("Shirts");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Clothes").document();

                            String classify = "Clothes/Shirts";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogcloth.cancel();
                            dialog.cancel();

                        }
                    });
                    button10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Clothes").child("Shoes");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Clothes").document();

                            String classify = "Clothes/Shoes";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogcloth.cancel();
                            dialog.cancel();
                        }
                    });
                }
            });
            ElectronicsProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
                    View mView = getLayoutInflater().inflate(R.layout.category_3c, null);
                    final Button button5 = (Button) mView.findViewById(R.id.btn3C);
                    final Button button6 = (Button) mView.findViewById(R.id.btnCellphone);
                    final Button button7 = (Button) mView.findViewById(R.id.btnComputer);

                    final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    mBuilder.setView(mView);
                    final AlertDialog dialogelec = mBuilder.create();
                    dialogelec.show();

                    button5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("ElectronicsProducts").child("3C").child(Date);
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ElectronicsProducts").document();
                            String classify = "ElectronicsProducts/3C";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogelec.cancel();
                            dialog.cancel();
                        }
                    });
                    button6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("ElectronicsProducts").child("Cellphone");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ElectronicsProducts").document();

                            String classify = "ElectronicsProducts/Cellphone";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogelec.cancel();
                            dialog.cancel();
                        }
                    });
                    button7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("ElectronicsProducts").child("Computer");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ElectronicsProducts").document();

                            String classify = "ElectronicsProducts/Computer";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogelec.cancel();
                            dialog.cancel();
                        }
                    });
                }
            });

            Books.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
                    View mView = getLayoutInflater().inflate(R.layout.category_books, null);
                    final Button button13 = mView.findViewById(R.id.btnNEW);
                    final Button button14 = mView.findViewById(R.id.btnUSED);

                    final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    mBuilder.setView(mView);
                    final AlertDialog dialogbook = mBuilder.create();
                    dialogbook.show();

                    button13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Books").child("New");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Books").document();

                            String classify = "Books/New";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogbook.cancel();
                            dialog.cancel();
                        }
                    });

                    button14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Books").child("Used");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Books").document();

                            String classify = "Books/Used";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogbook.cancel();
                            dialog.cancel();
                        }
                    });
                }
            });

            Furnitures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
                    View mView = getLayoutInflater().inflate(R.layout.category_furnitures, null);
                    final Button button11 = mView.findViewById(R.id.btnNew);
                    final Button button12 = mView.findViewById(R.id.btnUsed);

                    final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

                    mBuilder.setView(mView);
                    final AlertDialog dialogfurniture = mBuilder.create();
                    dialogfurniture.show();

                    button11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Furniture").child("New");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Furniture").document();

                            String classify = "Furniture/New";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogfurniture.cancel();
                            dialog.cancel();
                        }
                    });

                    button12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Storagepath = mStorage1.child("Furniture").child("Used");
                            mDBPath = mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("Furniture").document();

                            String classify = "Furniture/Used";

                            category = true;

                            returnstoragepath(Storagepath, category);
                            returndbpath(mDBPath, classify);

                            dialogfurniture.cancel();
                            dialog.cancel();

                        }
                    });
                }
            });


        } catch (Exception e) {
        }
    }

    private void returndbpath(DocumentReference mDBPath, String classify) {
        this.mDBPath = mDBPath;
        this.Classify = classify;
    }

    private void returnstoragepath(StorageReference storagepath, boolean category) {
        Storagepath = storagepath;
        this.category = category;
    }


    public void clickback(View view) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(cloudUploadProducts.this);
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
                Intent clickback = new Intent(cloudUploadProducts.this, home.class);
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
