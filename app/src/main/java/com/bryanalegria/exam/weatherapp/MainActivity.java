package com.bryanalegria.exam.weatherapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.bryanalegria.exam.weatherapp.API.GlobalVariables;
import com.bryanalegria.exam.weatherapp.Fragment.MainFragment;
import com.bryanalegria.exam.weatherapp.Fragment.WeatherDetailFragment;
import com.bryanalegria.exam.weatherapp.R;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener, WeatherDetailFragment.OnFragmentInteractionListener {

    private FragmentManager _fragManager;
    private FragmentTransaction _fragTransaction;
    private static final int FRAGMENT_COUNT = 2;
    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    DrawerLayout mDrawer;

    private static final int MAIN = 0;
    private static final int DETAILS = 1;
    private int currentFragmentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        _fragManager = getSupportFragmentManager();
        fragments[MAIN] = _fragManager.findFragmentById(R.id.Main);
        fragments[DETAILS] = _fragManager.findFragmentById(R.id.Details);

        _fragTransaction = _fragManager.beginTransaction();

        for (Fragment frag : fragments) {
            _fragTransaction.hide(frag);
        }
        _fragTransaction.commit();

        //for screen orientation change
        if (savedInstanceState != null) {
            showFragment(savedInstanceState.getInt("currentFragment", GlobalVariables.currentFrag), false);
        }
        //show first fragment if first time open
        else {
            showFragment(MAIN, false);
        }
    }

    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);

            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        switch (fragmentIndex) {
            case MAIN:
                currentFragmentPosition = MAIN;
                GlobalVariables.currentFrag = MAIN;
                break;
            case DETAILS:
                currentFragmentPosition = DETAILS;
                GlobalVariables.currentFrag = DETAILS;
                break;

        }
        transaction.commit();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentFragment", GlobalVariables.currentFrag);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
