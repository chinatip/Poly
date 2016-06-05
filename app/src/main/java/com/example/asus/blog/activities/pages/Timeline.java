package com.example.asus.blog.activities.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collection;
import java.util.Collections;

public class Timeline extends Fragment {
    private static ArrayList<Article> articles;
    public static ArticleAdapter articleAdapter;
    public ListView lv;
    public Button writeButton;
    private User user;
    private static Activity activity;
    private EditText searchText;

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
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("article", articles.get(i));
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        searchText = (EditText) v.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable arg0) {
                ArrayList<Article> src_list = new ArrayList<>();
                for (int i = 0; i < articles.size(); i++) {
                    try {
                        if(articles.get(i).search(searchText.getText().toString())){
                            src_list.add(articles.get(i));
                        }
                    } catch (Exception e) {
                    }
                }
                lv.setAdapter(new ArticleAdapter(activity.getApplicationContext(), R.layout.article_list, src_list));
            }

            public void beforeTextChanged(CharSequence s, int start
                    , int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start
                    , int before, int count) { }

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
        Collections.reverse(articles);
    }

    public static void update() {
        loadArticles(activity.getApplicationContext());
        articleAdapter.setListdata(articles);
        articleAdapter.notifyDataSetChanged();
    }


}
