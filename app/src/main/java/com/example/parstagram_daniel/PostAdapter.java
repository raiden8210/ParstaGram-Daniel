package com.example.parstagram_daniel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;
    private List<User> users;

    public PostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        ImageView ibLike;
        ImageView ibComment;
        ImageView ibDirectMessage;
        TextView tvUser;
        TextView tvUserBottom;
        ImageView ivPost;
        TextView tvDescription;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            ibDirectMessage = itemView.findViewById(R.id.ibDirectMessage);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvUserBottom = itemView.findViewById(R.id.tvUserBottom);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post) {
            tvUser.setText(post.getUser().getUsername());
            tvUserBottom.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ibComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ibLike.setImageResource(R.drawable.ufi_heart);
            ibComment.setImageResource(R.drawable.ufi_comment);
            ibDirectMessage.setImageResource(R.drawable.direct);

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }

            ParseFile profileImage = post.getUser().getParseFile("profilePic");
            if (profileImage != null) {
                Glide.with(context).load( post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfileImage);

            }
        }
    }


}

