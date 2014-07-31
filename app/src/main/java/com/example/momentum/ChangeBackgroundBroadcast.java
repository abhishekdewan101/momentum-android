package com.example.momentum;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by a.dewan on 7/30/14.
 */
public class ChangeBackgroundBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //You can do the processing here update the widget/remote views.
        Log.e("Error","ChangeBackgroundBroadcast");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_my);
        int backgroundFile = new Random().nextInt((10));
        //int backgroundFile = 1;
        Log.e("Background File", backgroundFile + "");
        ArrayList<Integer> backgroundImages = new ArrayList<Integer>();
        backgroundImages.add(R.drawable.pic1);
        backgroundImages.add(R.drawable.pic2);
        backgroundImages.add(R.drawable.pic3);
        backgroundImages.add(R.drawable.pic4);
        backgroundImages.add(R.drawable.pic5);
        backgroundImages.add(R.drawable.pic6);
        backgroundImages.add(R.drawable.pic7);
        backgroundImages.add(R.drawable.pic8);
        backgroundImages.add(R.drawable.pic9);
        backgroundImages.add(R.drawable.pic10);

        remoteViews.setImageViewResource(R.id.background,backgroundImages.get(backgroundFile));
        ComponentName thiswidget = new ComponentName(context, MyActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);
    }
}
