package com.example.apptracker;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		// TODO Auto-generated method stub
		String packagename = lpparam.packageName;
		LogUtil.log("App Launch:"+packagename);
		UIHook uiHook = new UIHook();
		uiHook.startHook(lpparam);
		
		HttpHook httpHook = new HttpHook();
		httpHook.startHook(lpparam);
	}

}
