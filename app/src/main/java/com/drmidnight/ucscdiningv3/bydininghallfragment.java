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
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialog;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.TimeZone;


public class bydininghallfragment extends android.support.v4.app.Fragment implements CalendarDatePickerDialog.OnDateSetListener {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 5;


    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    Calendar c = Calendar.getInstance(TimeZone.getDefault());
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH) + 1;
    int startDay = c.get(Calendar.DAY_OF_MONTH);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        final View x = inflater.inflate(R.layout.fragment_bydininghallfragment, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs2);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager2);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("By Dining Hall");

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        //FAB setup
        FloatingActionButton fab2 = (FloatingActionButton) x.findViewById(R.id.fab_normal2);
        Drawable fabicon2 = MaterialDrawableBuilder.with(getActivity()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.CALENDAR) // provide an icon
                .setColor(Color.WHITE) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        fab2.setImageDrawable(fabicon2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DateTime now = DateTime.now();
                CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog
                        .newInstance(bydininghallfragment.this, ((MainActivity) getActivity()).startYearint, ((MainActivity) getActivity()).startMonthint - 1,
                                ((MainActivity) getActivity()).startDayint);
                calendarDatePickerDialog.show(fm, FRAG_TAG_DATE_PICKER);


                /*Snackbar.make(x.findViewById(R.id.main_content2), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();*/
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ByDiningCMFragment();
                case 1:
                    return new ByDiningCSFragment();
                case 2:
                    return new ByDiningEOFragment();
                case 3:
                    return new ByDiningNTFragment();
                case 4:
                    return new ByDiningPKFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Crown\nMerrill";
                case 1:
                    return "Cowell\nStevenson";
                case 2:
                    return "Eight\nOakes";
                case 3:
                    return "Nine\nTen";
                case 4:
                    return "Porter\nKresge";
            }
            return null;
        }
    }


    @Override
    public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear+1;
        Toast.makeText(getActivity(), "Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth, Toast.LENGTH_LONG).show();
        ((MainActivity)getActivity()).ChangeDate(monthOfYear, dayOfMonth, year);
        System.out.println("Year: " + year + "\nMonth: " + monthOfYear + "\nDay: " + dayOfMonth);
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
