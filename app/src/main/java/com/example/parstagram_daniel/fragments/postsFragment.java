package com.example.parstagram_daniel.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram_daniel.EndlessRecyclerViewScrollListener;
import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.adapter.PostAdapter;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.models.User;
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
    protected List<User> allUsers;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeContainer;
    Context context;

    public postsFragment(Context context) {
        // Required empty public constructor
        this.context = context;
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
        rvPosts = view.findViewById(R.id.rvProfile);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        allPosts = new ArrayList<>();
        //allUsers = new ArrayList<>();
        adapter = new PostAdapter(getContext(), allPosts);

        rvPosts.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(manager);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimeLineAsync(0);
            }
        });

//        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                loadNextSet();
//            }
//        };

        queryPosts();
    }

//    private void loadNextSet() {
//
//    }

    private void fetchTimeLineAsync(int i) {
        adapter.clear();
        queryPosts();
        swipeContainer.setRefreshing(false);
    }

    public void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(adapter.getItemCount()-1);
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