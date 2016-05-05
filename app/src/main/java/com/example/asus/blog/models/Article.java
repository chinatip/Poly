package com.example.asus.blog.models;

import java.io.Serializable;

/**
 * Created by Asus on 3/5/2559.
 */
public class Article implements Serializable {
    private String header,text;
    private int writerID;
    public Article(int writerID, String header, String text) {
        this.writerID = writerID;
        this.header = header;
        this.text = text;
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
}
