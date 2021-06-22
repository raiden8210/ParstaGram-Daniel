package com.example.parstagram_daniel.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram_daniel.Post;
import com.example.parstagram_daniel.PostAdapter;
import com.example.parstagram_daniel.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class postsFragment extends Fragment {

    private RecyclerView rvPosts;
    protected PostAdapter adapter;
    protected List<Post> allPosts;

    public postsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostAdapter(getContext(), allPosts);

        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();
    }

    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
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
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}