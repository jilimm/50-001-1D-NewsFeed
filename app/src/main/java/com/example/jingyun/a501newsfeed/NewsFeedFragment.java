package com.example.jingyun.a501newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class NewsFeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView newsItemListView;
    List<NewsItem> newsItemList;
    public static NewsAdapter newsItemadapter;
    private static Parcelable state;
    private OnFragmentInteractionListener mListener;
    private final String reqURL="https://newsapi.org/v2/everything?q=sustainable%20business%20practices&sortBy=popularity&apiKey=1e128baae3034bd899a7a791748e8e47";



    public NewsFeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFeedFragment.
     */
    // TODO: Rename and change types and number of parameters


    public static NewsFeedFragment newInstance(String param1, String param2) {
        NewsFeedFragment fragment = new NewsFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_feed,container,false);
        newsItemadapter = new NewsAdapter(getActivity(), newsItemList);
        newsItemListView = (ListView) rootView.findViewById(R.id.newsList);
        newsItemListView.setAdapter(newsItemadapter);
        newsItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Uri webpage = Uri.parse(newsItemList.get(position).getImageURL());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            }
        });


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public class AsyncHttpTask extends AsyncTask<Void, Void, Void> {
        List<NewsItem> articleList = new ArrayList<NewsItem>();


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
            for (int i=0; i<articleList.size(); i++){
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
