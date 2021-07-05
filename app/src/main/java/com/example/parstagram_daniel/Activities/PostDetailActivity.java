package com.example.parstagram_daniel.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parstagram_daniel.EndlessRecyclerViewScrollListener;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.adapter.PostAdapter;
import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    Post post;
    ImageView ivProfileImage;
    ImageView ibLike;
    ImageView ibComment;
    ImageView ibDirectMessage;
    TextView tvUser;
    TextView tvUserBottom;
    ImageView ivPost;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        ivProfileImage = findViewById(R.id.ivProfileImage);
        ibLike = findViewById(R.id.ibLike);
        ibComment = findViewById(R.id.ibComment);
        ibDirectMessage = findViewById(R.id.ibDirectMessage);
        tvUser = findViewById(R.id.tvUser);
        tvUserBottom = findViewById(R.id.tvUserBottom);
        ivPost = findViewById(R.id.ivPost);
        tvDescription = findViewById(R.id.tvDescription);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvUser.setText(post.getUser().getUsername());
        tvUserBottom.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibLike.setImageResource(R.drawable.ufi_heart_active);
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
            Glide.with(this).load(post.getImage().getUrl()).into(ivPost);
        }

        ParseFile profileImage = post.getUser().getParseFile("profilePic");
        if (profileImage != null) {
            Glide.with(this).load(post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfileImage);


            //queryPosts();

        }

//    private void queryPosts() {
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        query.include(Post.KEY_USER);
//        query.setLimit(adapter.getItemCount()-1);
//        query.addDescendingOrder("createdAt");
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> posts, ParseException e) {
//                if(e!= null){
//                    Log.e("MainActivity", "Issue with getting posts", e);
//                    return;
//                }
//
//                for(Post post: posts){
//                    Log.i("MainActivity", "Post: " + post.getDescription() + ", username " + post.getUser().getUsername());
//                }
//                post.addAll(posts);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }
    }
}