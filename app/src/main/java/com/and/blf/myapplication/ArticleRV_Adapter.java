package com.and.blf.myapplication;

import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleRV_Adapter extends RecyclerView.Adapter<ArticleRV_Adapter.ArticleViewHolder> {
    private List<Article> mArticles;

    public ArticleRV_Adapter() {
        mArticles = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_list_item,viewGroup,false);
        layoutView.setTag(mArticles);
        return new ArticleViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {
        Article currArt = mArticles.get(position);
        Picasso.get().load(currArt.getPhoto()).fit().centerCrop().into(articleViewHolder.mImageView);
        articleViewHolder.mTitleTV.setText(currArt.getTitle());
        articleViewHolder.mAuthorTV.setText(currArt.getAuthor());
        articleViewHolder.mPublishDateTV.setText(currArt.getPublished_date());
        articleViewHolder.mBodyTV.setText(currArt.getBodyFragment());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void setArticles(List<Article> articles){
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mAuthorTV;
        TextView mTitleTV;
        TextView mPublishDateTV;
        TextView mBodyTV;
        ImageView mImageView;

        ArticleViewHolder(View layoutView) {
            super(layoutView);
            layoutView.setOnClickListener(this);
            mTitleTV = layoutView.findViewById(R.id.item_article_title);
            mAuthorTV = layoutView.findViewById(R.id.item_article_author);
            mPublishDateTV = layoutView.findViewById(R.id.item_published_date);
            mBodyTV = layoutView.findViewById(R.id.item_body);
            mImageView = layoutView.findViewById(R.id.item_imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ArticleActivity.class);
            intent.putExtra("ARTICLE_DBID", ((ArrayList<Article>) itemView.getTag()).get(getAdapterPosition()).getDb_id());
            v.getContext().startActivity(intent);
        }
    }
}
