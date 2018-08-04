package com.fitnesshourglass.android.Timing;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesshourglass.android.R;

import java.util.ArrayList;
import java.util.List;

public class TimingFragment extends Fragment {

    private WheelView wheelView_min;

    private WheelView wheelView_second;

    private List<String> dataSources;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_timing,container,false);
        wheelView_min = view.findViewById(R.id.wheelview_min);
        wheelView_min.setTextSize(180);
        wheelView_min.setTextVerticalSpacing(1);
        wheelView_min.setVisibilityCount(5);
        wheelView_min.setTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        wheelView_min.setSelectedTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        wheelView_second = view.findViewById(R.id.wheelview_second);
        wheelView_second.setTextSize(180);
        wheelView_second.setVisibilityCount(5);
        wheelView_second.setTextGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        wheelView_second.setSelectedTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        dataSources = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if(i<10){
                dataSources.add("0"+i);
            }
            else {
                dataSources.add(""+i);
            }
        }
        wheelView_second.setDataSources(dataSources);
        wheelView_min.setDataSources(dataSources);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wheelView_min.setCallBack(new WheelView.CallBack() {
            @Override
            public void onPositionSelect(int position) {
               // Log.d("RUSS","time is " + position);
            }
        });
    }
}
