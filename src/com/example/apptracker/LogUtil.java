package com.example.apptracker;

import android.util.Log;

public class LogUtil {
	private static int LOG_MAXLENGTH = 2000;
	
	private static final String LOG_TAG = "APPTRACE";

    public static void log(String msg) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                	Log.d(LOG_TAG, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                	Log.d(LOG_TAG, msg.substring(start, strLength));
                    break;
                }
        }
    }
}
