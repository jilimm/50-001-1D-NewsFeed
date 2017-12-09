package com.example.jingyun.a501newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing Yun on 10/12/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.newsViewHolder>{
    public ArrayList<NewsItem> articleList;
    private static int viewHolderCount=0;
    Context context;

    public NewsAdapter(Context context, ArrayList<NewsItem> newsItemList) {
        this.context=context;
        this.articleList = newsItemList;
    }

    @Override
    public newsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemLayoutID = R.layout.card;
        Layout inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImm = false;

        View view = inflater.inflate(itemLayoutID,parent,shouldAttachToParentImm);

        newsViewHolder = newsViewHolder = new newsViewHolder(view);

        return newsViewHolder;
    }

   

    @Override
    public void onBindViewHolder(newsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

  class newsViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        ImageView articleImg;
        TextView articleTextView;
        View v;

      newsViewHolder(View itemView) {
          super(itemView);
          this.v = itemView;
          itemView.setOnClickListener(this);
      }

      public void bind(int position){
          articleTextView = (TextView)this.v.findViewById(R.id.articleTitle);
          articleImg = (ImageView) this.v.findViewById(R.id.newsImg);

          Picasso.with(context)
                  .load(articleList.get(position).getImageURL())
                  .into(articleImg);

          String articleTitle = articleList.get(position).getNewsTitle();
          articleTextView.setText(articleTitle);
      }

      @Override
      public void onClick(View view) {
          int clickedPosition = getAdapterPosition();
          NewsItem thisarticle = articleList.get(clickedPosition);
          //builds implicit intent to launch user to the article
          Uri artWebpage = Uri.parse(thisarticle.getNewsLink());
          Intent webIntent = new Intent(Intent.ACTION_VIEW,artWebpage);
      }
  }
}