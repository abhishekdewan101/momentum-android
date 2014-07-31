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
import java.util.Calendar;

/**
 * Created by a.dewan on 7/30/14.
 */
public class AlarmBroadcast extends BroadcastReceiver {
    private static final String PREF_NAME = "momentum_file";
    @Override
    public void onReceive(Context context, Intent intent) {
        //You can do the processing here update the widget/remote views.

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_my);
        Calendar calendar = Calendar.getInstance();
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME,0);
        String userGoal = settings.getString("goal",null);
        remoteViews.setTextViewText(R.id.qoutation,userGoal);
        remoteViews.setTextViewText(R.id.time, new SimpleDateFormat("hh:mm").format(calendar.getTime()));
        String userName = settings.getString("name",null);

        if(calendar.getTime().getHours()<12){
         remoteViews.setTextViewText(R.id.name, "Good Morning,"+userName);
        }

        if(calendar.getTime().getHours()>12 &&calendar.getTime().getHours()<18 ){
            remoteViews.setTextViewText(R.id.name, "Good Afternoon,"+userName);
        }

        if(calendar.getTime().getHours()>18){
            remoteViews.setTextViewText(R.id.name, "Good Evening,"+userName);
        }

        ComponentName thiswidget = new ComponentName(context, MyActivity.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thiswidget, remoteViews);
    }
}
