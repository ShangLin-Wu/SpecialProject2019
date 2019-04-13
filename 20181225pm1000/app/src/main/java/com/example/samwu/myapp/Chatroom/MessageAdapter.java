package com.example.samwu.myapp.Chatroom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Chatroom.ChatModel.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String profileimg;
    private String receiver;
    private FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();


    public MessageAdapter(Context context, List<Chat> mChat, String profileimg, String receiver) {
        this.mContext = context;
        this.mChat = mChat;
        this.profileimg = profileimg;
        this.receiver=receiver;
        Log.d("IIIIIIIIIIIIm",profileimg);
        Log.d("IIIIIIIIIIIIm",receiver);
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);

        } else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());
        holder.showtime.setText(chat.getShowTime());
        Picasso.with(mContext).load(profileimg).into(holder.profile_image);

    }


    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView show_message;
        public CircleImageView profile_image;
        public TextView showtime;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            showtime = mView.findViewById(R.id.selfshow_time);
            show_message = mView.findViewById(R.id.show_message);
            profile_image = mView.findViewById(R.id.product_image);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getSender().equals(mAuth.getEmail())) {
            return MSG_TYPE_RIGHT;
        } else return MSG_TYPE_LEFT;
    }
}
