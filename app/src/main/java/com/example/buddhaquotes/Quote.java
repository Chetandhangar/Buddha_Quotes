package com.example.buddhaquotes;

public class Quote {
    private String quote;
    private String author;

    public Quote(String quotes, String authors) {
        this.quote = quotes;
        this.author = authors;
    }


    public String getQuotes() {
        return quote;
    }
    public void setQuotes(String quotes) {
        this.quote = quotes;
    }

    public void setAuthors(String authors) {
        this.author = authors;
    }

    public String getAuthors() {
        return author;
    }

    @Override
    public String toString() {
        return quote + "\n \n  .. " + author ;
    }
}
