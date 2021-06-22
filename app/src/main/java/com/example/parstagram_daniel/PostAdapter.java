package com.example.parstagram_daniel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;

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

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        ImageButton ibLike;
        ImageButton ibComment;
        ImageButton ibDirectMessage;
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

            ibLike.setImageResource(R.drawable.ufi_heart);
            ibComment.setImageResource(R.drawable.ufi_comment);
            ibDirectMessage.setImageResource(R.drawable.direct);

            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }

        }
    }


}

