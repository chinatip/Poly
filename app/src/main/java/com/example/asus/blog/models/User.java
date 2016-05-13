package com.example.asus.blog.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinatip Vichian
 */
public class User {
    private int ID;
    private String firstname, lastname, username, password;
    private List<Article> myArticles;
    private List<User> following;

    public User(int id, String uname, String pass,String fname, String lname) {
        ID = id;
        firstname = fname;
        lastname = lname;
        username = uname;
        password = pass;
        myArticles = new ArrayList<>();
        following = new ArrayList<>();
    }

    public void follow(User user) {
        following.add(user);
    }

    public List<User> getFollowing() {
        return following;
    }

    public void addArticle(Article ar) {
        myArticles.add(ar);
    }

    public List<Article> getMyArticles() {
        return myArticles;
    }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setFirstname(String fname) { firstname = fname; }

    public void setLastname(String lname) { lastname = lname; }

    public void setPassword(String pass) { password = pass; }
}
