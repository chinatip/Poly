package com.example.asus.blog.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.ArticleStorage;
import com.example.asus.blog.util.UserStorage;

import org.json.JSONException;

public class AddUserActivity extends AppCompatActivity {
    private EditText username, password, firstname, lastname;
    private Button register, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initComponents();
    }

    public void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timeline.update();
                finish();
            }
        });
    }

    public void initComponents() {
        createToolbar();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        register = (Button) findViewById(R.id.registerButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void save(){
        User user = new User(UserStorage.getCurrentID(),username.getText().toString(),password.getText().toString(),
        firstname.getText().toString(),lastname.getText().toString());
        try {
            UserStorage.getInstance().saveUser(this, user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
