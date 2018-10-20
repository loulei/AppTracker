package com.example.apptracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HideHook {

	public void hide(LoadPackageParam lpp){
		XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpp.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				List<ApplicationInfo> result = (List<ApplicationInfo>) param.getResult();
				List<ApplicationInfo> list = new ArrayList<>();
				Iterator<ApplicationInfo> iterator = result.iterator();
				while(iterator.hasNext()){
					ApplicationInfo info = iterator.next();
					String packageName = info.packageName;
					if(isHide(packageName)){
						continue;
					}
					list.add(info);
				}
				param.setResult(list);
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpp.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				List<PackageInfo> result = (List<PackageInfo>) param.getResult();
				List<PackageInfo> list = new ArrayList<>();
				Iterator<PackageInfo> iterator = result.iterator();
				while(iterator.hasNext()){
					PackageInfo info = iterator.next();
					String packageName = info.packageName;
					if(isHide(packageName)){
						continue;
					}
					list.add(info);
				}
				param.setResult(list);
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpp.classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String pacageName = (String) param.args[0];
				if(isHide(pacageName)){
					param.args[0] = "com.tencent.mm";
				}
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", lpp.classLoader, "getApplicationInfo", String.class, int.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String pacageName = (String) param.args[0];
				if(isHide(pacageName)){
					param.args[0] = "com.tencent.mm";
				}
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpp.classLoader, "getRunningServices", int.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				List<RunningServiceInfo> result = (List<RunningServiceInfo>) param.getResult();
				List<RunningServiceInfo> list = new ArrayList<>();
				Iterator<RunningServiceInfo> iterator = result.iterator();
				while(iterator.hasNext()){
					RunningServiceInfo info = iterator.next();
					String packageName = info.process;
					if(isHide(packageName)){
						continue;
					}
					list.add(info);
				}
				param.setResult(list);
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpp.classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				List<RunningTaskInfo> result = (List<RunningTaskInfo>) param.getResult();
				List<RunningTaskInfo> list = new ArrayList<>();
				Iterator<RunningTaskInfo> iterator = result.iterator();
				while(iterator.hasNext()){
					RunningTaskInfo info = iterator.next();
					String packageName = info.baseActivity.flattenToString();
					if(isHide(packageName)){
						continue;
					}
					list.add(info);
				}
				param.setResult(list);
			}
		});
		
		XposedHelpers.findAndHookMethod("android.app.ActivityManager", lpp.classLoader, "getRunningAppProcesses", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				List<RunningAppProcessInfo> result = (List<RunningAppProcessInfo>) param.getResult();
				List<RunningAppProcessInfo> list = new ArrayList<>();
				Iterator<RunningAppProcessInfo> iterator = result.iterator();
				while(iterator.hasNext()){
					RunningAppProcessInfo info = iterator.next();
					String packageName = info.processName;
					if(isHide(packageName)){
						continue;
					}
					list.add(info);
				}
				param.setResult(list);
			}
		});
	}
	
	private boolean isHide(String packageName){
		return packageName.contains("com.example.apptracker") || packageName.contains("xposed");
	}
}




























