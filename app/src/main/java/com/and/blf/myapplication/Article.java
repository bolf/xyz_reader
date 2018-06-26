package com.and.blf.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "article")
public class Article {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "db_id")
    private transient long db_id;
    private String id;
    private String title;
    private String author;
    @ColumnInfo(name = "bodyFragment")
    private transient String bodyFragment;
    private String body;
    private String thumb;
    private String photo;
    private String aspect_ratio;
    private String published_date;

    public Article(long db_id, String id, String title, String author, String body, String thumb, String photo, String aspect_ratio, String published_date, String bodyFragment) {
        this.db_id = db_id;
        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumb;
        this.photo = photo;
        this.aspect_ratio = aspect_ratio;
        this.published_date = published_date;
        this.bodyFragment = bodyFragment;
    }

    public String getBodyFragment() {
        return bodyFragment;
    }

    public void setBodyFragment(String bodyFragment) {
        this.bodyFragment = bodyFragment;
    }

    public long getDb_id() {
        return db_id;
    }

    public void setDb_id(long db_id) {
        this.db_id = db_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }
}
