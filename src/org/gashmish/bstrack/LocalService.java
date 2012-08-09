package org.gashmish.bstrack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "onStartCommand()");
        
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, OnAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        
        alarmManager.setRepeating(
        	AlarmManager.RTC_WAKEUP,
        	System.currentTimeMillis(),
            10000,
            pi);
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("LocalService", "onDestroy()");
        
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, OnAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, i, 0);        
        alarmManager.cancel(sender);
    }

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
    @Override
    public void onCreate() {
    	super.onCreate();
    }
}

