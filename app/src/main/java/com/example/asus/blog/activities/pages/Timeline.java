package com.example.asus.blog.activities.pages;

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
import com.example.asus.blog.adapters.ArticleAdapter;
import com.example.asus.blog.models.Article;
import com.example.asus.blog.util.Storage;

import org.json.JSONException;

import java.util.ArrayList;

public class Timeline extends Fragment {
    private ArrayList<Article> articles;
    private static ArticleAdapter articleAdapter;
    public ListView lv;
    public Button writeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_timeline, container,false);
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
                Intent intent = new Intent(getActivity(), WriteAnArticle.class);
                startActivity(intent);
            }
        });
        lv = (ListView)v.findViewById(R.id.listView);
        articleAdapter = new ArticleAdapter(context, R.layout.article_list, articles);
        lv.setAdapter(articleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), null);
                startActivity(intent);
            }

//            public void onListItemClick(ListView l, View v, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), "Yea!!! click ho gae called", Toast.LENGTH_SHORT).show();
//            }
        });

    }

    public void loadArticles(Context context) {
        try {
            articles.clear();
            for(Article n: Storage.getInstance().loadArticles(context)) {
                articles.add(n);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void update() {
        articleAdapter.notifyDataSetChanged();
    }
}
