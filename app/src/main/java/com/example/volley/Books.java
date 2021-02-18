package com.example.volley;

public class Books {
    String id;
    String title;
    String author;

   public Books(String id,String title,String author){
       this.id=id;
       this.title=title;
       this.author=author;


   }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setAuthor(String author){
        this.author=author;
    }
    public String getAuthor(){
        return this.author;
    }
}
