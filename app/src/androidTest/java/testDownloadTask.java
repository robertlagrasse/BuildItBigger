import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContext;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.udacity.gradle.builditbigger.DownloadTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * Created by robert on 12/8/16.
 */

@RunWith(AndroidJUnit4.class)
public class testDownloadTask {


    @Test
    public void testAsyncTask () {

        MockContext mockContext = new MockContext();
        DownloadTask downloadTask = new DownloadTask();

        downloadTask.execute(new Pair<Context, String>(mockContext, "mock"));
        try {
            downloadTask.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        assertFalse(downloadTask.communicationsError());
        assertFalse(downloadTask.stringWasNull());
        assertFalse(downloadTask.stringWasEmpty());
    }

}
