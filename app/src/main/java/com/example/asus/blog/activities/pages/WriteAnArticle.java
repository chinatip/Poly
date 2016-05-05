package com.example.asus.blog.activities.pages;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.blog.R;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.util.Storage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class WriteAnArticle extends AppCompatActivity {
    private EditText header, text;
    private Button save;

    public WriteAnArticle() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    public void initComponents() {
        setContentView(R.layout.activity_write_an_article);
        createFormForArticle();
        createToolbar();
        save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Timeline.update();
                finish();
            }
        });
    }

    public void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createFormForArticle(){
        header = (EditText) findViewById(R.id.header);
        text = (EditText) findViewById(R.id.text);
    }

    private void save(){
        //fix writer id
        Article article = new Article(0000,header.getText().toString(),text.getText().toString());
        try {
            Storage.getInstance().saveWord(this, article);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
