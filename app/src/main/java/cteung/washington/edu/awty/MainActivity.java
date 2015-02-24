package cteung.washington.edu.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private PendingIntent pendingIntent;
    private Intent alarmIntent;
    private boolean set;
    private boolean message;
    private boolean phone;
    private boolean freq;
    private int min;
    private EditText m;
    private EditText p;
    private EditText f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

        set = PendingIntent.getBroadcast(MainActivity.this, 1, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null;
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        checkAlarmSetup();

        m = (EditText) findViewById(R.id.message);
        m.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    message = false;
                } else {
                    message = true;
                    checkText();
                }
            }
        });

        p = (EditText) findViewById(R.id.phone);
        p.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    phone = false;
                } else {
                    phone = true;
                    checkText();
                }
            }
        });

        f = (EditText) findViewById(R.id.freq);
        f.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    freq = false;
                } else {
                    freq = true;
                    checkText();
                }
            }
        });

        findViewById(R.id.startAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set){
                    cancel();
                }else {
                    start();
                }
            }
        });
    }

    public void checkText(){
        Button b = (Button) findViewById(R.id.startAlarm);

        String text = f.getText().toString();
        try {
            min = Integer.parseInt(text);
            if (min <= 0){
                freq = false;
            }
        } catch (NumberFormatException e) {
            freq = false;
        }

        if(message & phone & freq){
            b.setEnabled(true);
            alarmIntent.putExtra("message", m.getText().toString());
            alarmIntent.putExtra("phone", p.getText().toString());
        }else {
            b.setEnabled(false);
        }
    }

    public void checkAlarmSetup(){
        if (!set){
            set = true;
            Toast.makeText(getApplicationContext(), "SET!", Toast.LENGTH_SHORT).show();

            Button button = (Button)findViewById(R.id.startAlarm);
            button.setText("Stop");
        }else {
            set = false;
            Toast.makeText(getApplicationContext(), "NOT SET!", Toast.LENGTH_SHORT).show();

            Button button = (Button)findViewById(R.id.startAlarm);
            button.setText("Start");
        }
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = min * 60000;

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        set = true;
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();

        Button button = (Button)findViewById(R.id.startAlarm);
        button.setText("Stop");
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        set = false;
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();

        Button button = (Button)findViewById(R.id.startAlarm);
        button.setText("Start");
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
}
