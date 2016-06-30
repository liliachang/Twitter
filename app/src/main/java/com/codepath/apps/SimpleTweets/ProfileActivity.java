package com.codepath.apps.SimpleTweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.SimpleTweets.fragments.UserTimelineFragment;
import com.codepath.apps.SimpleTweets.models.Tweet;
import com.codepath.apps.SimpleTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;
    String screenName;
    UserTimelineFragment fragmentUserTimeline;
    int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();
        // Get account info
        if (getIntent().getParcelableExtra("user") == null) {
            client.getUserInfo(new JsonHttpResponseHandler() { // Async runs in the background, then goes to onSuccess.
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJson(response);
                    // My current user account's info
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    screenName = user.getScreenName();
                    populateProfileHeader(user);
                }

            });
        } else {
            user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateProfileHeader(user);
            //screenName = getIntent().getStringExtra("screen_name");
            screenName = user.getScreenName(); // this does not work either
        }

        // Get the screen name from the activity that launches this

        if (savedInstanceState == null) {
            // Crate the user timeline fragment
            //UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            // Display user fragment within this activity (dynamically)
            FragmentManager fm = getSupportFragmentManager();
            fragmentUserTimeline = (UserTimelineFragment) fm.findFragmentById(R.id.fgUserTweets);
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).transform(new RoundedCornersTransformation(3, 3)).into(ivProfileImage);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);

    }

    public void onComposeView(MenuItem mi) {
        Intent i = new Intent(this, ComposeActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            fragmentUserTimeline.appendTweet(tweet);
        }
    }

}
