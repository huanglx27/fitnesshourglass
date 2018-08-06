package com.fitnesshourglass.android.CountDown;

import android.os.Message;

/**
 *在Andorid SDK中CountdownTimer的基础上修改
*/
public abstract class MyCountdownTimer {

    private final long mCountdownInterval;

    private long mTotalTime;

    private long mRemainTime;

    private static final int MSG_RUN = 1;

    private static final int MSG_PAUSE = 2;

    public MyCountdownTimer(long millisInFuture,long countdownInterval){

        mRemainTime = millisInFuture;

        mTotalTime = millisInFuture;

        mCountdownInterval = countdownInterval;
    }

    public long getmRemainTime() {
        return mRemainTime;
    }

    public final void restart(){
        synchronized (MyCountdownTimer.this){
            mRemainTime = mTotalTime;
        }
    }

    public final void cancel(){
        handler.removeMessages(MSG_RUN);
        handler.removeMessages(MSG_PAUSE);
    }

    /*public final void resume(){
        handler.removeMessages(MSG_PAUSE);
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(MSG_RUN));
    }*/

    public final void pause(){
        handler.removeMessages(MSG_RUN);
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(MSG_PAUSE));
    }

    public synchronized final MyCountdownTimer start(){
        if (mRemainTime <= 0){
            onFinish();
            return this;
        }
        handler.sendMessageDelayed(handler.obtainMessage(MSG_RUN),mCountdownInterval);
        return this;
    }

    public abstract void onTick(long millisUntilFinished,int percent);

    public abstract void onFinish();

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            synchronized (MyCountdownTimer.this){
                if (msg.what == MSG_RUN){
                    mRemainTime = mRemainTime - mCountdownInterval;
                    if (mRemainTime <= 0){
                        onFinish();
                    }else if (mRemainTime < mCountdownInterval){
                        sendMessageDelayed(obtainMessage(MSG_RUN),mRemainTime);
                    }else {
                        onTick(mRemainTime,new Long(100*(mTotalTime - mRemainTime)/mTotalTime).intValue());
                        sendMessageDelayed(obtainMessage(MSG_RUN),mCountdownInterval);
                    }
                }else if (msg.what == MSG_PAUSE){

                }
            }
        }
    };
}
