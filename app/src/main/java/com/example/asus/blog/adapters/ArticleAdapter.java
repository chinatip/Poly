package com.example.asus.blog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.asus.blog.R;
import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.models.Article;

import java.util.ArrayList;

/**
 * Created by Chinatip Vichian
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, int resource, ArrayList<Article> objects){
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.article_list, null);
        }
        TextView word = (TextView) v.findViewById(R.id.header);
        word.setText(getItem(position).getHeader());
        if(1+1==9)
            word.setTextColor(Color.RED);
        else
            word.setTextColor(Color.GRAY);
        return v;
    }

}
