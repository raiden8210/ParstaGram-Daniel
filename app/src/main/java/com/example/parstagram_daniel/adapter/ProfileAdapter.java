package com.example.parstagram_daniel.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram_daniel.Activities.PostDetailActivity;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.fragments.postsFragment;
import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.models.User;
import com.parse.ParseFile;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private List<User> users;
    CardView cvRoot;

    public ProfileAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profilepost, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileAdapter.ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPost;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivPost = itemView.findViewById(R.id.ivPost);
            cvRoot= itemView.findViewById(R.id.cvRoot);
        }

        public void bind(Post post) {

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
            }

            cvRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Post post = posts.get(position);
                        Intent i = new Intent(context, PostDetailActivity.class);
                        i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                        //Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
                        //Log.i("DetailActivity", "rkrk");
                        context.startActivity(i);
                    }
                }
            });



        }
    }

}
