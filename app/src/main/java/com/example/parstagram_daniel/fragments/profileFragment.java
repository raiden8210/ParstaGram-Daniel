package com.example.parstagram_daniel.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram_daniel.Activities.LoginActivity;
import com.example.parstagram_daniel.adapter.ProfileAdapter;
import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends postsFragment {

    RecyclerView rvProfile;
    Button btnLogout;
    ProfileAdapter adapter;
    List<Post> allPosts;
    ParseUser user;

    public profileFragment(Context context, ParseUser user) {
        // Required empty public constructor
        super(context);
        this.user = user;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfile = view.findViewById(R.id.rvProfile);

        allPosts = new ArrayList<>();

        adapter = new ProfileAdapter(getContext(), allPosts);

        btnLogout = view.findViewById(R.id.btnLogout);

        final GridLayoutManager layout = new GridLayoutManager(getContext(), 3);
        rvProfile.setAdapter(adapter);
        rvProfile.setLayoutManager(layout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground();
                goLoginActivity();
            }
        });

    queryPosts();
    }

    private void goLoginActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, user);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!= null){
                    Log.e("MainActivity", "Issue with getting posts", e);
                    return;
                }

                for(Post post: posts){
                    Log.i("MainActivity", "Post: " + post.getDescription() + ", username " + post.getUser().getUsername());
                }
                adapter.clear();
                allPosts.clear();

                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
