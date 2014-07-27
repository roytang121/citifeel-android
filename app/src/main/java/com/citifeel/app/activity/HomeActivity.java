package com.citifeel.app.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.citifeel.app.R;
import com.citifeel.app.fragment.NewsFeedFragment;
import com.citifeel.app.util.SessionManager;
import com.facebook.Session;

import java.util.HashMap;

public class HomeActivity extends FragmentActivity {

    // Session Manager Class
    SessionManager session;

    // items for tab bar
    public static final String[] TABS = {"Home", "Page2", "Page3"};
    public static final int[] TABS_D = {R.drawable.ic_action_group, R.drawable.ic_favorite, R.drawable.ic_action_person};

    //View pager
    ViewPager mViewPager;

    //Tab Adapter
    HomePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Session class instance
        /* avoid using applicationcontext */
//        session = new SessionManager(getApplicationContext());
        session = new SessionManager(HomeActivity.this);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        if(session.checkLogin()){
            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // email
            String email = user.get(SessionManager.KEY_EMAIL);

            Log.i("Login successful, user email:", email);
        }

        //init view
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(mPagerAdapter);
        configTabs();

    }

    private void configTabs() {
        final ActionBar actionBar = getActionBar();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                if(mViewPager != null){
                    mViewPager.setCurrentItem(tab.getPosition());
                }
                // change the icon color
                if (tab.getIcon() != null) {
                    tab.getIcon().clearColorFilter();
                    tab.getIcon().setColorFilter(Color.parseColor("#ff8800"), PorterDuff.Mode.MULTIPLY);
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab

                // change the icon color
                if (tab.getIcon() != null) {
                    tab.getIcon().clearColorFilter();
                    tab.getIcon().setColorFilter(Color.parseColor("#cccccc"), PorterDuff.Mode.MULTIPLY);
                }
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        //add tabs
        for (int i = 0; i < TABS.length; i++) {
            actionBar.addTab(
                    actionBar.newTab()
//                            .setText(TABS[i])
                            .setIcon(getResources().getDrawable(TABS_D[i]))
                            .setTabListener(tabListener));
            actionBar.getTabAt(i).getIcon().setColorFilter(Color.parseColor("#CCCCCC"), PorterDuff.Mode.MULTIPLY);
        }

        if(mViewPager != null) {
            mViewPager.setOnPageChangeListener(
                    new ViewPager.SimpleOnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            // When swiping between pages, select the
                            // corresponding tab.
                            getActionBar().setSelectedNavigationItem(position);
                        }
                    }
            );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            doLogout();
            return true;
        } else if(id == R.id.action_search) {
            /* jump to search activity */
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_create) {
            /* jump to post activity */
            Intent intent = new Intent(this, PostActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogout(){
        Log.i("Listener","logoutbutton onclick");

        // Clear the session data
        // This will clear all session data and
        // redirect user to LoginActivity
        session.logoutUser();

        //clear fb session info
        Session fbsession = Session.getActiveSession();
        if (fbsession!=null && !fbsession.isClosed()) {
            fbsession.closeAndClearTokenInformation();
        }
    }

    /* Custom view page adapter */
    class HomePagerAdapter extends FragmentPagerAdapter{

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch(position){
                case 0: //NewsFeed
                case 1: //?
                case 2: //?
                    f = new NewsFeedFragment();
            }
            return f;
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }
    }
}
