package com.example.samwu.myapp.Chatroom;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.samwu.myapp.Chatroom.chatFragment.UsersFragment;
import com.example.samwu.myapp.Home.home;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.ShowProductPage.all.AllUploaded;

import java.util.ArrayList;

public class ShowChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chat);

        ViewPager viewPager = findViewById(R.id.chat_viewPager);

        ShowChatActivity.ChatViewPagerAdapter chatViewPagerAdapter = new ShowChatActivity.ChatViewPagerAdapter(getSupportFragmentManager());

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

    public void clickback(View view) {
        Intent clickback = new Intent(this, home.class);

        startActivity(clickback);
        finish();
    }


}