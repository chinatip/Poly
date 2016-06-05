package com.example.asus.blog.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.blog.models.Article;
import com.example.asus.blog.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Chinatip Vichian
 */
public class UserStorage {
    private static int currentID = 0;
    private static UserStorage instance;
    private String DB = "USER";
    private SharedPreferences.Editor editor;

    public static UserStorage getInstance() {
        if(instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public static int getCurrentID() { return currentID; }

    private UserStorage() {}

    public boolean saveUser(Context context, User user) throws JSONException {
        editor = context.getSharedPreferences(DB, context.MODE_PRIVATE).edit();
        ArrayList<User> users = loadUsers(context);
        if(users.size()>0)
            for (int i = 0; i< users.size(); i++) {
                User u = users.get(i);
                if(u.getUsername().equals(user.getUsername())){
                    return false;
                }
            }
        users.add(user);
        currentID++;

        saveUserJson(new Gson().toJson(users));
        return true;
    }


    public boolean userLogIn(Context context, String username, String password) throws JSONException {
        String usersJson = context.getSharedPreferences(DB, Context.MODE_PRIVATE).getString(DB, null);
        if(usersJson == null || usersJson.trim().equals("")) {
            return false;
        }
        Type type = new TypeToken< ArrayList < User >>() {}.getType();
        ArrayList<User> users = new Gson().fromJson(usersJson, type);
        if(users.size()>0) {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                if (u.getUsername().equals(username) && u.getPassword().equals(password))
                    return true;
            }
        }
        return false;
    }

//    public void deleteArticle(Context context, Article article) throws JSONException {
//        editor =  context.getSharedPreferences(DB, Context.MODE_PRIVATE).edit();
//        ArrayList<Article> articles = loadArticles(context);
//        /// fix this <for>?
//        for (int i = 0; i< articles.size(); i++) {
//            Article a = articles.get(i);
//            if(a.getHeader().equals(article.getHeader())){
//                articles.remove(a);
//            }
//        }
//        saveArticleJson(new Gson().toJson(articles));
//    }

    public  ArrayList<User> loadUsers(Context context) throws JSONException {
        String userJson = context.getSharedPreferences(DB, Context.MODE_PRIVATE).getString(DB, null);
        if(userJson == null || userJson.trim().equals("")) {
            return new ArrayList<>();
        }
        Type type = new TypeToken< ArrayList < User >>() {}.getType();
        return new Gson().fromJson(userJson, type);
    }

//    public void deleteAllArticles(Context context) {
//        editor =  context.getSharedPreferences(DB, Context.MODE_PRIVATE).edit();
//        saveArticleJson(new Gson().toJson(new ArrayList<Article>()));
//    }

    public User getUser(Context context, String username) {
        String usersJson = context.getSharedPreferences(DB, Context.MODE_PRIVATE).getString(DB, null);
        if(usersJson == null || usersJson.trim().equals("")) {
            return null;
        }
        Type type = new TypeToken< ArrayList < User >>() {}.getType();
        ArrayList<User> users = new Gson().fromJson(usersJson, type);
        for(User u:users){
            if(u.getUsername().equals(username)) return u;
        }
        return null;
    }

    private void saveUserJson(String userJson) {
        editor.putString(DB, userJson);
        editor.commit();
    }

    public void UpdateUser(Context context, User user) throws JSONException{
        editor = context.getSharedPreferences(DB, context.MODE_PRIVATE).edit();
        ArrayList<User> users = loadUsers(context);
        if(users.size()>0)
            for (int i = 0; i< users.size(); i++) {
                User u = users.get(i);
                if(u.getUsername().equals(user.getUsername())){
                    u.setFollowing(user.getFollowing());
                }
            }
        users.add(user);
        currentID++;

        saveUserJson(new Gson().toJson(users));
    }
}