package com.example.dragviewdemo;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Timer;
import java.util.TimerTask;

import com.example.camerademo.CustomService;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

public class NotificationService extends CustomService {
	private final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			restart();
			
		}
	};
	
	public static final String Action_Alarm = "ACTION_HEARTBEAT";
	
	public static class BroadCastDemo extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e("BroadcastReceiver ", "Action Received " + intent.getAction());
//			context.startService(new Intent(context, NotificationService.class));
//			AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//			Intent intent2 = new Intent(Action_Alarm);
//			intent.setFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
//			PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, 0x111,intent2 , PendingIntent.FLAG_UPDATE_CURRENT);
//	        // 启动心跳定时器
//	        long triggerAtTime = SystemClock.elapsedRealtime() + 1000;
//	        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//	        		triggerAtTime, 1000, mPendingIntent);
		}

	}
	
	
	private void restart(){
		Intent intent = new Intent(NotificationService.this, TransparentActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		
		
		Log.e("startActivity", " ------- ");
		
//		Intent restartServiceIntent = new Intent(getApplicationContext(),
//	            this.getClass());
//	    restartServiceIntent.setPackage(getPackageName());
//		PendingIntent restartServicePendingIntent = PendingIntent.getService(
//	            getApplicationContext(), 1, restartServiceIntent,
//	            PendingIntent.FLAG_UPDATE_CURRENT);
//	    AlarmManager alarmService = (AlarmManager) getApplicationContext()
//	            .getSystemService(Context.ALARM_SERVICE);
//	    
//	    alarmService.setRepeating(AlarmManager.ELAPSED_REALTIME,
//	            SystemClock.elapsedRealtime() + 10,1,
//	            restartServicePendingIntent);
		
	}
	
	@Override
    public void onTaskRemoved(Intent rootIntent) {  
		
		
//		Intent restartServiceIntent = new Intent(getApplicationContext(),
//	            this.getClass());
//	    restartServiceIntent.setPackage(getPackageName());
//
//	    PendingIntent restartServicePendingIntent = PendingIntent.getService(
//	            getApplicationContext(), 1, restartServiceIntent,
//	            PendingIntent.FLAG_ONE_SHOT);
//	    AlarmManager alarmService = (AlarmManager) getApplicationContext()
//	            .getSystemService(Context.ALARM_SERVICE);
//	    alarmService.set(AlarmManager.ELAPSED_REALTIME,
//	            SystemClock.elapsedRealtime() + 2000,
//	            restartServicePendingIntent);
//	    wl.acquire(2000);
        // TODO Auto-generated method stub  
		
		
		/*
		
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
		t.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				restart();
				Log.e("Thread default uncaughtException", " === ");
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
		
		*/
		
//		restart();
//		mTimer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				Looper.prepare();
//				handler.sendEmptyMessageDelayed(0x999, 10);
//				Looper.loop();
//				Log.e("Timer ", " === ");
//			}
//		}, 10,1);
		super.onTaskRemoved(rootIntent);  
//		handler.sendEmptyMessageDelayed(0x999, 10);
//        startForeground(0x110, new Notification(0x909, "", System.currentTimeMillis()));
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext()).setMessage("message");
//        
//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//设定为系统级警告，关键
//        dialog.show();
    }  
	
	public static final aidlDemo.Stub mBinder = new aidlDemo.Stub() {
		
		@Override
		public void test() throws RemoteException {
			Log.e("aidl", "success");
			
			
			new Thread(new Runnable() {
                @Override
                public void run() {
                    //未达到线程条件，会一直在后台运行，就算服务已经关闭
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            Log.e("Stub ", "is working ....");
                            mBinder.joinThreadPool();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("onbind", "invoked ... ");
		return mBinder;
	}
	
	public static Timer mTimer;
	
	@Override
	public boolean onUnbind(Intent intent) {
		
		
		return super.onUnbind(intent);
	}
	
	public static final ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        	try {
				aidlDemo.Stub.asInterface(service).test();
				Log.e("mian stub",  " is working");
				
				
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        	Log.e("mian stub onServiceDisconnected",  " is working");
        }
    };
	
	@Override
	public void onCreate() {
//		Intent binderIntent = new Intent(this,	NotificationService.class);
//        bindService(binderIntent, sc, Context.BIND_AUTO_CREATE);
		Log.e("I'm Service ", "Notification I has been created !");
//		AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		Intent intent = new Intent(Action_Alarm);
//		intent.setFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
//		PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0x111,intent , PendingIntent.FLAG_UPDATE_CURRENT);
//        // 启动心跳定时器
//        long triggerAtTime = SystemClock.elapsedRealtime() + 2000;
//        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//        		triggerAtTime, 2000, mPendingIntent);
//				startService(new Intent(getApplicationContext(), ServiceDemon.class));
//        
//				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//				wl = pm.newWakeLock(0x108, "wake lock");
//		mTimer = new Timer(true);
//		new Timer(true).schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//			}
//		}, 100, 10);
		
		if(mTimer != null)
			mTimer.cancel();
		else
			mTimer = new Timer(true);
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		sendBroadcast(new Intent(Action_Alarm));
//		startService(new Intent(getApplicationContext(),ServiceDemon.class));
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// TODO Auto-generated method stub
//				Log.e("service ", "started run .... ");
//				NotificationCompat.Builder localBuilder;
//				localBuilder = new NotificationCompat.Builder(NotificationService.this).setContentTitle("Title").setContentText("Content").setAutoCancel(true).setWhen(System.currentTimeMillis()).setTicker("testS");
//				localBuilder.setLights(-16711936, 2000, 2000);qq设置的呼吸灯值
//				localBuilder.setDefaults(Notification.DEFAULT_ALL);
//				Notification noti = localBuilder.build();
//				NotificationManager mn = (NotificationManager) NotificationService.this.getSystemService(NOTIFICATION_SERVICE);
//				mn.notify(0x101, noti);
//				
//			}
//		}).start();
//		try{
//
//	         Log.e("FLAGX : ", "on StartCommand");
//	         Intent restartServiceIntent = new Intent(getApplicationContext(),
//	                 this.getClass());
//	         restartServiceIntent.setPackage(getPackageName());
//
//	         PendingIntent restartServicePendingIntent = PendingIntent.getService(
//	                 getApplicationContext(), 1, restartServiceIntent,
//	                 PendingIntent.FLAG_ONE_SHOT);
//	         AlarmManager alarmService = (AlarmManager) getApplicationContext()
//	                 .getSystemService(Context.ALARM_SERVICE);
//	         alarmService.set(AlarmManager.ELAPSED_REALTIME,
//	                 SystemClock.elapsedRealtime() + 1000,
//	                 restartServicePendingIntent);
//
//	         super.onTaskRemoved(intent);
//	     }catch(Exception e){;}
//		Log.e("I'm Service A", "I'm working !");
		
		return Service.START_STICKY_COMPATIBILITY;
	}
	
	@Override
	public void onDestroy() {
		Log.e("I'm Service ", "I has been destoryed !");
		super.onDestroy();
	}

}
