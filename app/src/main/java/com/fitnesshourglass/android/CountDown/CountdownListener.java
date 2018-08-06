package com.fitnesshourglass.android.CountDown;

/**
 * 回调接口，对倒计时过程中的各种状态进行接听和回调
 */

public interface CountdownListener {

    void onProgress(long progress);//进度

    void onEnd();//计时结束

    void onPaused();//暂停

    void onCanceled();//取消倒计时

}
