package com.drmidnight.ucscdiningv3;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;


public class ByMealAdapter extends RecyclerView.Adapter<ByMealAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<MainActivity.Menudetail> mMenuTitles;

    private String name;
    //private int dininghallpic;
    private String hours;
    //private String meal;
    Context context;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        LinearLayout LinearLayoutView;
        //ImageView imageView;
        //ImageView profile;
        TextView Name;
        TextView Hours;
        Context contxt;


        public ViewHolder(View itemView, int ViewType, Context c) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
//                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                LinearLayoutView = (LinearLayout) itemView.findViewById(R.id.dining_itemrow_linearlayout);
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            } else {

                Name = (TextView) itemView.findViewById(R.id.bymeal_header_name);         // Creating Text View object from header.xml for name
                Hours = (TextView) itemView.findViewById(R.id.bymeal_header_hours);       // Creating Text View object from header.xml for email
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }

        }

        @Override
        public void onClick(View v) {

            Toast.makeText(contxt, "The Item Clicked is: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

        }
    }

    ByMealAdapter(List<MainActivity.Menudetail> Titles, String Name, String Hours, Context passedContext) { // MyAdapter Constructor with titles and icons parameter
        mMenuTitles = Titles;                //have seen earlier
        name = Name;
        hours = Hours;
        //meal = Meal;
        //dininghallpic = Dininghallpic;
        this.context = passedContext;

    }

    @Override
    public ByMealAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dining_itemrow, parent, false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bymeal_header, parent, false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ByMealAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            if (mMenuTitles != null) {
                MainActivity.Menudetail element = null;
                int counter = 0;
                for (Iterator<MainActivity.Menudetail> iter = mMenuTitles.iterator(); iter.hasNext(); ) {
                    element = iter.next();
                    counter++;
                    if (counter == position) break;
                }
                if (element != null) {
                    if (element.getName().contains("&apos;")) {
                        element.name = element.name.replace("&apos;", "'");
                    }
                    if (element.getName().contains("&amp;")) {
                        element.name = element.name.replace("&amp;", "&");
                    }
                    if (element.getName().contains("&quot;")) {
                        element.name = element.name.replace("&quot;", "\"");
                    }

                    holder.textView.setText(element.getName());

                    //if (element != null) {
                    if (element.getName().equals("Farm Fridays")) {
                        holder.textView.setTypeface(null, Typeface.BOLD);
                        holder.textView.setTextColor(Color.RED);
                        ////holder.LinearLayoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.dining_greensubbar));
                    }
                    if (element.getName().equals("Healthy Mondays")) {
                        holder.textView.setTypeface(null, Typeface.BOLD);
                        holder.textView.setTextColor(Color.RED);
                        ////holder.LinearLayoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.dining_greensubbar));
                    }
                    if (element.getName().contains("BAR") || element.getName().contains("Bar ") || element.getName().endsWith("Bar")) {
                        //holder.textView.setTextColor(Color.YELLOW);
                        //TextView questionValue = (TextView) findViewById(R.layout.TextView01);
                        holder.textView.setTypeface(null, Typeface.BOLD);
                    }
                    //}

                }
            }
            ////holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
            //holder.imageView.setImageDrawable(context.getResources().getDrawable(mIcons[position - 1]));// Settimg the image with array of our icons
        } else {

            //holder.dininghallpic.setImageResource(dininghallpic);           // Similarly we set the resources for header view
            holder.Name.setText(name);
            holder.Hours.setText(hours);
            //dininghallpic = Dininghallpic;
        }
    }

    @Override
    public int getItemCount() {
        int counter = 0;
        if (mMenuTitles != null) {
            if (!mMenuTitles.isEmpty()) {
/*            MainActivity.Menudetail element = null;
            for (Iterator<MainActivity.Menudetail> iter2 = mMenuTitles.iterator(); iter2.hasNext(); ) {
                element = iter2.next();
                counter++;
            }*/

                counter = mMenuTitles.size() + 1;
            }
        }
        //System.out.println("Counter: " + counter);
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
