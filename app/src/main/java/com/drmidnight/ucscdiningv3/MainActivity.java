package com.drmidnight.ucscdiningv3;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //////////////////////////////////////
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    //Default Date is current date
    Calendar c = Calendar.getInstance(TimeZone.getDefault());
    int startYearint = c.get(Calendar.YEAR);
    String startYear = "";
    int startMonthint = c.get(Calendar.MONTH) + 1;
    String startMonth = "";
    int startDayint = c.get(Calendar.DAY_OF_MONTH);
    String startDay = "";

    String date = "";
    int dayofweekint = c.get(Calendar.DAY_OF_WEEK);
    String dayofweek = Integer.toString(dayofweekint);

    //Breakfast variables
    int[] bmbarray = {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0};
    int[] bmborderarray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    ArrayList<List<Menudetail>> bmbCompleteDayMenu;
    ArrayList<String[]> bmbmealrowmeta;
    RecyclerView bmbmRecyclerView;
    //RelativeLayout bmbmRelativeLayout;
    RecyclerView.Adapter bmbmAdapter;
    RecyclerView.LayoutManager bmbmLayoutManager;

    //Lunch Variables
    int[] bmlarray = {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0};
    int[] bmlorderarray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    ArrayList<List<Menudetail>> bmlCompleteDayMenu;
    ArrayList<String[]> bmlmealrowmeta;
    RecyclerView bmlmRecyclerView;
    //RelativeLayout bmlmRelativeLayout;
    RecyclerView.Adapter bmlmAdapter;
    RecyclerView.LayoutManager bmlmLayoutManager;

    //Dinner Variables
    int[] bmdarray = {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1};
    int[] bmdorderarray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    ArrayList<List<Menudetail>> bmdCompleteDayMenu;
    ArrayList<String[]> bmdmealrowmeta;
    RecyclerView bmdmRecyclerView;
    //RelativeLayout bmdmRelativeLayout;
    RecyclerView.Adapter bmdmAdapter;
    RecyclerView.LayoutManager bmdmLayoutManager;

    ///String diningmeal = "Breakfast";
    int dininghallpic = R.drawable.ic_menu_camera;

    ArrayList<List<Menudetail>> CompleteDayMenu = new ArrayList<>();
    ///////////////////////////////////////

    bycurrentmealfragment bycurrentmealfragment = new bycurrentmealfragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SetMenuIcons();
        ChangeDate(startMonthint, startDayint, startYearint);

        //Loads bycurrentmeal as default option
        //bycurrentmealfragment bycurrentmealfragment = new bycurrentmealfragment();
        android.support.v4.app.FragmentTransaction bycurrentmealfragmentTransaction = getSupportFragmentManager().beginTransaction();
        bycurrentmealfragmentTransaction.replace(R.id.frame, bycurrentmealfragment);
        bycurrentmealfragmentTransaction.commit();


        navigationView.getMenu().findItem(R.id.bycurrentmeal).setChecked(true);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bycurrentmeal) {
            ChangeDate(startMonthint, startDayint, startYearint);
            //bycurrentmealfragment bycurrentmealfragment = new bycurrentmealfragment();
            android.support.v4.app.FragmentTransaction bycurrentmealfragmentTransaction = getSupportFragmentManager().beginTransaction();
            bycurrentmealfragmentTransaction.replace(R.id.frame, bycurrentmealfragment);
            bycurrentmealfragmentTransaction.commit();
        } else if (id == R.id.bydininghall) {
            bydininghallfragment bydininghallfragment = new bydininghallfragment();
            android.support.v4.app.FragmentTransaction bydininghallfragmentTransaction = getSupportFragmentManager().beginTransaction();
            bydininghallfragmentTransaction.replace(R.id.frame, bydininghallfragment);
            bydininghallfragmentTransaction.commit();
        } else if (id == R.id.myucsc) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://my.ucsc.edu/psp/ep9prd/?cmd=login&languageCd=ENG"));
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        } else if (id == R.id.ecommons) {
            Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ecommons.ucsc.edu/portal/login"));
            if (browserIntent2.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent2);
            }
        } else if (id == R.id.piazza) {
            Intent browserIntent3 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://piazza.com/class"));
            if (browserIntent3.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent3);
            }
        } else if (id == R.id.settings) {
            Intent intent2 = new Intent(this, SettingsActivity.class);
            startActivity(intent2);
        } else if (id == R.id.helpfeedback) {
            helpfeedbackfragment helpfeedbackfragment = new helpfeedbackfragment();
            android.support.v4.app.FragmentTransaction helpfeedbackfragmentTransaction = getSupportFragmentManager().beginTransaction();
            helpfeedbackfragmentTransaction.replace(R.id.frame, helpfeedbackfragment);
            helpfeedbackfragmentTransaction.commit();
        } else if (id == R.id.about) {
            aboutfragment aboutfragment = new aboutfragment();
            android.support.v4.app.FragmentTransaction aboutfragmentTransaction = getSupportFragmentManager().beginTransaction();
            aboutfragmentTransaction.replace(R.id.frame, aboutfragment);
            aboutfragmentTransaction.commit();
        } /*else if (id == R.id.bydininghall) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SetMenuIcons() {
        //Customized Navigation Drawer MenuItem Icons

        //By Meal
        Drawable navdrawericon1 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.FOOD) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon1 = navigationView.getMenu().getItem(0);
        menuItemicon1.setIcon(navdrawericon1);

        //By Dining Hall
        Drawable navdrawericon2 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.STORE) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon2 = navigationView.getMenu().getItem(1);
        menuItemicon2.setIcon(navdrawericon2);

        //
        //INSIDE: navigationView

        //myUCSC
        Drawable navdrawericon30 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.WEB) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon30 = navigationView.getMenu().getItem(2).getSubMenu().getItem(0);
        menuItemicon30.setIcon(navdrawericon30);

        //eCommons
        Drawable navdrawericon31 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.WEB) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon31 = navigationView.getMenu().getItem(2).getSubMenu().getItem(1);
        menuItemicon31.setIcon(navdrawericon31);

        //Piazza
        Drawable navdrawericon32 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.WEB) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon32 = navigationView.getMenu().getItem(2).getSubMenu().getItem(2);
        menuItemicon32.setIcon(navdrawericon32);

        //
        //

        //Settings
        Drawable navdrawericon4 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.SETTINGS) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon4 = navigationView.getMenu().getItem(3);
        menuItemicon4.setIcon(navdrawericon4);

        //Help and Feedback
        Drawable navdrawericon5 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.HELP_CIRCLE) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon5 = navigationView.getMenu().getItem(4);
        menuItemicon5.setIcon(navdrawericon5);

        //About
        Drawable navdrawericon6 = MaterialDrawableBuilder.with(this) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.INFORMATION) // provide an icon
                .setColor(Color.DKGRAY) // set the icon color
                .setToActionbarSize() // set the icon size
                .build();
        MenuItem menuItemicon6 = navigationView.getMenu().getItem(5);
        menuItemicon6.setIcon(navdrawericon6);

    }



    //Asynctask that gets the json files from server
    class GetDaysMenuasync extends AsyncTask<String, String, ArrayList<List<Menudetail>>> {

        private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String month = "";
        String day = "";
        String year = "";

        public GetDaysMenuasync(String givenmonth, String givenday, String givenyear) {
            super();
            month = givenmonth;
            day = givenday;
            year = givenyear;
        }

        ArrayList<String> DiningNameList = new ArrayList<>();

        String urlCMBR, urlCMLU, urlCMDI, urlCSBR, urlCSLU, urlCSDI, urlEOBR, urlEOLU, urlEODI, urlNTBR, urlNTLU, urlNTDI, urlPKBR, urlPKLU, urlPKDI;


        protected void onPreExecute() {

            urlCMBR = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CM_BR.json";
            urlCMLU = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CM_LU.json";
            urlCMDI = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CM_DI.json";

            urlCSBR = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CS_BR.json";
            urlCSLU = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CS_LU.json";
            urlCSDI = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_CS_DI.json";

            urlEOBR = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_EO_BR.json";
            urlEOLU = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_EO_LU.json";
            urlEODI = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_EO_DI.json";

            urlNTBR = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_NT_BR.json";
            urlNTLU = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_NT_LU.json";
            urlNTDI = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_NT_DI.json";

            urlPKBR = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_PK_BR.json";
            urlPKLU = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_PK_LU.json";
            urlPKDI = "http://chandlermoeller.me/menuoutputdetailed/jmenu_" + month + "_" + day + "_" + year + "_PK_DI.json";

            progressDialog.setMessage("Retrieving Menus");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetDaysMenuasync.this.cancel(true);
                }
            });
            DiningNameList.add(urlCMBR);
            DiningNameList.add(urlCMLU);
            DiningNameList.add(urlCMDI);

            DiningNameList.add(urlCSBR);
            DiningNameList.add(urlCSLU);
            DiningNameList.add(urlCSDI);

            DiningNameList.add(urlEOBR);
            DiningNameList.add(urlEOLU);
            DiningNameList.add(urlEODI);

            DiningNameList.add(urlNTBR);
            DiningNameList.add(urlNTLU);
            DiningNameList.add(urlNTDI);

            DiningNameList.add(urlPKBR);
            DiningNameList.add(urlPKLU);
            DiningNameList.add(urlPKDI);
        }

        protected ArrayList<List<Menudetail>> doInBackground(String... params) {
            CompleteDayMenu.clear();
            for (String DiningNameListelement : DiningNameList) {
                getJSON jsonfromurl = new getJSON();

                String json = jsonfromurl.getJSONfromURL(DiningNameListelement, 999);

                Type listType = new TypeToken<ArrayList<Menudetail>>() {
                }.getType();
                List<Menudetail> MenuList = new Gson().fromJson(json, listType);

                CompleteDayMenu.add(MenuList);
            }
            //System.out.println("CompleteDayMenu current size is:" + CompleteDayMenu.size());
            return CompleteDayMenu;
        }

        protected void onPostExecute(ArrayList<List<Menudetail>> v) {
            try {

                //
                //by meal breakfast
                //String bmbmaintitle;
                //String bmbmainsubtitle;
                bmbCompleteDayMenu = filllistwithmeals(bmbarray, bmborderarray, CompleteDayMenu);
                bmbmealrowmeta = filllistwithmealmeta(bmbarray, bmborderarray, CompleteDayMenu);

                bmbmRecyclerView = (RecyclerView) findViewById(R.id.bymealbreakfast_RecyclerViewDining);
                bmbmRecyclerView.setHasFixedSize(false);
                bmbmAdapter = new ByMealSUPERAdapter(bmbCompleteDayMenu, bmbmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
                bmbmRecyclerView.setAdapter(bmbmAdapter);
                bmbmLayoutManager = new LinearLayoutManager(MainActivity.this);
                bmbmRecyclerView.setLayoutManager(bmbmLayoutManager);

                //
                //by meal lunch
                //String bmlmaintitle;
                //String bmlmainsubtitle;
                bmlCompleteDayMenu = filllistwithmeals(bmlarray, bmlorderarray, CompleteDayMenu);
                bmlmealrowmeta = filllistwithmealmeta(bmlarray, bmlorderarray, CompleteDayMenu);

                bmlmRecyclerView = (RecyclerView) findViewById(R.id.bymeallunch_RecyclerViewDining);
                bmlmRecyclerView.setHasFixedSize(false);
                bmlmAdapter = new ByMealSUPERAdapter(bmlCompleteDayMenu, bmlmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
                bmlmRecyclerView.setAdapter(bmlmAdapter);
                bmlmLayoutManager = new LinearLayoutManager(MainActivity.this);
                bmlmRecyclerView.setLayoutManager(bmlmLayoutManager);

                //
                //by meal dinner
                //String bmdmaintitle;
                //String bmdmainsubtitle;
                bmdCompleteDayMenu = filllistwithmeals(bmdarray, bmdorderarray, CompleteDayMenu);
                bmdmealrowmeta = filllistwithmealmeta(bmdarray, bmdorderarray, CompleteDayMenu);

                bmdmRecyclerView = (RecyclerView) findViewById(R.id.bymealdinner_RecyclerViewDining);
                bmdmRecyclerView.setHasFixedSize(false);
                bmdmAdapter = new ByMealSUPERAdapter(bmdCompleteDayMenu, bmdmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
                bmdmRecyclerView.setAdapter(bmdmAdapter);
                bmdmLayoutManager = new LinearLayoutManager(MainActivity.this);
                bmdmRecyclerView.setLayoutManager(bmdmLayoutManager);


                ////////////
                bycurrentmealfragment.bycurrentmealchangetitle(startYearint, startMonthint, startDayint);
                ////////////

            } catch (Exception e) {
                Log.i("refreshdata", "Error: " + e.toString());
            }


            progressDialog.dismiss();

        }
    }

    /////
    /////TODO: Implement refresh, so that tabs are refreshed without having to access server again
    /////
    public void testrefresh() {
        try {

            bmbCompleteDayMenu = filllistwithmeals(bmbarray, bmborderarray, CompleteDayMenu);
            bmbmealrowmeta = filllistwithmealmeta(bmbarray, bmborderarray, CompleteDayMenu);

            bmbmRecyclerView = (RecyclerView) findViewById(R.id.bymealbreakfast_RecyclerViewDining);
            //bmbmRecyclerView.setHasFixedSize(false);
            bmbmAdapter = new ByMealSUPERAdapter(bmbCompleteDayMenu, bmbmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
            bmbmRecyclerView.setAdapter(bmbmAdapter);
            bmbmLayoutManager = new LinearLayoutManager(MainActivity.this);
            bmbmRecyclerView.setLayoutManager(bmbmLayoutManager);


            bmlCompleteDayMenu = filllistwithmeals(bmlarray, bmlorderarray, CompleteDayMenu);
            bmlmealrowmeta = filllistwithmealmeta(bmlarray, bmlorderarray, CompleteDayMenu);

            bmlmRecyclerView = (RecyclerView) findViewById(R.id.bymeallunch_RecyclerViewDining);
            //bmlmRecyclerView.setHasFixedSize(false);
            bmlmAdapter = new ByMealSUPERAdapter(bmlCompleteDayMenu, bmlmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
            bmlmRecyclerView.setAdapter(bmlmAdapter);
            bmlmLayoutManager = new LinearLayoutManager(MainActivity.this);
            bmlmRecyclerView.setLayoutManager(bmlmLayoutManager);


            bmdCompleteDayMenu = filllistwithmeals(bmdarray, bmdorderarray, CompleteDayMenu);
            bmdmealrowmeta = filllistwithmealmeta(bmdarray, bmdorderarray, CompleteDayMenu);

            bmdmRecyclerView = (RecyclerView) findViewById(R.id.bymealdinner_RecyclerViewDining);
            //bmdmRecyclerView.setHasFixedSize(false);
            bmdmAdapter = new ByMealSUPERAdapter(bmdCompleteDayMenu, bmdmealrowmeta, date, dayofweek, dininghallpic, MainActivity.this);
            bmdmRecyclerView.setAdapter(bmdmAdapter);
            bmdmLayoutManager = new LinearLayoutManager(MainActivity.this);
            bmdmRecyclerView.setLayoutManager(bmdmLayoutManager);

        } catch (Exception e) {
            Log.i("testrefresh", "Error: " + e.toString());
        }
    }
    /////
    /////

    public class Menudetail {
        public String name;
        private String url;
        private String[] tags;
        private String ingredients;
        private String allergens;

        public String getName() {
            return name;
        }
        public String getUrl() {
            return url;
        }
        public String[] gettags() {
            return tags;
        }
        public String getIngredients() {
            return ingredients;
        }
        public String getAllergens() {
            return allergens;
        }
    }

    //Function to make the single digit days or months into the appropriate double digit
    public String FixSingleDigittoDouble(int month) {
        if (month < 10) {
            String fixedmonth = "0";
            fixedmonth = fixedmonth + Integer.toString(month);
            return fixedmonth;
        }
        return Integer.toString(month);
    }

    //Function that changes the date and starts asynctask to update view
    //Called at startup with default date and when new date is picked
    public void ChangeDate(int month, int day, int year) {
        startMonth = FixSingleDigittoDouble(month);
        startDay = FixSingleDigittoDouble(day);
        startYear = Integer.toString(year);

        startMonthint = month;
        startDayint = day;
        startYearint = year;

        date = startMonth + "/" + startDay + "/" + startYear;

        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        int result = calendar.get(Calendar.DAY_OF_WEEK);
        switch (result) {
            case 1:
                dayofweek = "Sunday";
                break;
            case 2:
                dayofweek = "Monday";
                break;
            case 3:
                dayofweek = "Tuesday";
                break;
            case 4:
                dayofweek = "Wednesday";
                break;
            case 5:
                dayofweek = "Thursday";
                break;
            case 6:
                dayofweek = "Friday";
                break;
            case 7:
                dayofweek = "Saturday";
                break;
        }

        new GetDaysMenuasync(startMonth, startDay, startYear).execute();
    }


    //
    //Post Execute Helper Functions
    //
    ArrayList<List<Menudetail>> filllistwithmeals(int[] whicharray, int[] ordermenus, ArrayList<List<Menudetail>> CompleteDayMenu) {
        ArrayList<List<Menudetail>> newCompleteDayMenu = new ArrayList<>();
        /*for (int i = 0; i < whicharray.length; i++) {
            if (whicharray[i] == 1) {
                newCompleteDayMenu.add(CompleteDayMenu.get(i));
            }
        }*/

        if (CompleteDayMenu != null) {
            if (CompleteDayMenu.size() != 15) {
                System.err.println("Wrong Amount of menus: current size is:" + CompleteDayMenu.size());
                //TODO: ERROR
            }
            for (int i = 0; i < ordermenus.length; i++) {
                for (int j = 0; j < ordermenus.length; j++) {
                    if (whicharray[j] == 1) {
                        if (i == ordermenus[j]) {
                            newCompleteDayMenu.add(CompleteDayMenu.get(j));
                        }
                    }
                }
            }
        }

        return newCompleteDayMenu;
    }


    //Fills with meta data
    //Meta arrays are formatted as follows:
    //[0] dining hall name,
    //[1] complete open hours,
    //[2] M-F breakfast hrs,
    //[3] M-F lunch hrs,
    //[4] M-F dinner hrs,
    //[5] if bymeal then that current meals hours,
    //[6] Is healthy monday?,
    //[7] Is Farm Friday?
    ArrayList<String[]> filllistwithmealmeta(int[] whicharray, int[] ordermenus, ArrayList<List<Menudetail>> CompleteDayMenu) {
        ArrayList<String[]> newmealrowmeta = new ArrayList<>();

        if (CompleteDayMenu != null) {
            if (CompleteDayMenu.size() != 15) {
                System.err.println("2Wrong Amount of menus: current size is:" + CompleteDayMenu.size());
                //TODO: ERROR
            }
            for (int k = 0; k < ordermenus.length; k++) {
                for (int j = 0; j < ordermenus.length; j++) {
                    if (whicharray[j] == 1) {
                        if (k == ordermenus[j]) {

                            String[] inner1 = new String[8];
                            newmealrowmeta.add(inner1);

                            inner1[6] = "0";
                            inner1[7] = "0";

                            if (CompleteDayMenu.get(j) != null) {
                                if (CompleteDayMenu.get(j).size() > 1) {
                                    if (CompleteDayMenu.get(j).get(0) != null) {
                                        if (CompleteDayMenu.get(j).get(0).getName().equals("Healthy Mondays")) {
                                            inner1[6] = "1";
                                            System.out.println("Healthy Mondays Detected");
                                        }
                                        if (CompleteDayMenu.get(j).get(0).getName().equals("Farm Fridays")) {
                                            inner1[7] = "1";
                                            System.out.println("Farm Friday Detected");
                                        }
                                    }
                                }
                            }


                            fillmetaswitch(j, inner1);

                        }
                    }
                }
            }
        }
        return newmealrowmeta;
    }

    String[] fillmetaswitch(int j, String[] inner1) {
        switch (j) {
            //CM
            case 0:
                inner1[0] = "Crown/Merill";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[2];
                break;
            case 1:
                inner1[0] = "Crown/Merill";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[3];
                break;
            case 2:
                inner1[0] = "Crown/Merill";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[4];
                break;
            //CS
            case 3:
                inner1[0] = "Cowell/Stevenson";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[2];
                break;
            case 4:
                inner1[0] = "Cowell/Stevenson";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[3];
                break;
            case 5:
                inner1[0] = "Cowell/Stevenson";
                inner1[1] = "7:15am to 8:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 8:00pm";
                inner1[5] = inner1[4];
                break;
            //EO
            case 6:
                inner1[0] = "Eight/Oakes";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[2];
                break;
            case 7:
                inner1[0] = "Eight/Oakes";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[3];
                break;
            case 8:
                inner1[0] = "Eight/Oakes";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[4];
                break;
            //NT
            case 9:
                inner1[0] = "Nine/Ten";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[2];
                break;
            case 10:
                inner1[0] = "Nine/Ten";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[3];
                break;
            case 11:
                inner1[0] = "Nine/Ten";
                inner1[1] = "7:15am to 11:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 11:00pm";
                inner1[5] = inner1[4];
                break;
            //PK
            case 12:
                inner1[0] = "Porter/Kresge";
                inner1[1] = "7:15am to 7:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 7:00pm";
                inner1[5] = inner1[2];
                break;
            case 13:
                inner1[0] = "Porter/Kresge";
                inner1[1] = "7:15am to 7:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 7:00pm";
                inner1[5] = inner1[3];
                break;
            case 14:
                inner1[0] = "Porter/Kresge";
                inner1[1] = "7:15am to 7:00pm";
                inner1[2] = "7:15am to 11:00am";
                inner1[3] = "12:00pm to 2:00pm";
                inner1[4] = "5:00pm to 7:00pm";
                inner1[5] = inner1[4];
                break;
        }

        return inner1;
    }
}
