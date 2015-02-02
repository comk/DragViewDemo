package com.example.dragviewdemo;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Timer;
import java.util.TimerTask;

import com.example.camerademo.CustomService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ServiceDemon extends CustomService {

	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			restart();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void restart(){
		startService(new Intent(this, NotificationService.class));
	}
	
	@Override
    public void onTaskRemoved(Intent rootIntent) {  
        // TODO Auto-generated method stub  
		
		handler.sendEmptyMessageDelayed(0x999, 10);
		
		Log.e("onTaskRemoved", "Notification 服务：onTaskRemoved"+rootIntent.getAction());
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					Looper.prepare();
					handler.sendEmptyMessageDelayed(0x999, 1000);
					Looper.loop();
					Log.e("Thread ", " === ");
				}
			}
			
		});
		
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				restart();
				Log.e("Thread uncaughtException", " === ");
			}
		});
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		Log.e("onTaskRemoved", " Demon 服务：onTaskRemoved"+rootIntent.getAction());  
        super.onTaskRemoved(rootIntent);  
//        Intent intent = new Intent(this, TransparentActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
    }
	
	@Override
	public void onCreate() {
		Log.e("I'm Service B", "Demon I'm created !");
//		new Timer(true).schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//			}
//		}, 100, 10);
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		startService(new Intent(getApplicationContext(), NotificationService.class));
//		sendBroadcast(new Intent(NotificationService.Action_Alarm));
//		Log.e("I'm Service B", "Demon I'm working !");
		return START_STICKY_COMPATIBILITY;
	}

}
