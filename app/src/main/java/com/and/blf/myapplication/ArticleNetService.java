package com.and.blf.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleNetService {
    @GET("/{endpoint}")
    Call<List<Article>> getArticles(@Path("endpoint") String endpoint);
}
