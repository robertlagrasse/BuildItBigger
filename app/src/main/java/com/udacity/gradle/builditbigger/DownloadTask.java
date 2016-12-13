package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.example.robert.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by robert on 12/7/16.
 */

public class DownloadTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private MyApi myApiService = null;
    private Context context;
    private Boolean nullString = false;
    private Boolean emptyString = false;
    private Boolean commError = false;
    private String downloadedData = null;
    private String name = null;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
////                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setRootUrl("http://192.168.1.136:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://udacitybuilditbigger-152022.appspot.com/_ah/api/");
            myApiService = builder.build();
        }

        context = params[0].first;
        name = params[0].second;

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            commError = true;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Check returned result
        if (result == null){
            nullString = true;
        }
        if (result.equals("")) {
            emptyString = true;
        }
        if (commError || nullString || emptyString){
            downloadedData = null;
        } else {
            downloadedData = result;
        }
        // Broadcast
        if (!name.equals("mock")) {
            Intent intent = new Intent();
            intent.setAction("com.udacity.gradle.builditbigger.CUSTOM_INTENT");
            context.sendBroadcast(intent);
        }
    }

    public Boolean stringWasNull(){return nullString;}
    public Boolean stringWasEmpty(){return emptyString;}
    public Boolean communicationsError(){return commError;}
    public String getDownloadedData(){return downloadedData;}

}
