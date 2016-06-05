package com.example.asus.blog.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.asus.blog.R;
import com.example.asus.blog.adapters.ArticleAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.ArticleStorage;

import org.json.JSONException;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private User user;
    private TextView username;
    private ToggleButton followToggle;
    private ArrayList<Article> articles;
    public ArticleAdapter articleAdapter;
    public ListView lv;
    public ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        articles = new ArrayList<>();
        loadArticles(this);
        initComponents();
    }

    public void initComponents(){
        username = (TextView)findViewById(R.id.username);
        username.setText(user.getUsername());
        back = (ImageView) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        followToggle = (ToggleButton) findViewById(R.id.toggleButton);
        followToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
                else {

                }
            }
        });
        lv = (ListView)findViewById(R.id.listView);
        articleAdapter = new ArticleAdapter(this, R.layout.article_list, articles);
        lv.setAdapter(articleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(UserActivity.this, ArticleActivity.class);
                intent.putExtra("article", articles.get(i));
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
    public void loadArticles(Context context) {
        try {
            articles.clear();
            for(Article a: ArticleStorage.getInstance().loadArticles(context)) {
                if(a.getUsername().equals(user.getUsername()))
                    articles.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
