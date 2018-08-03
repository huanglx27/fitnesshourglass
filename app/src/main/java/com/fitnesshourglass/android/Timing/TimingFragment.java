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

    private WheelView wheelView;

    private List<String> dataSources;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_timing,container,false);
        wheelView = view.findViewById(R.id.wheelview);
        wheelView.setTextSize(80);
        wheelView.setVisibilityCount(7);
        wheelView.setTextGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        wheelView.setSelectedTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        dataSources = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            dataSources.add("数据" + i);
        }
        wheelView.setDataSources(dataSources);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wheelView.setCallBack(new WheelView.CallBack() {
            @Override
            public void onPositionSelect(int position) {
                Log.d("RUSS","time is " + position);
            }
        });
    }
}
