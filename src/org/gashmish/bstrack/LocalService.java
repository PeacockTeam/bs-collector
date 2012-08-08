/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gashmish.bstrack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class LocalService extends Service {

	private final Timer timer = new Timer();
	private TelephonyManager telephonyManager;
	private BufferedWriter bufferedWriter;
	
	public class MyTimerTask extends TimerTask {
		
		@Override
		public void run() {
			
			try {
				GsmCellLocation gsmCellLocation =
					(GsmCellLocation) telephonyManager.getCellLocation();
								
				if (gsmCellLocation != null) {
					bufferedWriter.write(
						(new Date()).toLocaleString() + "," + 
						gsmCellLocation.getLac() + "," + 
						gsmCellLocation.getCid() + "\n");
					
					bufferedWriter.flush();
				}
				
			} catch (IOException e) {
				Log.i("LocalService", "failed to write to file");
			}
		}
	}
	
    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "onStartCommand()");

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        bufferedWriter = createDumpFile();
        
        if (bufferedWriter != null && telephonyManager != null) {
            timer.scheduleAtFixedRate(new MyTimerTask(), 0, 20000);
        } else {
        	Log.e("LocalService", "failed to init service");
        }
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("LocalService", "onDestroy()");
        
        timer.cancel();
    	
        try {
			bufferedWriter.close();
		} catch (IOException e) {
			Log.e("LocalService", "failed to close file");
		}
    }

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("LocalService", "onBind()");
		return null;
	}
		
	protected BufferedWriter createDumpFile() {
		String baseDir =
			Environment.getExternalStorageDirectory().getAbsolutePath() +
			File.separator +
			"bs_track";
		
		String fullPath =
			baseDir +
			File.separator + 
			"bs_dump_appended.csv";
		
		try {
			
			(new File(baseDir)).mkdirs();
			FileWriter fstream = new FileWriter(fullPath, true);
			return new BufferedWriter(fstream);
			
		} catch (Exception e) {
			
			System.err.println("Error: " + e.getMessage());
			return null;
		}		
	}
}

