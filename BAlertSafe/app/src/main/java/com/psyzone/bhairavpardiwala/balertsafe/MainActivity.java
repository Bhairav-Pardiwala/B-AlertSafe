package com.psyzone.bhairavpardiwala.balertsafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    List<String> listItem=new ArrayList<String>();
    ListView listView;
     ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        registerWithNotificationHubs();
        FirebaseService.createChannelAndHandleNotifications(getApplicationContext());

       adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);

         listView=(ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String date2="";
//        date2="2019-10-06T11:41:56.1220000Z";
//        date2=date2.replace("T"," ");
//        date2=date2.substring(0,20);
//        Date date = null;
//        try {
//            date = df.parse(date2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//                df.setTimeZone(TimeZone.getDefault());
//                String formattedDate = df.format(date);
        //U+1F513

    }
    public void registerWithNotificationHubs()
    {
        if (checkPlayServices()) {
            // Start IntentService to register this application with FCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog box that enables  users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                //ToastNotify("This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isVisible = false;
    }

    public void ToastNotify(final String notificationMessage,final String date) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String date2="";
                date2=date;
                date2=date2.replace("T"," ");
                date2=date2.substring(0,20);
                Date date = null;
                try {
                    date = df.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                df.setTimeZone(TimeZone.getDefault());
//                String formattedDate = df.format(date);
                if(notificationMessage.indexOf("0")==0)
                {
                    listItem.add(getEmojiByUnicode(0x1F512)+" Lock " +date);

                }
                else
                {
                    listItem.add(getEmojiByUnicode(0x1F513)+ " Unlock "+date);
                }
                adapter.notifyDataSetChanged();
               // Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG).show();
                //TextView helloText = (TextView) findViewById(R.id.text_hello);
                //helloText.setText(notificationMessage);

            }
        });
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
