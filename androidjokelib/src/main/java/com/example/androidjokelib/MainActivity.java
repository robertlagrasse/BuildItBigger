package com.example.androidjokelib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajl_activity_main);
        String TAG = "AndroidLib";

        String joke = getIntent().getStringExtra("joke");

        TextView tvJoke = (TextView) findViewById(R.id.tvJoke);
        tvJoke.setText("AndroidJokeLib recieved via intent: " + joke);
    }
}
