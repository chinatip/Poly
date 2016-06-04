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
    private int writerID;
    private ArrayList<File> images;
    public Article(int writerID, String header, String text,ArrayList<File> images) {
        this.writerID = writerID;
        this.header = header;
        this.text = text;
        this.images = images;
    }
    public int getWriterID(){
        return writerID;
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
    public void setImages(ArrayList<File> images){
        this.images = images;
    }
    public ArrayList<File> getImages() {
        return images;
    }
}
