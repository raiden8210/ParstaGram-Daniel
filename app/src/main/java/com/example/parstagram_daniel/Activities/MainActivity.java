package com.example.parstagram_daniel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.adapter.PostAdapter;
import com.example.parstagram_daniel.R;
import com.example.parstagram_daniel.fragments.composeFragment;
import com.example.parstagram_daniel.fragments.postsFragment;
import com.example.parstagram_daniel.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvPosts;
    List<Post> posts;
    PostAdapter adapter;
    Context context;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        //ImageButton ibInstagram = binding.ibInstagram;
        //Toolbar toolbar = binding.toolbar;

        bottomNavigationView = findViewById(R.id.bottom_navigation);

//        rvPosts = findViewById(R.id.rvPosts);
//        posts = new ArrayList<>();
//        adapter = new PostAdapter(this, posts);
//        rvPosts.setAdapter(adapter);
//        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new postsFragment(context);
                        break;
                    case R.id.action_compose:
                        fragment = new composeFragment();
                        break;
                    case R.id.action_profile:
                        ParseUser user = ParseUser.getCurrentUser();
                        fragment = new profileFragment(context, user);
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu, this adds items to the action bar if it is present
        //This method returns a boolean
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

}