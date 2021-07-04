package com.example.parstagram_daniel;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;


@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_PROFILEPICTURE = "profilePic";

    public ParseFile getProfilePic (){
        return getParseFile(KEY_PROFILEPICTURE);
    }

    public void setProfilePic (ParseFile parseFile){
        put(KEY_PROFILEPICTURE, parseFile);
    }

}
