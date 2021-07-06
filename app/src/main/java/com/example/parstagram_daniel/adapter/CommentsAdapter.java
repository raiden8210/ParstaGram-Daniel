package com.example.parstagram_daniel.adapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.models.Comment;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @NotNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView tvComment;
        TextView tvUser;
        TextView relativeTimeComment;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvUser = itemView.findViewById(R.id.tvUser);
            tvComment = itemView.findViewById(R.id.tvComment);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            relativeTimeComment = itemView.findViewById(R.id.tvRelativeTimeComment);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Comment comment) {

            tvUser.setText(comment.getUser().getUsername());
            tvComment.setText(comment.getDescription());
            relativeTimeComment.setText(comment.getRelativeTimeAgo(comment.getCreatedAt()));

            ParseFile profileImage = comment.getUser().getParseFile("profilePic");
            if (profileImage != null) {
                Glide.with(context).load(comment.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(profilePic);
            } else{
                Glide.with(context).load(R.drawable.placeholderprofilepic).circleCrop().into(profilePic);
            }
        }
    }
}
