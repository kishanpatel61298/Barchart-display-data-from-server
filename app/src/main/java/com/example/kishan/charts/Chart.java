package com.example.kishan.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Objects;


public class Chart extends Fragment {

    PieChart pieChart;
    ArrayList<String> name;
    ArrayList<String> value;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        pieChart = view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
       // pieChart.setExtraOffsets(-10,-10,-10,-10);


        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        name = MainActivity.name;
        value = MainActivity.value;

        if (null != value) {

            ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
            Float[] a = new Float[value.size()];
            for (int i = 0; i < name.size(); i++) {
                a[i] = Float.parseFloat(value.get(i));
            }
            for (int i = 0; i < name.size(); i++) {
                yVals1.add(new PieEntry(a[i], name.get(i)));
            }

            pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

            PieDataSet dataSet = new PieDataSet(yVals1, "States");
            dataSet.setSliceSpace(0f);
            dataSet.setSelectionShift(6f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData((dataSet));
            data.setValueTextSize(20f);
            data.setValueTextColor(Color.rgb(125, 206, 160));

            pieChart.setData(data);
        }
        return view;

    }




}
