package com.codepath.apps.SimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.SimpleTweets.R;
import com.codepath.apps.SimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.SimpleTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liliachang on 6/27/16.
 */
public class TweetsListFragment extends Fragment {

    protected ArrayList<Tweet> tweets;
    protected TweetsArrayAdapter aTweets;
    protected ListView lvTweets;
    // private SwipeRefreshLayout swipeContainer;



    // inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        // Find the listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        // Connect adapter to list view
        lvTweets.setAdapter(aTweets);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the arraylist (data source)
        tweets = new ArrayList<>();
        // Construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
       /* swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });*/

    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void appendTweet(Tweet tweet) {
        tweets.add(0, tweet); //add?
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }

}
