package com.example.asus.blog.models;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 3/5/2559.
 */
public class Article implements Serializable {
    private String header,text;
    private String username;
    private ArrayList images;
    private ArrayList<String> keywords;
    public Article(String username, String header, String text,ArrayList images, ArrayList<String> keywords) {
        this.username = username;
        this.header = header;
        this.text = text;
        this.images = images;
        this.keywords = keywords;
    }
    public String getUsername(){
        return username;
    }
    public String getHeader(){
        return header;
    }
    public String getText(){
        return text;
    }
    public void setHeader(String header){
        this.header = header;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setImages(ArrayList images){
        this.images = images;
    }
    public ArrayList getImages() {
        return images;
    }
    public void setKeywords(ArrayList<String> keywords){
        this.keywords = keywords;
    }
    public ArrayList<String> getKeywords() {
        return keywords;
    }
    public boolean search(String s) {
        boolean isIn = false;
        for(int i =0;i<keywords.size();i++) {
            String ky = keywords.get(i);
            if (s.equalsIgnoreCase(ky.subSequence(0, s.length()).toString()) || s.equals("")) {
                isIn = true;
            }
        }
        return isIn;
    }
}
