package com.fitnesshourglass.android.CountDown;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fitnesshourglass.android.R;

public class CountDownFrament extends Fragment {

    private TextView timeNowTextView;

    private Button StartTestButton;

    private Button PauseTestButton;

    private Button RestartTestButton;

    private Timer timer;

    class Timer extends MyCountdownTimer{

        private long mMillisInFuture;

        public Timer(long millisInFuture, long countdownInterval) {
            super(millisInFuture, countdownInterval);
            mMillisInFuture =millisInFuture;
        }

        @Override
        public void onTick(long millisUntilFinished, int percent) {
            timeNowTextView.setText(millisUntilFinished/1000 + "秒");
        }

        @Override
        public void onFinish() {
            timeNowTextView.setText("结束");
        }

        @Override
        public void onRestart() {
            timeNowTextView.setText(mMillisInFuture/1000 + "秒");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_countdown,container,false);
        timeNowTextView = (TextView) view.findViewById(R.id.text_timenow);
        StartTestButton = (Button) view.findViewById(R.id.button_start_test);
        RestartTestButton = (Button) view.findViewById(R.id.button_start_restart_test);
        PauseTestButton = (Button) view.findViewById(R.id.button_pause_test);
        timer = new Timer(60000,1000);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StartTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
            }
        });
        RestartTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.restart();
            }
        });
        PauseTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pause();
            }
        });
    }
}
