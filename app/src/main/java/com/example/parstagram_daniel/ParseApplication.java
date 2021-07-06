package com.example.parstagram_daniel;

import android.app.Application;

import com.example.parstagram_daniel.models.Comment;
import com.example.parstagram_daniel.models.Post;
import com.example.parstagram_daniel.models.User;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register parseModels
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Comment.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TZVilyUqNIyQr2ReAE4A9qPL9pWSoZG4WT1JHZN1")
                .clientKey("GYffwji8Lj328FQucxS7Jj7WI04WxNjpdtVQim2H")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
