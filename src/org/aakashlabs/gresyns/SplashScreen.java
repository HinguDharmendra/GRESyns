package org.aakashlabs.gresyns;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity implements Runnable{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				finish();
	    		Intent mainActivity =new Intent(getApplicationContext(), MainActivity.class);
				startActivity(mainActivity);
			}
		};
		Timer timer =new Timer();
		timer.schedule(task, 3000);
		
		Thread t = new Thread(this);
		t.start();
	}		

    public void run() {
		
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Intent dbInitService = new Intent(this, DBInit.class);
		startService(dbInitService);
    } 

}
