package com.example.samwu.myapp.Chatroom;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Chatroom.chatFragment.ChatsFragment;
import com.example.samwu.myapp.Chatroom.chatFragment.UsersFragment;

import java.util.ArrayList;

public class chatpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);

        ViewPager viewPager = findViewById(R.id.chat_viewPager);


        ChatViewPagerAdapter chatViewPagerAdapter = new ChatViewPagerAdapter(getSupportFragmentManager());

        chatViewPagerAdapter.addFrament(new ChatsFragment(),"Chats");
        chatViewPagerAdapter.addFrament(new UsersFragment(),"Users");


        viewPager.setAdapter(chatViewPagerAdapter);

    }

    class ChatViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        ChatViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        public void addFrament(Fragment fragment, String title) {

            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


}
