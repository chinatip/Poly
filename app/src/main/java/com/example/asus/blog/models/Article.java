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
    public Article(String username, String header, String text,ArrayList<File> images) {
        this.username = username;
        this.header = header;
        this.text = text;
        this.images = images;
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
}
