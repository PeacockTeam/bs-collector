package org.gashmish.bstrack;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate()");

        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.start);
        button.setOnClickListener(mStartListener);
        button = (Button)findViewById(R.id.stop);
        button.setOnClickListener(mStopListener);
        
    	updateLabel();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Log.i("MainActivity", "onStart()");        
    	updateLabel();
    }

    private OnClickListener mStartListener = new OnClickListener() {
        public void onClick(View v) {
            startService(new Intent(MainActivity.this, LocalService.class));
            updateLabel();
        }
    };

    private OnClickListener mStopListener = new OnClickListener() {
        public void onClick(View v) {
            stopService(new Intent(MainActivity.this, LocalService.class));
            updateLabel();
        }
    };
    
    private void updateLabel() {
    	TextView textView = (TextView) findViewById(R.id.serviceActivityLabel);
    	if (isLocalServiceRunning()) {
    		textView.setText("Service is running");
    	} else {
    		textView.setText("Service is off");
    	}
    }
    
    private boolean isLocalServiceRunning() {
        final ActivityManager activityManager =
        	(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        
        final List<RunningServiceInfo> services =
        	activityManager.getRunningServices(Integer.MAX_VALUE);

        for (RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(LocalService.class.getName())){
                return true;
            }
        }
        return false;
     }
}
