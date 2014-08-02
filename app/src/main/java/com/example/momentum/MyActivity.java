package com.example.momentum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class MyActivity extends AppWidgetProvider {
    private static final String PREF_NAME = "momentum_file";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        ComponentName componentName = new ComponentName(context,MyActivity.class);
        int [] allWidgetIds =appWidgetManager.getAppWidgetIds(componentName);
        for(int widgetId:allWidgetIds){
            int number = (new Random().nextInt(100));

            SharedPreferences settings = context.getSharedPreferences(PREF_NAME,0);
            String userGoal = settings.getString("goal",null);

            if(userGoal == null){
                Intent dailyIntent = new Intent(context,Daily.class);
                dailyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(dailyIntent);
            }

            AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent broadcastIntent = new Intent(context, AlarmBroadcast.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, broadcastIntent, 0);

            Intent backgroundIntent = new Intent(context, ChangeBackgroundBroadcast.class);
            PendingIntent backgroundPI = PendingIntent.getBroadcast(context, 0, backgroundIntent, 0);

            Intent weatherIntent = new Intent(context, WeatherBroadcast.class);
            PendingIntent weatherPI = PendingIntent.getBroadcast(context, 0, weatherIntent, 0);


            Intent dailyIntent = new Intent(context,Daily.class);
            PendingIntent dailyPI = PendingIntent.getActivity(context,0,dailyIntent,0);
            Date currentDate = Calendar.getInstance().getTime();
            Date wantedDate = null;
            Date alarmDate = null;
            try {
                wantedDate = new SimpleDateFormat("HH:mm").parse("08:00 AM");
                alarmDate = new SimpleDateFormat("HH:mm").parse(Integer.toString(currentDate.getHours())+":"+Integer.toString(currentDate.getMinutes()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long differenceTime = wantedDate.getTime() - currentDate.getTime();
            long alarmDifference = alarmDate.getTime() - currentDate.getTime();

            //Log.e("Alarm Differnce",differenceTime+"");

            am.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis() + alarmDifference, (1000 * 60 * 1), pi);
            am.setRepeating(AlarmManager.RTC,Calendar.getInstance().getTimeInMillis(),(1000*60*60),backgroundPI);
            am.setRepeating(AlarmManager.RTC,Calendar.getInstance().getTimeInMillis(),(1000*60*60*24),dailyPI);
            am.setRepeating(AlarmManager.RTC,Calendar.getInstance().getTimeInMillis() + alarmDifference,(1000*60*1),weatherPI);


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.activity_my);
            remoteViews.setImageViewResource(R.id.background,R.drawable.pic1);
            remoteViews.setTextViewText(R.id.time, new SimpleDateFormat("hh:mm").format(Calendar.getInstance().getTime()));
            remoteViews.setOnClickPendingIntent(R.id.qoutation,dailyPI);
            remoteViews.setOnClickPendingIntent(R.id.name,dailyPI);
            Intent intent = new Intent(context,MyActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId,remoteViews);
        }
    }
}
