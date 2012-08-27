package org.gashmish.bstrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupIntentReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("StartupIntentReceiver", "onReceive()");
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("org.gashmish.bstrack.LocalService");
		context.startService(serviceIntent);
	}
}
