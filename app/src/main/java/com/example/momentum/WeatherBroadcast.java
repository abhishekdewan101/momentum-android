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

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_my);
        String weather = getWeather(context);
        remoteViews.setTextViewText(R.id.weather,weather);

        ComponentName thiswidget = new ComponentName(context, MyActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);
    }

    private String getWeather(Context context){

        GPSTracker gpsTracker = new GPSTracker(context);

        if(gpsTracker.canGetLocation()){
            Log.e("Lattitude",gpsTracker.getLatitude()+"");
            Log.e("Longitude",gpsTracker.getLongitude()+"");
        }




        String weather = "";
//        String WEATHER_MANAGER ="http://api.worldweatheronline.com/free/v1/weather.ashx?";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String beaconName ="";
//                HttpClient client = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet("http://"+SpaceManager+"/beacons?major="+major+"&minor="+minor);
//                StringBuilder builder =new StringBuilder();
//                try {
//                    HttpResponse response = client.execute(httpGet);
//                    StatusLine statusLine = response.getStatusLine();
//                    int statusCode = statusLine.getStatusCode();
//                    if (statusCode == 200) {
//                        HttpEntity entity = response.getEntity();
//                        InputStream content = entity.getContent();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            builder.append(line);
//                        }
//                    } else {
//                        Log.e("ERROR IN READING DATA", "FAILED TO GET ANY DATA");
//                    }
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    JSONArray jsonArray = new JSONArray(builder.toString());
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                    beaconName = jsonObject.getString("name");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                try {
//                    HttpGet httpGet1 = new HttpGet("http://"+SpaceManager+"/users/setactive?uniqueid="+ URLEncoder.encode(USER_ID, "utf-8")+"&activebeacon="+URLEncoder.encode(beaconName,"utf-8"));
//                    HttpResponse response = client.execute(httpGet1);
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                    somethingWrong = true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    somethingWrong = true;
//                }
//            }
//        }).start();
//        somethingFound = true;
//    }else{
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpClient client = new DefaultHttpClient();
//                HttpGet httpGet1 = new HttpGet("http://"+SpaceManager+"/users/setpassive?uniqueid="+USER_ID);
//                try {
//                    HttpResponse response = client.execute(httpGet1);
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                    somethingWrong = true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    somethingWrong = true;
//                }
//            }
//        }).start();

        return weather;
    }
}
