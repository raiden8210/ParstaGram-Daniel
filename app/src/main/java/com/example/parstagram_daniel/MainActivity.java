package com.example.parstagram_daniel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parstagram_daniel.databinding.ActivityMainBinding;
import com.example.parstagram_daniel.fragments.composeFragment;
import com.example.parstagram_daniel.fragments.postsFragment;
import com.example.parstagram_daniel.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvPosts;
    List<Post> posts;
    PostAdapter adapter;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Toast.makeText(getBaseContext(), "Home button pressed", Toast.LENGTH_SHORT).show();
                        fragment = new postsFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new composeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new profileFragment();
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