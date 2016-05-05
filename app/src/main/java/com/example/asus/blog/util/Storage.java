package com.example.asus.blog.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.blog.activities.pages.Timeline;
import com.example.asus.blog.models.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Chinatip Vichian
 */
public class Storage {
    private static Storage instance;
    private String DB = "ARTICLE";
    private SharedPreferences.Editor editor;

    public static Storage getInstance() {
        if(instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    private Storage() {}

//    public Article SearchWord(Context context, String s) throws JSONException {
//        String wordsJson = context.getSharedPreferences(DB, Context.MODE_PRIVATE).getString(DB, null);
//        if(wordsJson == null || wordsJson.trim().equals("")) {
//            return null;
//        }
//        Type type = new TypeToken< ArrayList<Article>>() {}.getType();
//        ArrayList<Article> words = new Gson().fromJson(wordsJson, type);
//        for(Word w:words){
//            if(w.getWord().equals(s)) {
//                return w;
//            }
//        }
//        return null;
//    }

//    public void editWord(Context context, Article removeWord, Word saveWord) throws JSONException {
//        editor = context.getSharedPreferences(DB, context.MODE_PRIVATE).edit();
//        ArrayList<Word> words = loadWords(context);
//        words.remove(removeWord);
//        words.add(saveWord);
//        saveWordsJson(new Gson().toJson(words));
//    }

    public void saveWord(Context context, Article article) throws JSONException {
        editor = context.getSharedPreferences(DB, context.MODE_PRIVATE).edit();
        ArrayList<Article> articles = loadArticles(context);
        boolean isSave = false;
        for (int i = 0; i< articles.size(); i++) {
            Article a = articles.get(i);
            //check same article
//            if(w.getHeader().equalsIgnoreCase(article.getHeader())){
//                w.setAllSynonyms(word.getSynonyms());
//                w.setAllTranslations(word.getTranslations());
//                isSave = true;
//            }
        }
        if(!isSave)
            articles.add(article);
        saveArticleJson(new Gson().toJson(articles));
    }

    public void deleteArticle(Context context, Article article) throws JSONException {
        editor =  context.getSharedPreferences(DB, Context.MODE_PRIVATE).edit();
        ArrayList<Article> articles = loadArticles(context);
        /// fix this <for>?
        for (int i = 0; i< articles.size(); i++) {
            Article a = articles.get(i);
            if(a.getHeader().equals(article.getHeader())){
                articles.remove(a);
            }
        }
        saveArticleJson(new Gson().toJson(articles));
    }

    public  ArrayList<Article> loadArticles(Context context) throws JSONException {
        String articleJson = context.getSharedPreferences(DB, Context.MODE_PRIVATE).getString(DB, null);
        if(articleJson == null || articleJson.trim().equals("")) {
            return new ArrayList<Article>();
        }
        Type type = new TypeToken< ArrayList < Article >>() {}.getType();
        return new Gson().fromJson(articleJson, type);
    }

    public void deleteAllArticles(Context context) {
        editor =  context.getSharedPreferences(DB, Context.MODE_PRIVATE).edit();
        saveArticleJson(new Gson().toJson(new ArrayList<Article>()));
    }

    private void saveArticleJson(String articleJson) {
        editor.putString(DB, articleJson);
        editor.commit();
    }
}