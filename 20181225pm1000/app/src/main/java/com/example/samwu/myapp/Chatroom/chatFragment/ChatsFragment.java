package com.example.samwu.myapp.Chatroom.chatFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samwu.myapp.Chatroom.ChatModel.getUserInfoForChat;
import com.example.samwu.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView;
    private chatlistadapter adapter;
    private List<getUserInfoForChat> mUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        recyclerView = view.findViewById(R.id.recycler_userview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();

        return view;
    }

    private void readUsers() {

        final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        CollectionReference mDBPath;

        mDBPath = mDB.collection("ChatProfiles");
        mDBPath.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                for (QueryDocumentSnapshot document : task.getResult()) {

                    getUserInfoForChat lll = document.toObject(getUserInfoForChat.class);

                    if (!mAuth.getEmail().equals(lll.getEmail())) {
                        Log.d("WWWEEE", document.getReference().getId() + " => " + document.getData());
                        mUsers.add(lll);
                    }

                }

                Log.d("EEEEE", "Error getting documents: " + task.getException());

                adapter = new chatlistadapter(getContext(), mUsers, true);
                recyclerView.setAdapter(adapter);


            }
        });

    }


}