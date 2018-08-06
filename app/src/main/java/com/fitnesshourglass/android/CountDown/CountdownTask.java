package com.fitnesshourglass.android.CountDown;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.fitnesshourglass.android.MainActivity;
import com.fitnesshourglass.android.Timing.TimeSetted;

/**
 * 实现倒计时功能
 */

public class CountdownTask extends AsyncTask<TimeSetted,Long,Integer> {

    public static final int TYPE_FINISHED = 0;

    public static final int TYPE_PAUSED = 1;

    public static final int TYPE_CANCELED = 2;

    private CountdownListener listener;

    private boolean isCanceled = false;

    private boolean isPaused = false;

    private boolean isFinished = false;

    private long lastProgress;

    private Timer timer;

    public CountdownTask(CountdownListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(TimeSetted... timeSetteds) {
        TimeSetted timeSetted = timeSetteds[0];
        long millIsInFuture = timeSetted.getTotalMill();
        lastProgress = millIsInFuture;
        timer = new Timer(millIsInFuture,TimeSetted.SECOND_TO_MILL);
        while (getLastProgress() > 0){
            if (isCanceled){
                timer.cancel();
                return TYPE_CANCELED;
            }else if (isPaused){
                timer.pause();
                return TYPE_PAUSED;
            }else {
                timer.start();
                long progress = timer.getmMillisUntilFinished();
                publishProgress(progress);
            }
        }
        return TYPE_FINISHED;
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_FINISHED:
                listener.onEnd();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        long progress = values[0];
        listener.onProgress(progress);
        lastProgress = progress;
    }

    /**
     *提供给前端改变状态；
      */

    public void pauseCountdown(){
        isPaused = true;
    }

    public void continueCountdown(){
        isPaused = false;
    }

    public void canceledCountdown(){
        isCanceled = true;
    }


    /**
     * 传递lastProgress给Binder
     */
    public long getLastProgress(){
        return lastProgress;
    }

    class Timer extends MyCountdownTimer{

        private long mMillisUntilFinished;

        public Timer(long millisInFuture, long countdownInterval) {
            super(millisInFuture, countdownInterval);
            mMillisUntilFinished = millisInFuture;
        }

        @Override
        public void onTick(long millisUntilFinished, int percent) {
            setmMillisUntilFinished(millisUntilFinished);
        }

        @Override
        public void onFinish() {
        }

        private void setmMillisUntilFinished(long millisUntilFinished){
            mMillisUntilFinished = millisUntilFinished;
        }

        public long getmMillisUntilFinished() {
            return mMillisUntilFinished;
        }
    }
}
