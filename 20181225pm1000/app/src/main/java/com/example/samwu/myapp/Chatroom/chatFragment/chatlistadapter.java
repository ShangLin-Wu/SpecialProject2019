package com.example.samwu.myapp.Chatroom.chatFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.samwu.myapp.Chatroom.ChatModel.getUserInfoForChat;
import com.example.samwu.myapp.R;
import com.example.samwu.myapp.Chatroom.MessageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class chatlistadapter extends RecyclerView.Adapter<chatlistadapter.ViewHolder> {

    private Context mContext;
    private List<getUserInfoForChat> mUsers;

    public chatlistadapter(Context context, List<getUserInfoForChat> mUsers, boolean b) {
        this.mContext = context;
        this.mUsers = mUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_searchresult, parent, false);


        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
          view.startAnimation(animation);

        return new chatlistadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //取得使用者之路徑
        final getUserInfoForChat user = mUsers.get(position);
        //從該路徑中取得帳號及最新一則訊息
        holder.producttitle_text.setText(user.getEmail());
        holder.details_text.setText(user.getMessage());
        //   Log.d("ProductListAdapterUria", user.getStdclass()+"");
        //   Log.d("ProductListAdapterUrib", user.getPhone()+"");
        //   Log.d("ProductListAdapterUric", user.getProfileimg()+"");
        //   Uri uri = Uri.parse(mUsers.get(position).getA());

        //從使用者該路徑中取得大頭貼路徑，並將其顯示
        Glide.with(mContext).load(user.getProfileimg()).into(holder.prod_image);

        //點選該使用者之監聽器
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                //將使用者之帳號及大頭貼利用intent傳入MessageActivity.class當中
                intent.putExtra("email", user.getEmail());
                intent.putExtra("profileimg", user.getProfileimg());
                mContext.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView producttitle_text;
        public TextView details_text;
        public CircleImageView prod_image;
        public ImageButton showbtn;
        // public ImageView prod_image ;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            showbtn = mView.findViewById(R.id.messagebtn);

            producttitle_text = mView.findViewById(R.id.producttitle_text);
            details_text = mView.findViewById(R.id.status_text);
            prod_image = mView.findViewById(R.id.product_image);
        }
    }


}
