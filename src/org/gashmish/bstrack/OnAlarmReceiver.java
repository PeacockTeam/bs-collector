package org.gashmish.bstrack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class OnAlarmReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			Log.i("OnAlarmReceiver", "onReceive()");
			
			BufferedWriter bufferedWriter = getLogFileWriter();
			if (bufferedWriter == null) {
				Log.e("OnAlarmReceiver", "failed to open log file");
				return;
			}
			
			TelephonyManager telephonyManager =
				(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
								
			GsmCellLocation gsmCellLocation =
				(GsmCellLocation) telephonyManager.getCellLocation();

			try {
				
				if (gsmCellLocation != null) {
					bufferedWriter.write(
						(new Date()).toLocaleString() + "," +
						gsmCellLocation.getLac() + "," +
						gsmCellLocation.getCid() + "\n");
				} else {
					bufferedWriter.write(
						(new Date()).toLocaleString() + "," +
						"0" + "," +
						"0" + "\n");
				}

				bufferedWriter.close();

			} catch (IOException e) {
				Log.i("OnAlarmReceiver", "failed to write to file");
			}
		}
		  
		protected BufferedWriter getLogFileWriter() {
			String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
				File.separator + "bs_track";

			String filePath = baseDir + File.separator + "bs_dump_appended.csv";

			try {
				(new File(baseDir)).mkdirs();
				FileWriter fstream = new FileWriter(filePath, true);
				return new BufferedWriter(fstream);
			} catch (Exception e) {
				return null;
			}
		}
	}