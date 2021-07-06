package com.example.parstagram_daniel.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.adapter.CommentsAdapter;
import com.example.parstagram_daniel.models.Comment;
import com.example.parstagram_daniel.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    TextView tvComment;
    ImageView ivProfileImage;
    TextView relativeTimeComment;
    TextView user;
    List<Comment> comments;
    Post post;
    CommentsAdapter adapter;
    RecyclerView rvComments;
    EditText etComposeComment;
    Button submitComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        ivProfileImage = findViewById(R.id.ivProfileImage);
        etComposeComment = findViewById(R.id.etComposeComment);
        submitComment = findViewById(R.id.sendComment);

        comments = new ArrayList<>();
        rvComments = findViewById(R.id.rvComments);
        adapter = new CommentsAdapter(getApplicationContext(), comments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        ParseUser user = ParseUser.getCurrentUser();

        ParseFile profileImage = user.getParseFile("profilePic");
        if (profileImage != null) {
            Glide.with(this).load(post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfileImage);
        } else{
            Glide.with(this).load(R.drawable.placeholderprofilepic).circleCrop().into(ivProfileImage);
        }

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etComposeComment.getText().toString();

                Comment comment = new Comment();
                comment.setDescription(message);
                comment.setUser(ParseUser.getCurrentUser());
                comment.setPostId(post.getObjectId());
                comment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            return;
                        }
                        etComposeComment.setText("");
                        queryComments();

                    }
                });
            }
        });

        queryComments();
    }

    private void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.whereEqualTo("postedId", post.getObjectId());
        query.include(Comment.KEY_USER);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> results, ParseException e) {
                if (e != null) {
                    //Handle error with retrieving posts
                    Log.e("CommentsActivity", "Issues with pulling: ", e);
                    return;
                }
                for (Comment comment : results) {
                    Log.i("CommentsActivity", comment.getUser().getUsername() + " says: " + comment.getDescription());
                }
                adapter.clear();
                comments.addAll(results);
                adapter.notifyDataSetChanged();
            }
        });
    }


}
