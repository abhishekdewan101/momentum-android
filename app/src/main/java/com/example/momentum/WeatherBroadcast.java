package com.example.momentum;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by a.dewan on 7/31/14.
 */
public class WeatherBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //You can do the processing here update the widget/remote views.
        Log.e("Error","WeatherBroadcast Entered");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_my);
        String weather = getWeather(context);
        remoteViews.setTextViewText(R.id.weather,weather);

        ComponentName thiswidget = new ComponentName(context, MyActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);
    }

    private String getWeather(Context context){

        GPSTracker gpsTracker = new GPSTracker(context);

        final Double longitude = gpsTracker.getLongitude();
        final Double lattitude = gpsTracker.getLatitude();

        final String weatherURL = "http://api.openweathermap.org/data/2.5/weather?";
        final String APIKEY  = "APPID=76c8832dd3db27a491f30b597fefb3c0";

        new Thread(new Runnable() {
            @Override
            public void run() {
                String beaconName ="";
                HttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(weatherURL+"lat="+lattitude.toString()+"&lon="+longitude.toString()+"&"+APIKEY);
                StringBuilder builder =new StringBuilder();
                try {
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } else {
                        Log.e("ERROR IN READING DATA", "FAILED TO GET ANY DATA");
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    Log.e("Json Data",jsonObject.getString("main"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return null;
    }
}
