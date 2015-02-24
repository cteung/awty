package cteung.washington.edu.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chris_000 on 2/22/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String m = intent.getStringExtra("message");
        String p = intent.getStringExtra("phone");

        // For our recurring task, we'll just display a message
        Toast.makeText(context, p+": "+m, Toast.LENGTH_SHORT).show();
    }
}
