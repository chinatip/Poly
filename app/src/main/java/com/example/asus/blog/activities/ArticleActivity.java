package com.example.asus.blog.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.adapters.ShowImageAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.UserStorage;

public class ArticleActivity extends AppCompatActivity {
    private TextView header, text, keyword, username;
    private Article article;
    private GridView gv;
    private ShowImageAdapter showImageAdapter;
    private ImageView userPic;
    private User userOwner,userViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra("article");
        userViewer = (User) intent.getSerializableExtra("user");
        userOwner = getUser(article.getUsername());
        gv = (GridView) findViewById(R.id.showImageGridView);
        showImageAdapter = new ShowImageAdapter(this, R.layout.grid_item_layout, article.getImages());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        initComponents();
    }

    public void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
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
        header = (TextView) findViewById(R.id.header);
        text = (TextView) findViewById(R.id.text);
        header.setText(article.getHeader());
        text.setText(article.getText());
        keyword = (TextView) findViewById(R.id.keywords);
        keyword.setText(article.getKeywords().toString());
        username = (TextView) findViewById(R.id.username);
        username.setText(article.getUsername());
        userPic = (ImageView) findViewById(R.id.userPic);
        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, UserActivity.class);
                intent.putExtra("userOwner", userOwner);
                intent.putExtra("userViewer",userViewer);
                startActivity(intent);
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, UserActivity.class);
                intent.putExtra("userOwner", userOwner);
                intent.putExtra("userViewer",userViewer);
                startActivity(intent);
            }
        });
    }

    public User getUser(String username) {
        return UserStorage.getInstance().getUser(this,article.getUsername());
    }
}
