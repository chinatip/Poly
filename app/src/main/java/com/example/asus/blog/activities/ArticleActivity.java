package com.example.asus.blog.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.models.Article;

public class ArticleActivity extends AppCompatActivity {
    private TextView header, text;
    private Article article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra("article");
        createToolbar();
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
        header = (TextView) findViewById(R.id.header);
        text = (TextView) findViewById(R.id.text);
        header.setText(article.getHeader());
        text.setText(article.getText());
    }
}
