package com.fitnesshourglass.android.CountDown;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.fitnesshourglass.android.MainActivity;
import com.fitnesshourglass.android.R;
import com.fitnesshourglass.android.Timing.TimeSetted;

public class CountdownService extends Service {

    private Handler handler;

    public static final int UPDATE_TIME = 1;

    public static final int UPDATE_GROUP = 2;

    private CountdownTask countdownTask;

    private TimeSetted countdownTimeSetted;

    private CountdownListener listener = new CountdownListener() {
        @Override
        public void onProgress(long progress) {
            getNotificationManager().notify(1,getNotification("计时器",progress));
            Message message = new Message();
            message.what = UPDATE_TIME;
            message.arg1 = (int) (progress/TimeSetted.SECOND_TO_MILL) / 60;
            message.arg2 = (int) (progress/TimeSetted.SECOND_TO_MILL) % 60;
            handler = new Handler();
            handler.sendMessage(message);
        }

        @Override
        public void onEnd() {
            /**
             * 可能countdownTask = null 这句不需要
             */
            countdownTask = null;
            //计时结束时将前台服务通知关闭，并创建计时结束的通知
            stopForeground(true);
            /**
             * 计划修改为一直弹出的通知；
             * 加入自定义布局，一个hutton和相关逻辑
             */
            getNotificationManager().notify(1,getNotification("计时结束",-1));
        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onCanceled() {
            countdownTask = null;
            stopForeground(true);
        }
    };

    public CountdownService() {
    }

    class CountdownBinder extends Binder{

        public void startCountdown(TimeSetted timeSetted){
            if (countdownTask == null){
                countdownTimeSetted = timeSetted;
                countdownTask = new CountdownTask(listener);
                countdownTask.execute(countdownTimeSetted);
                startForeground(1,getNotification("计时器",countdownTimeSetted.getTotalMill()));
            }else {
                countdownTask.continueCountdown();
            }
        }

        public void pauseCountdown(){
            if (countdownTask != null){
                countdownTask.pauseCountdown();
            }
        }

        public void cancelCountdown(){
            if (countdownTask != null){
                countdownTask.canceledCountdown();
            }
        }
    }

    private CountdownBinder mBinder = new CountdownBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,long progress){

        NotificationCompat.Builder builder = null;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myChannal","whatever", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this,"myChannal");
        }else {
            builder = new NotificationCompat.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentIntent(pi);
        if (progress > 0){
            int remainMin =(int) (progress/TimeSetted.SECOND_TO_MILL) / 60;
            if (remainMin > 0 ){
                builder.setContentText("还剩" + remainMin + "分钟");
            }else {
                builder.setContentText("还剩不到1分钟");
                builder.setAutoCancel(true);
            }
        }
        return builder.build();
    }

}
