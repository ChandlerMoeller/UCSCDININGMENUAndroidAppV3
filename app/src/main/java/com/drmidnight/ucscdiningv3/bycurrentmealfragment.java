package com.drmidnight.ucscdiningv3;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialog;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.TimeZone;


public class bycurrentmealfragment extends android.support.v4.app.Fragment implements CalendarDatePickerDialog.OnDateSetListener {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    Calendar c = Calendar.getInstance(TimeZone.getDefault());
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH) + 1;
    int startDay = c.get(Calendar.DAY_OF_MONTH);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_bycurrentmealfragment, container, false);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(int_items);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("By Meal");
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth);
        //"Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        //
        //FAB setup
        FloatingActionButton fab = (FloatingActionButton) x.findViewById(R.id.fab_normal1);
        Drawable fabicon1 = MaterialDrawableBuilder.with(getActivity()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.CALENDAR) // provide an icon
                .setColor(Color.WHITE) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        fab.setImageDrawable(fabicon1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DateTime now = DateTime.now();
                CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog
                        .newInstance(bycurrentmealfragment.this, ((MainActivity) getActivity()).startYearint, ((MainActivity) getActivity()).startMonthint - 1,
                                ((MainActivity) getActivity()).startDayint);
                calendarDatePickerDialog.show(fm, FRAG_TAG_DATE_PICKER);


                /*Snackbar.make(x.findViewById(R.id.main_content), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();*/
            }
        });
        //FAB end
        //

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ByMealBreakfastFragment();
                case 1:
                    return new ByMealLunchFragment();
                case 2:
                    return new ByMealDinnerFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Breakfast";
                case 1:
                    return "Lunch";
                case 2:
                    return "Dinner";
            }
            return null;
        }
    }

/*    public static String POSITION = "POSITION";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }*/

    @Override
    public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear + 1;
        //Toast.makeText(getActivity(), "Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth, Toast.LENGTH_LONG).show();
        ((MainActivity) getActivity()).ChangeDate(monthOfYear, dayOfMonth, year);
    }

    public void bycurrentmealchangetitle(int year, int monthOfYear, int dayOfMonth) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(monthOfYear + "/" + dayOfMonth + "/" + year);
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialog calendarDatePickerDialog = (CalendarDatePickerDialog) getFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialog != null) {
            calendarDatePickerDialog.setOnDateSetListener(this);
        }
    }

}