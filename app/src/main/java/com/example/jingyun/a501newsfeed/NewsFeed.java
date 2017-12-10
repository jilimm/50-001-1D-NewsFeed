package com.example.jingyun.a501newsfeed;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


//****this is my main activity it will call on my news fragment activity
//*****very impt to habe the newsfee fragment.on frag interaction listener
public class NewsFeed extends AppCompatActivity
    implements NewsFeedFragment.OnFragmentInteractionListener{


    NewsFeedFragment newsFeedFragment;
    android.support.v4.app.FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        newsFeedFragment = new NewsFeedFragment();
        //FOR DEBUGGING Toast.makeText(this, "activity", Toast.LENGTH_SHORT).show();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.newsFeedFragmentContainer,newsFeedFragment);
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
