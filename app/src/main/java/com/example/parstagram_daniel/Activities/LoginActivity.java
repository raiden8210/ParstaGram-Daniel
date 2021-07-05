package com.example.parstagram_daniel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.parstagram_daniel.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        EditText usernameEditText = binding.etUsername;
        EditText passwordEditText = binding.etPassword;
        Button loginButton = binding.btnLogin;
        Button signupButton = binding.btnSignup;
        ProgressBar loadingProgressBar = binding.loading;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginUser(username, password);
                Toast.makeText(LoginActivity.this, "Buttonclicked", Toast.LENGTH_LONG).show();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility((View.VISIBLE));
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                createUser(username, password);
            }
        });


    }

    private void createUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(e ->  {
            if(e == null) {
                goMainActivity();
                Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loginUser(String username, String password){
        Log.i("LoginActivity", "Attempting to login user");
        //Navigate to main activity if user has been able to login properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e("LoginActivity", "Something went wrong");
                    return;
                }
                goMainActivity();
            }
        });

    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}