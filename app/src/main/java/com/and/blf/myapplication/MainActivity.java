package com.and.blf.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView mArticleRecyclerView;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    
    ArticleRV_Adapter mArticleRecyclerViewAdapter = new ArticleRV_Adapter();
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle(getString(R.string.app_name));
        }
        mLayoutManager = new LinearLayoutManager(this);

        mArticleRecyclerView = findViewById(R.id.articleRV);
        mArticleRecyclerView.setHasFixedSize(true);
        mArticleRecyclerView.setLayoutManager(mLayoutManager);
        mArticleRecyclerView.setAdapter(mArticleRecyclerViewAdapter);

        DividerItemDecoration itemDecorator_border = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator_border.setDrawable(ContextCompat.getDrawable(this, R.drawable.rv_border));

        mArticleRecyclerView.addItemDecoration(itemDecorator_border);

        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if(articles.size() == 0){
                    loadArticles();
                }
                else{
                    findViewById(R.id.list_loading_progressBar).setVisibility(View.GONE);
                }
                mArticleRecyclerViewAdapter.setArticles(articles);
            }
        });
    }

    private void loadArticles(){
        //check the connectivity
        Toast.makeText(this, R.string.retrieving_articles_msg,Toast.LENGTH_SHORT).show();
        Call<List<Article>> articlesCall = ArticleNetworkUtils.getArticleNetService().getArticles("xyz-reader-json");
        articlesCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articleList = response.body();

                final ArticleDao artDao = ArticleDataBase.getArticleDataBaseInstance(MainActivity.this).articleDao();
                for(Article article : articleList){
                    final Article curArt = article;

                    curArt.setPublished_date(parsePublishedDate(curArt.getPublished_date()));

                    curArt.setBodyFragment(curArt.getBody().substring(0,1024).replaceAll("\\s+", " "));

                    curArt.setBody(curArt.getBody().substring(0,4096));

                    curArt.setAuthor("by ".concat(curArt.getAuthor()));

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            artDao.insertArticle(curArt);
                        }
                    });
                }
                findViewById(R.id.list_loading_progressBar).setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton_reloadrv).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.could_not_retrieve_msg,Toast.LENGTH_LONG).show();
                t.printStackTrace();
                findViewById(R.id.list_loading_progressBar).setVisibility(View.GONE);
                findViewById(R.id.floatingActionButton_reloadrv).setVisibility(View.VISIBLE);
            }
        });
    }

    @NonNull
    private String parsePublishedDate(String published_date) {
        try {
            return "(".concat(android.text.format.DateFormat.format("MM-dd-yyyy",dateFormat.parse(published_date)).toString().concat(")"));
        } catch (ParseException ex) {
            return "-/-";
        }
    }

    public void reloadOnFab(View view) {
        findViewById(R.id.list_loading_progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.floatingActionButton_reloadrv).setVisibility(View.GONE);
        setupViewModel();
    }
}
