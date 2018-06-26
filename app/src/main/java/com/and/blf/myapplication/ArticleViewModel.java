package com.and.blf.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

class ArticleViewModel extends ViewModel{
    private LiveData<Article> mArticleLiveData;

    public LiveData<Article> getArticleLiveData(){
        return mArticleLiveData;
    }

    public ArticleViewModel(ArticleDataBase db, long articleDbId) {
        mArticleLiveData = db.articleDao().getArticleById(articleDbId);
    }
}
