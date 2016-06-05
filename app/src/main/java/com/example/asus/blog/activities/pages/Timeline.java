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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.ArticleActivity;
import com.example.asus.blog.activities.MainActivity;
import com.example.asus.blog.adapters.ArticleAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.example.asus.blog.util.ArticleStorage;

import org.json.JSONException;

import java.util.ArrayList;

public class Timeline extends Fragment {
    private static ArrayList<Article> articles;
    public static ArticleAdapter articleAdapter;
    public ListView lv;
    public Button writeButton;
    private User user;
    private static Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_timeline, container, false);
        user = MainActivity.getUser();
        activity = getActivity();
        initComponents(v);
        return v;
    }


    public void initComponents(View v) {
        Context context = getActivity().getApplicationContext();
        articles = new ArrayList<>();
        loadArticles(context);

        writeButton = (Button) v.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user == null){
                    Toast.makeText(getActivity(),"Please login to write an article",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), WriteAnArticle.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
        lv = (ListView)v.findViewById(R.id.listView);
        articleAdapter = new ArticleAdapter(context, R.layout.article_list, articles);
        lv.setAdapter(articleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class );
                intent.putExtra("article", articles.get(i));
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }

    public static void loadArticles(Context context) {
        try {
            articles.clear();
            for(Article n: ArticleStorage.getInstance().loadArticles(context)) {
                articles.add(n);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void update() {
        loadArticles(activity.getApplicationContext());
        articleAdapter.setListdata(articles);
        articleAdapter.notifyDataSetChanged();
    }


}
