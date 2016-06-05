package com.example.asus.blog.activities.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.ArticleActivity;
import com.example.asus.blog.activities.MainActivity;
import com.example.asus.blog.adapters.ArticleAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.ArticleStorage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class History extends Fragment {

    private static ArrayList<Article> articles;
    public static ArticleAdapter articleAdapter;
    public ListView lv;
    private static User user;
    private static Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_history, container,false);
        user = MainActivity.getUser();
        activity = getActivity();
        initComponents(v);
        return v;
    }

    public void initComponents(View v){
        Context context = getActivity().getApplicationContext();
        articles = new ArrayList<>();
        if(user!=null) loadArticles(context);

        lv = (ListView)v.findViewById(R.id.listView);
        articleAdapter = new ArticleAdapter(context, R.layout.article_list, articles);
        lv.setAdapter(articleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("article", articles.get(i));
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    public static void loadArticles(Context context) {
        try {
            articles.clear();
            for(Article a: ArticleStorage.getInstance().loadArticles(context)) {
                if(a.getUsername().equals(user.getUsername()))
                    articles.add(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.reverse(articles);
    }

    public static void update() {
        loadArticles(activity.getApplicationContext());
        articleAdapter.setListdata(articles);
        articleAdapter.notifyDataSetChanged();
    }
}
