package com.drmidnight.ucscdiningv3;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ByMealSUPERAdapter extends RecyclerView.Adapter<ByMealSUPERAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 0;
    private ArrayList<List<MainActivity.Menudetail>> mMenuTitles;

    //private int[] whichmenus;
    //private int[] ordermenus;
    ArrayList<String[]> mealrowtitles;
    private String date;
    //private String hourscurrentmeal;
    //private int dininghallpic;
    private String dayofweek;
    //private String meal;
    Context context;


    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    //String dininghallname = "Crown/Merrill";
    //String dininghallhours = "7:15am to 11:00am";
    //String diningmeal = "Breakfast";
    //int dininghallpic2 = R.drawable.ic_piazza;



    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        RecyclerView textView;
        //RelativeLayout relativeView;
        //ImageView imageView;
        //ImageView profile;
        TextView Name;
        TextView Hours;
        Context contxt;

        //RecyclerView mRecyclerView;
        //RecyclerView.Adapter mAdapter;


        public ViewHolder(View itemView,int ViewType,Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == TYPE_ITEM) {
                textView = (RecyclerView) itemView.findViewById(R.id.bymeal_mealrow_RecyclerViewDining); // Creating TextView object with the id of textView from item_row.xml
                //relativeView = (RelativeLayout) itemView.findViewById(R.id.bymeal_mealrow_RelativeLayout);
//                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row

            }
            else {

                Name = (TextView) itemView.findViewById(R.id.bymeal_super_header_name);         // Creating Text View object from header.xml for name
                Hours = (TextView) itemView.findViewById(R.id.bymeal_super_header_hours);       // Creating Text View object from header.xml for email
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(contxt, "The Item Clicked is: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

        }
    }

    ByMealSUPERAdapter(ArrayList<List<MainActivity.Menudetail>> Titles, ArrayList<String[]> Mealrowmeta, String Date, String Dayofweek, int Dininghallpic, Context passedContext){
        mMenuTitles = Titles;
        //whichmenus = Whichmenus;
        //ordermenus = Ordermenus;
        mealrowtitles = Mealrowmeta;
        date = Date;
        dayofweek = Dayofweek;
        //meal = Meal;
        //dininghallpic = Dininghallpic;
        this.context = passedContext;

    }

    @Override
    public ByMealSUPERAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bymeal_mealrow,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        }
        else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bymeal_super_header,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType,context); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ByMealSUPERAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            if (mMenuTitles != null) {
                List<MainActivity.Menudetail> element = null;
                int counter = 0;
                for (Iterator<List<MainActivity.Menudetail>> iter = mMenuTitles.iterator(); iter.hasNext(); ) {
                    element = iter.next();
                    counter++;
                    if (counter == position) break;
                }

                String dininghallname = mealrowtitles.get(position-1)[0];
                String hourscurrentmeal = mealrowtitles.get(position-1)[5];
                mAdapter = new ByMealAdapter(element, dininghallname, hourscurrentmeal, context);
                holder.textView.setAdapter(mAdapter);
                mLayoutManager = new LinearLayoutManager(context);                 // Creating a layout Manager
                holder.textView.setLayoutManager(mLayoutManager);

                int elementsize = 0;
                if (element != null) {
                    if (!element.isEmpty()) {
                        elementsize = element.size();
                    }
                }
                int h = 0;
                if (elementsize != 0) {
                    h = (elementsize * ((int) context.getResources().getDimension(R.dimen.item_height))) + ((int) context.getResources().getDimension(R.dimen.header_height));
                }
                holder.textView.getLayoutParams().height = h;


            }
            ////holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
            //holder.imageView.setImageDrawable(context.getResources().getDrawable(mIcons[position - 1]));// Settimg the image with array of our icons
        } else {

            //holder.dininghallpic.setImageResource(dininghallpic);           // Similarly we set the resources for header view
            holder.Name.setText(dayofweek);
            holder.Hours.setText(date);
            //dininghallpic = Dininghallpic;
        }
    }

    @Override
    public int getItemCount() {
        int counter;
        if (mMenuTitles != null) {
            counter = mMenuTitles.size() + 1;
        } else {
            counter =0;
        }
        return counter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
}
