package com.drmidnight.ucscdiningv3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class helpfeedbackfragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_helpfeedbackfragment,container,false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Help and Feedback");

        return v;
    }
}