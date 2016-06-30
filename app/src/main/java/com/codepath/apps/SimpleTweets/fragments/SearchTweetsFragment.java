package com.codepath.apps.SimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.SimpleTweets.TwitterApplication;
import com.codepath.apps.SimpleTweets.TwitterClient;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SearchTweetsFragment extends TweetsListFragment {
    private TwitterClient client;
    private String q;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the client
        client = TwitterApplication.getRestClient();
    }

    public static SearchTweetsFragment newInstance(String query) {
        SearchTweetsFragment searchFragment = new SearchTweetsFragment();
        Bundle args = new Bundle();
        args.putString("q", query);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    //Send an API request to get the timeline json
    // Fill the listview by creating the Tweet objects from the json

    public void fetchNewSearchResults(String query) {
        client.searchTweets(query, new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
                try {
                    JSONArray jsonArray = (JSONArray) response.getJSONArray("statuses");
                    addAll(Tweet.fromJSONArray(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            // FAILURE


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }



}
