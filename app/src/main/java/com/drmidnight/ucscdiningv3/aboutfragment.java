package com.drmidnight.ucscdiningv3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class aboutfragment extends android.support.v4.app.Fragment {

    private Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aboutfragment,container,false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");

        return v;
    }
}