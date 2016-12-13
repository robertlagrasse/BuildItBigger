package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    // This is the AsyncTask that gets executed when a joke is requested
    DownloadTask downloadTask = null;

    // onPostExecute() sends out a broadcast. We're interested in a specific broadcast
    // so we build a filter to look for it.
    IntentFilter filter = new IntentFilter("com.udacity.gradle.builditbigger.CUSTOM_INTENT");

    // Triggering onPostExecute() means the AsyncTask completed, so we can make decisions
    // based on the attributes of downloadTask.
    BroadcastReceiver jokeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive", "Intent Received.");
            TextView tvResult = (TextView) findViewById(R.id.results_text_view);
            // DownloadTask contains status information.
            if (downloadTask.stringWasNull() || downloadTask.stringWasEmpty()){
                tvResult.setText(R.string.missing_jokes);
                downloadTask = null;
            }
            else if (downloadTask.communicationsError()){
                tvResult.setText(R.string.comm_issue);
                downloadTask = null;
            }
            else {
                // Fire off the Intent
                intent = new Intent(context, com.example.androidjokelib.MainActivity.class);
                intent.putExtra("joke", downloadTask.getDownloadedData());
                downloadTask = null;
                startActivity(intent);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is sitting in onCreate, but will have to move once everything goes to the fragment.
        this.registerReceiver(jokeReceiver, filter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        // Setup UI
        TextView tvResult = (TextView) findViewById(R.id.results_text_view);
        tvResult.setText("Fetching a hilarious joke...");

        if (downloadTask == null) {
            downloadTask = new DownloadTask();
            downloadTask.execute(new Pair<Context, String>(this, "to be removed"));
        } else {
            tvResult.setText("Be patient, dude.");
        }

//        EndpointsAsyncTask eat = new EndpointsAsyncTask();
//        eat.execute(new Pair<Context, String>(this, "standard"));
//
//        TextView tvResult = (TextView) findViewById(R.id.results_text_view);
//        tvResult.setText("Fetching a hilarious joke...");
    }

//    public boolean checkAsyncTask(View view){
//        EndpointsAsyncTask eat = new EndpointsAsyncTask();
//        eat.setTestRun(true);
//        eat.execute(new Pair<Context, String>(this, "standard"));
//        return false;
//    }

//    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
//        private MyApi myApiService = null;
//        private Context context;
//        private Boolean testRun = false;
//
//        public void setTestRun(Boolean testRun) {
//            this.testRun = testRun;
//        }
//
//        @Override
//        protected String doInBackground(Pair<Context, String>... params) {
//            if(myApiService == null) {  // Only do this once
//                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                        new AndroidJsonFactory(), null)
//                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                            @Override
//                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                abstractGoogleClientRequest.setDisableGZipContent(true);
//                            }
//                        });
//                myApiService = builder.build();
//            }
//
//            context = params[0].first;
//            String name = params[0].second;
//
//            String serverResult = null;
//            try {
//                serverResult = myApiService.tellJoke().execute().getData();
//                return serverResult;
//            } catch (IOException e) {
//                return e.getMessage();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            TextView tvResult = (TextView) findViewById(R.id.results_text_view);
//
//            if (testRun) {
//                Log.e("testRun", "result: " + result);
//                if ((result == null) || (result.equals("")) || result.contains("failed to connect to")) {
//                    tvResult.setText("FAIL");
//                }
//                else {
//                    tvResult.setText("PASS");
//                }
//            }
//            else {
//                if ((result == null) || (result.equals("")) || result.contains("failed to connect to")) {
//                    tvResult.setText("Missing jokes. Not funny.");
//                } else {
//                    Intent intent = new Intent(context, com.example.androidjokelib.MainActivity.class);
//                    intent.putExtra("joke", result);
//                    startActivity(intent);
//                }
//            }
//        }
//    }
}
