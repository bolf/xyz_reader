package com.and.blf.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        Toolbar toolbar = findViewById(R.id.article_detail_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            if (dpWidth < 700) {
                int padding = (int) dpWidth / 4;
                findViewById(R.id.article_detail_constraint_lo).setPadding(padding, 0, padding, 0);
            }
        }

        final long articleDbId = getIntent().getLongExtra("ARTICLE_DBID",0l);

        ArticleViewModelFactory factory = new ArticleViewModelFactory(ArticleDataBase.getArticleDataBaseInstance(ArticleActivity.this),articleDbId);

        final ArticleViewModel viewModel = ViewModelProviders.of(this,factory).get(ArticleViewModel.class);

        viewModel.getArticleLiveData().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                viewModel.getArticleLiveData().removeObserver(this);
                populateUI(article);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void populateUI(Article article) {
        ((TextView)findViewById(R.id.article_detail_title)).setText(article.getTitle());
        ((TextView)findViewById(R.id.article_detail_body)).setText(article.getBody());
        ((TextView)findViewById(R.id.item_article_author)).setText(article.getAuthor());
        ((TextView)findViewById(R.id.article_published_date)).setText(article.getPublished_date());
        getSupportActionBar().setTitle(article.getTitle());

        Picasso.get().load(article.getPhoto()).fit().centerCrop().into((ImageView)findViewById(R.id.article_detail_photo));
    }
}
