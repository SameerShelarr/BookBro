package com.example.booksearch;

public class Books {

    private String mTitle;
    private String mAuthor;
    private String mPublishedDate;
    private String mInfoLink;
    private String mImageLink;

    public Books(String title, String author, String publishedDate, String infoLink, String imageLink){
        mTitle = title;
        mAuthor = author;
        mPublishedDate = publishedDate;
        mInfoLink = infoLink;
        mImageLink = imageLink;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getPublishedDate(){
        return mPublishedDate;
    }

    public String getInfoLink(){
        return mInfoLink;
    }

    public String getImageLink(){
        return mImageLink;
    }
}
