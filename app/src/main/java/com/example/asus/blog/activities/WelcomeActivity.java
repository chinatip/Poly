package com.example.asus.blog.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.blog.R;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.UserStorage;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private Button logIn, skip;
    private EditText username, password;
    private ImageView addUserButton;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initComponents();
    }

    public void initComponents() {
        logIn = (Button) findViewById(R.id.logInButton);
        skip = (Button) findViewById(R.id.skipButton);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        addUserButton = (ImageView) findViewById(R.id.addUserButton);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUser()) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("user", getUser());
                    startActivity(intent);
                } else {
                    username.setText("");
                    password.setText("");
                    Toast.makeText(WelcomeActivity.this, "Username or Password is incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("user", (Serializable)null);
                startActivity(intent);
            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkUser() {
        try {
            return UserStorage.getInstance().userLogIn(this,username.getText().toString(),password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUser() {
        return UserStorage.getInstance().getUser(this, username.getText().toString());
    }
}
