package com.and.blf.myapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Article.class},version = 1, exportSchema = false)
public abstract class ArticleDataBase extends RoomDatabase {
    public static final String DB_NAME = "article_db";
    public static final Object LOCK = new Object();
    private static ArticleDataBase sArticleDataBaseInstance;

    public  static ArticleDataBase getArticleDataBaseInstance(Context context){
        if(sArticleDataBaseInstance == null){
            synchronized (LOCK){
                sArticleDataBaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ArticleDataBase.class,
                        ArticleDataBase.DB_NAME)
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        return sArticleDataBaseInstance;
    }

    public abstract ArticleDao articleDao();

}
