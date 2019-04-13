package com.example.samwu.myapp.Chatroom.chatFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.samwu.myapp.Chatroom.ChatModel.Chatlist;
import com.example.samwu.myapp.Chatroom.ChatModel.getUserInfoForChat;
import com.example.samwu.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private chatlistadapter chatlistadapter;
    private List<getUserInfoForChat> mUsers;
    private List<Chatlist> mUsersList;

    private FirebaseFirestore mDB = FirebaseFirestore.getInstance();
    private final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_userview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsersList = new ArrayList<>();


        final CollectionReference mDBPath;
        mDBPath = mDB.collection("ChatProfiles");


        mDBPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                mUsersList.clear();

                for (DocumentSnapshot dc : task.getResult().getDocuments()) {
                    Log.d("IDDDDDD", dc.getId());

                    mDBPath.document(mAuth.getEmail()).collection("Chat").document(dc.getId()).collection("X").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

                            for (DocumentSnapshot dd : querySnapshot.getDocuments()) {
                                Chatlist chatlist = dd.toObject(Chatlist.class);
                                Log.d("KKKKKKKMMMM", chatlist.getSender() + "***");
                                mUsersList.add(chatlist);
                            }
                            chatList();
                        }
                    });
                }
            }
        });

        return view;
    }

    private void chatList() {

        mUsers = new ArrayList<>();
        final CollectionReference mPath;
        mPath = mDB.collection("ChatProfiles").document(mAuth.getEmail()).collection("SaveUsersAccount");
        mPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mUsers.clear();
                for (DocumentSnapshot ds : task.getResult()) {
                    if (!ds.getData().containsValue(ds.getString("sender"))) {
                        getUserInfoForChat chat = ds.toObject(getUserInfoForChat.class);
                        mUsers.add(chat);
                        /*
                        for (Chatlist chatlist : mUsersList) {

                            Log.d("LKLKLKLKLKLKLK", chat.getEmail() + "*****" + chatlist.getSender());

                            //chat is all of user
                            //chatlist is chathistory

                            if (chat.getEmail().equals(chatlist.getSender()) || (chat.getEmail().equals(chatlist.getReceiver()))) {

                                if (!chat.getEmail().equals(mAuth.getEmail())) {

                                    if (!mUsers.contains(chat.getEmail()) && (!mUsers.contains(chatlist.getSender())) && (!mUsers.contains(chatlist.getReceiver()))) {

                                    }
                                }
                            }
                        }
                        */
                    }

                }
                chatlistadapter = new chatlistadapter(getContext(), mUsers, true);
                recyclerView.setAdapter(chatlistadapter);
            }
        });
    }
}
