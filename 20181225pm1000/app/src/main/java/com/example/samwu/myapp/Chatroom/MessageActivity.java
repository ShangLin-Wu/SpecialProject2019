package com.example.samwu.myapp.Chatroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samwu.myapp.Chatroom.ChatModel.Chat;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.Profile.cloudProfile;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Chatroom.ChatModel.userlist;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_img;
    TextView username;
    Intent intent;

    FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore mDB = FirebaseFirestore.getInstance();

    ImageButton btn_send;
    EditText text_send;
    String email, otherSideProfileimg;

    MessageAdapter adapter;
    List<Chat> mchat;

    TextView chatemail;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        profile_img = findViewById(R.id.product_image);
        username = findViewById(R.id.producttitle_text);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        chatemail=findViewById(R.id.ChatEmail);


        recyclerView = findViewById(R.id.message_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


//***getReceiver
        intent = getIntent();

        //This email \ intent.getStringExtra("email") / is ReceiverEmail
        //Not CurrentUserEmail!!!!

        email = intent.getStringExtra("email");
        otherSideProfileimg = intent.getStringExtra("profileimg");
        final CollectionReference mDBPath = mDB.collection("ChatProfiles");

        chatemail.setText(email.substring(0,email.indexOf("@")));

       /*
        mDBPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (email.equals(documentSnapshot.getString("email"))) {

                        DocumentReference a = mDBPath.document(email);
                        a.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                otherSideProfileimg = documentSnapshot.getString("profileimg");
                                Log.d("SSSSSSSSSSS", otherSideProfileimg);
                                Picasso.with(MessageActivity.this).load(otherSideProfileimg).into(profile_img);

                                readMessage(mAuth.getEmail(), email, String.valueOf(otherSideProfileimg));
                                returnOtherSideProfileimg(otherSideProfileimg);

                            }
                        });
                    }

                }
            }
        });
        */


        final CollectionReference userchat = mDB.collection("UserProfiles").document(email).collection("ProfileInfo");

        userchat.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    if (email.equals(documentSnapshot.getString("email"))) {

                        DocumentReference a = mDBPath.document(email);
                        a.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                //otherSideProfileimg = documentSnapshot.getString("profileimg");
                                //Log.d("SSSSSSSSSSS", otherSideProfileimg);
                                Picasso.with(MessageActivity.this).load(otherSideProfileimg).into(profile_img);

                                readMessage(mAuth.getEmail(), email, String.valueOf(otherSideProfileimg));
                                returnOtherSideProfileimg(otherSideProfileimg);

                            }
                        });
                    }

                }
            }
        });


//---getReceiver


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(mAuth.getEmail(), email, msg);
                    mSaveUserAccount(email, msg);
                } else {

                    Toast.makeText(MessageActivity.this, "You can't send empty message.", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


    }

    private void returnOtherSideProfileimg(String otherSideProfileimg) {
        this.otherSideProfileimg = otherSideProfileimg;
    }


    private void sendMessage(final String sender, final String receiver, final String message) {

        Calendar calendar;
        SimpleDateFormat simpleDateFormat, simpleDateFormat1, simpleDateFormat2, simpleDateFormat3;
        final String Date, Date1, NowTime, ShowTime;

        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());

        simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd ");
        Date1 = simpleDateFormat1.format(calendar.getTime());

        simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        NowTime = simpleDateFormat2.format(calendar.getTime());

        simpleDateFormat3 = new SimpleDateFormat("HH:mm");
        ShowTime = simpleDateFormat3.format(calendar.getTime());

        //將以下資料儲存至資料庫當中
        final HashMap<String, Object> Map = new HashMap<>();
        Map.put("sender", sender);
        Map.put("receiver", receiver);
        Map.put("message", message);
        Map.put("Date", Date);
        Map.put("Month", Date1);
        Map.put("NowTime", NowTime);
        Map.put("ShowTime", ShowTime);


//***Save Message To Sender DataBase
        CollectionReference mDBSender = mDB.collection("ChatProfiles");
        mDBSender.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    //尋找mDBSender路徑中的帳號是否與A方(即當前帳號)相符
                    if (documentSnapshot.getString("email").equals(mAuth.getEmail())) {
                        String SenderId = mAuth.getEmail();
                        //將Map內的資料傳至 "ChatProfiles/A方(即當前使用者or訊息發送者)/Chat/B方(即訊息接收者)/X" 此路徑當中
                        CollectionReference documentReference = mDB.collection("ChatProfiles").document(SenderId)
                                .collection("Chat").document(receiver).collection("X");
                        documentReference.add(Map);
                        Log.d("ZZZZZZZZZZZZZZZ", documentReference.getPath());
                    }

                }
            }
        });
//---Save Message To Sender DataBase


//***Save Message To Receiver DataBase
        CollectionReference mDBReceiver = mDB.collection("ChatProfiles");
        mDBReceiver.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    //尋找mDBReceiver路徑中的帳號是否與B方(即訊息接收者帳號)相符
                    if (documentSnapshot.getString("email").equals(email)) {
                        String ReceiverId = email;
                        //將Map內的資料傳至 "ChatProfiles/B方(即訊息接收者)/Chat/A方(即當前使用者or訊息發送者)/X" 此路徑當中
                        CollectionReference documentReference = mDB.collection("ChatProfiles").document(ReceiverId)
                                .collection("Chat").document(sender).collection("X");
                        documentReference.add(Map);
                        Log.d("ZZZ1111ZZZZZZZZZZZZ", documentReference.getPath());
                    }
                }
            }
        });
//---Save Message To Receiver DataBase


    }

    private void mSaveUserAccount(final String receiver, final String message) {

        Calendar calendar;
        SimpleDateFormat simpleDateFormat2;
        final String NowTime;
        calendar = Calendar.getInstance();

        simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        NowTime = simpleDateFormat2.format(calendar.getTime());

        //在saveUser中存入B方之帳號、訊息、時間、大頭貼
        final HashMap<String, Object> saveUser = new HashMap<>();
        saveUser.put("email", receiver);
        saveUser.put("nowtime", NowTime);
        saveUser.put("message", message);
        saveUser.put("profileimg", otherSideProfileimg);
        final CollectionReference mSaveUsers = mDB.collection("ChatProfiles").document(mAuth.getEmail()).collection("SaveUsersAccount");
        final CollectionReference mSaveUsersReceiver = mDB.collection("ChatProfiles").document(receiver).collection("SaveUsersAccount");

        mSaveUsers.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                userlist a = null;
                if (!querySnapshot.isEmpty()) {
                    for (DocumentSnapshot dc : querySnapshot.getDocuments()) {
                        a = dc.toObject(userlist.class);
                        //將每筆資料中的ID(即email)加入到a(userlist)中
                        a.setMid(dc.getId());
                        // Log.d("qqqqq", a.getEmail() + "///////" + receiver);

                        if (a.getEmail().contains(receiver)) {
                            //若加到a(userlist)中的email與B方(訊息接收者)相符則更新資料
                            mSaveUsers.document(a.getMid()).update(saveUser);
                            // Log.d("GETTTTTTTBBBBBB", a.getMid() + "///////" + a.getEmail());
                            break;
                        }
                    }

                    if (!a.getEmail().equals(receiver)) {
                        //若加到a(userlist)中的email與B方(訊息接收者)不相符則新增資料
                        mSaveUsers.add(saveUser);

                        // Log.d("AAAAAA", mSaveUsers.getPath());
                    }
                } else {
                    //在 "ChatProfiles/A方/SaveUserAccount" 無資料則直接新增(add)資料至路徑當中
                    mSaveUsers.add(saveUser);
                    // Log.d("-", a.getEmail() + "///////" + receiver);
                }
            }
        });

        //在saveUserReceiver中存入A方之帳號、訊息、時間
        final HashMap<String, Object> saveUserReceiver = new HashMap<>();
        saveUserReceiver.put("email", mAuth.getEmail());
        saveUserReceiver.put("nowtime", NowTime);
        saveUserReceiver.put("message", message);

        mDB.collection("UserProfiles").document(mAuth.getEmail()).collection("ProfileInfo").document("X").addSnapshotListener(
                this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        String img = documentSnapshot.getString("profileimg");
                        saveUserReceiver.put("profileimg", img);
                    }

                });
        mSaveUsersReceiver.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                userlist a = null;
                if (!querySnapshot.isEmpty()) {
                    for (DocumentSnapshot dc : querySnapshot.getDocuments()) {
                        //將每筆資料中的ID(即email)加入到a(userlist)中
                        a = dc.toObject(userlist.class);
                        a.setMid(dc.getId());
                        // Log.d("qqqqq", a.getEmail() + "///////" + receiver);

                        //若加到a(userlist)中的email與A方(訊息發送者)相符則更新資料
                        if (a.getEmail().contains(mAuth.getEmail())) {
                            mSaveUsersReceiver.document(a.getMid()).update(saveUserReceiver);
                            // Log.d("GETTTTTTTBBBBBB", a.getMid() + "///////" + a.getEmail());
                            break;
                        }
                    }
                    //若加到a(userlist)中的email與A方(訊息發送者)不相符則新增資料
                    if (!a.getEmail().equals(mAuth.getEmail())) {
                        mSaveUsersReceiver.add(saveUserReceiver);
                        // Log.d("AAAAAA", mSaveUsers.getPath());
                    }
                } else {
                    //在 "ChatProfiles/B方/SaveUserAccount" 無資料則直接新增(add)資料至路徑當中
                    mSaveUsersReceiver.add(saveUserReceiver);
                    // Log.d("ZZZZZZZZZZZZZ", a.getEmail() + "///////" + receiver);
                }
            }
        });
    }


    private void readMessage(final String sender, final String receiver, final String profileimg) {
        mchat = new ArrayList<>();

        CollectionReference mDBReadMessage = mDB.collection("ChatProfiles").document(mAuth.getEmail())
                .collection("Chat").document(receiver).collection("X");

        mDBReadMessage.orderBy("NowTime").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    Chat chat = dc.getDocument().toObject(Chat.class);

                    //  Log.d("WWWWWEEEERRRR", chat.getSender() + "//" + sender + "**" + chat.getReceiver() + "//" + receiver);

                    if (chat.getSender().equals(sender) && chat.getReceiver().equals(receiver)
                            || chat.getSender().equals(receiver) && chat.getReceiver().equals(sender)
                            ) {
                        mchat.add(chat);
                    }

                    //  Log.d("SSSSSSSDDDDDSSSS", profileimg);
                    adapter = new MessageAdapter(MessageActivity.this, mchat, profileimg,receiver);
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }

    public void clickback(View view) {
        Intent clickback = new Intent(this, ShowChatActivity.class);

        startActivity(clickback);
        finish();
    }
}