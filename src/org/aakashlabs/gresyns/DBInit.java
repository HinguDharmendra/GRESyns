package org.aakashlabs.gresyns;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DBInit extends Service {
	DBManager db = new DBManager(this);
	
	public DBInit() {
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();		
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		db.open();
		onDestroy();
		return START_STICKY;


	}
	
	public void onDestroy()
	{
		db.close();
		stopSelf();
	}
}
