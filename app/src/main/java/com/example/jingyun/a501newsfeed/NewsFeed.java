package com.example.jingyun.a501newsfeed;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsFeed extends AppCompatActivity {

    private TextView newsTitles;
    String newsTitlesfromJSON="";
    private final String reqURL="https://newsapi.org/v2/everything?q=sustainable%20business%20practices&sortBy=popularity&apiKey=1e128baae3034bd899a7a791748e8e47";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        newsTitles = (TextView) findViewById(R.id.testnews);

        new NewsFeed.AsyncHttpTask().execute(reqURL);
    }




    public class AsyncHttpTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                String response = streamtoString(urlConnection.getInputStream());
                result = parseResult(response);
                return  result;

            } catch (Exception e){
                e.printStackTrace();
            }
           return null;
        }

        @Override
        protected void onPostExecute(String s) {
            newsTitles.setText(s);
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

    private String parseResult(String result){
        JSONObject response = null;
        String totalRes="";
        try {
            response = new JSONObject(result);
            JSONArray articles = response.optJSONArray("articles");

            for (int i=0; i<articles.length();i++){
                JSONObject article = articles.optJSONObject(i);
                String title = article.optString("title");
                String imageURL = article.optString("urlToImage");
                String articleLink = article.optString("url");
                //to check if I am getting any news titles
                totalRes+="TITLE IS";
                totalRes+=title;
                totalRes+="\n";
                totalRes+="\n";
                Log.i("Titles Titties", title);

                totalRes+="IMAGE URL IS";
                totalRes+=imageURL;
                totalRes+="\n";
                totalRes+="\n";
                Log.i("Eeeemage URLz", imageURL);

                totalRes+="ARTICLE LINK IS";
                totalRes+=articleLink;
                totalRes+="\n";
                totalRes+="\n";
                Log.i("ARRRRticle Linkzzesz", articleLink);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalRes;
    }
}
