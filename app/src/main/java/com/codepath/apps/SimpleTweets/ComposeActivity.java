package com.codepath.apps.SimpleTweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.SimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    EditText etComposeBox;
    TextView tvTextCount;
    TextWatcher textWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
        etComposeBox = (EditText) findViewById(R.id.etComposeBox);
        tvTextCount = (TextView) findViewById(R.id.tvTextCount);
        textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                tvTextCount.setText(String.valueOf(140 - s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        etComposeBox.addTextChangedListener(textWatcher);
    }

    public void onSubmit(View v) {
        EditText etComposeBox = (EditText) findViewById(R.id.etComposeBox);
        //Intent i = new Intent();
        String status = etComposeBox.getText().toString();
        client.updateStatus(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);
                Intent i = new Intent();
                i.putExtra("tweet", Parcels.wrap(tweet));
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }


}
