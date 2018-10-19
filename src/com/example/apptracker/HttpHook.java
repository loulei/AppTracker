package com.example.apptracker;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.Build;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HttpHook {
	
	public void startHook(LoadPackageParam lpp){
		Class<?> httpUrlConnectionClazz = XposedHelpers.findClass(getHttpUrlConnection(), lpp.classLoader);
		if(httpUrlConnectionClazz == null){
			LogUtil.log("httpUrlConnection not found");
			return;
		}
		try {
			hookHttpUrlConnection(lpp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void hookHttpUrlConnection(LoadPackageParam lpp) throws Exception{
		XposedHelpers.findAndHookMethod(URL.class, "openConnection", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				URL url = (URL) param.thisObject;
				LogUtil.log("url:"+url.toString());
			}
		});
		
		XposedHelpers.findAndHookMethod(getHttpUrlConnection(), lpp.classLoader, "setRequestMethod", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String method = (String) param.args[0];
				LogUtil.log("method:"+method);
			}
		});
		
		XposedHelpers.findAndHookMethod(getHttpUrlConnection(), lpp.classLoader, "setRequestProperty", String.class, String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String key = (String) param.args[0];
				String value = (String) param.args[1];
				LogUtil.log("header:"+key+","+value);
			}
		});
		
		
	}

	private String getHttpUrlConnection(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			return "com.android.okhttp.internal.huc.HttpURLConnectionImpl";
		}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			return "com.android.okhttp.internal.http.HttpURLConnectionImpl";
		}else {
			return "libcore.net.http.HttpURLConnectionImpl";
		}
	}
}
