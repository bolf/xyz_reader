package com.and.blf.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT db_id,aspect_ratio,author,published_date,photo,thumb,title,id,bodyFragment FROM article ORDER BY db_id")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT db_id FROM article LIMIT 1")
    List<Article> getOneArticle();

    @Query("SELECT * FROM article WHERE db_id = :db_id")
    LiveData<Article> getArticleById (long db_id);

    @Insert
    void insertArticle(Article article);
}
