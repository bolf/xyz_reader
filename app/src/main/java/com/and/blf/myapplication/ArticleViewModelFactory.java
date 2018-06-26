package com.and.blf.myapplication;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ArticleViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ArticleDataBase mDb;
    private final long mArticleDbId;

    public ArticleViewModelFactory(ArticleDataBase db, long articleDbId) {
        mDb = db;
        mArticleDbId = articleDbId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArticleViewModel(mDb,mArticleDbId);
    }
}
