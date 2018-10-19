package com.example.apptracker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class UIHook {
	
	
	public void startHook(XC_LoadPackage.LoadPackageParam lpp){
		hookActivity(lpp);
		hookFragment(lpp);
		hookV4Frament(lpp);
	}
	

	private void hookActivity(XC_LoadPackage.LoadPackageParam lpp){
		XposedHelpers.findAndHookMethod("android.app.Activity", lpp.classLoader, "onResume", new XC_MethodHook() {
			
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				Activity activity = (Activity) param.thisObject;
				Intent intent = activity.getIntent();
				ComponentName componentName = intent.getComponent();
				String classname = componentName.getClassName();
				String packagename = componentName.getPackageName();
				printActivityMessage(classname, packagename);
			}
			
		});
	}
	
	private void hookFragment(XC_LoadPackage.LoadPackageParam lpp){
		XposedHelpers.findAndHookMethod("android.app.Fragment", lpp.classLoader, "onResume", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String fragmentName = param.thisObject.getClass().getName();
				printFragmentMessage(fragmentName);
			}
		});
	}
	
	private void hookV4Frament(XC_LoadPackage.LoadPackageParam lpp){
		XposedHelpers.findAndHookMethod("android.support.v4.app.Fragment", lpp.classLoader, "onResume", new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				// TODO Auto-generated method stub
				String fragmentName = param.thisObject.getClass().getName();
				printFragmentMessage(fragmentName);
			}
		});
	}
	
	
	private void printActivityMessage(String classname, String packagename){
		LogUtil.log("activity onResume: " + classname + ", from " + packagename);
	}
	
	private void printFragmentMessage(String fragmentName){
		LogUtil.log("fragment onResume: " + fragmentName);
	}
	
}
