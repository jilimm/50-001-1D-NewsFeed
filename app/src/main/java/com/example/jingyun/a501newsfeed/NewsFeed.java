package com.example.jingyun.a501newsfeed;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;


//supposed to be a fragment!!!

public class NewsFeed extends AppCompatActivity {

    private TextView articleTitle;
    private ImageView newsImg;
    private List<NewsItem> articleList = new ArrayList<NewsItem>();
    //private ListView newsListView;
    //public static NewsAdapter newsAdapter;
    String newsTitlesfromJSON="";
    Context context;
    private final String reqURL="https://newsapi.org/v2/everything?q=sustainable%20business%20practices&sortBy=popularity&apiKey=1e128baae3034bd899a7a791748e8e47";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        articleTitle = (TextView) findViewById(R.id.newsTitle);
        newsImg = (ImageView) findViewById(R.id.newsItemImg);


        /*
        newsListView = (ListView) findViewById(R.id.newsList);
        newsAdapter = new NewsAdapter();
        newsListView.setAdapter(newsAdapter);
        //newsTitles = (TextView) findViewById(R.id.testnews);
        */


        new NewsFeed.AsyncHttpTask().execute();
    }




    public class AsyncHttpTask extends AsyncTask<Void, Void, Void>{
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();


        @Override
        protected Void doInBackground(Void... arg0) {
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(reqURL);
                urlConnection = (HttpURLConnection)url.openConnection();
                String response = streamtoString(urlConnection.getInputStream());
                articleList = parseResult(response);

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (int i=0; i<1; i++){
                articleTitle.setText(articleList.get(i).getNewsTitle());
                Picasso.with(NewsFeed.this)
                        .load(articleList.get(i).getImageURL())
                        .into(newsImg);
            }
        }
    }

    private String streamtoString(InputStream stream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        String result = "";
        try {
            while ((data = bufferedReader.readLine()) != null) {
                result += data;
            }
            if (null != stream){
                stream.close();
            }
        } catch (Exception e){
            e.printStackTrace();;
        }
        return result;
    }

    private List<NewsItem> parseResult(String result){
        JSONObject response = null;
        List<NewsItem> newsItemList = new ArrayList<NewsItem>();
        String totalRes="";
        try {
            response = new JSONObject(result);
            JSONArray articles = response.optJSONArray("articles");
            //test for first jSON object
            for (int i=0; i<articles.length();i++){
                JSONObject article = articles.optJSONObject(i);
                String title = article.optString("title");
                String imageURL = article.optString("urlToImage");
                String articleLink = article.optString("url");
                //to check if I am getting any news titles
                newsItemList.add(new NewsItem(title, imageURL,articleLink));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsItemList;
    }
}
