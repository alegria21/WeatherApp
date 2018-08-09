package com.bryanalegria.exam.weatherapp;

import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.bryanalegria.exam.weatherapp.API.GlobalVariables;
import com.bryanalegria.exam.weatherapp.Fragment.MainFragment;
import com.bryanalegria.exam.weatherapp.Fragment.WeatherDetailFragment;
import com.bryanalegria.exam.weatherapp.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager _fragManager;
    private FragmentTransaction _fragTransaction;
    private static final int FRAGMENT_COUNT = 2;
    DrawerLayout mDrawer;

    private static final int MAIN = 0;
    private static final int DETAILS = 1;
    private int currentFragmentPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fragManager = getFragmentManager();

        MainFragment weatherDetailFragment = new MainFragment();
        FragmentTransaction transaction = _fragManager.beginTransaction();
        transaction.replace(R.id.container, weatherDetailFragment, "A");
        transaction.commit();

    }


}
