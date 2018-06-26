package com.and.blf.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Article>> articles;

    public MainViewModel(@NonNull Application application) {
        super(application);
        ArticleDataBase db = ArticleDataBase.getArticleDataBaseInstance(this.getApplication());
        articles = db.articleDao().getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return articles;
    }

}
