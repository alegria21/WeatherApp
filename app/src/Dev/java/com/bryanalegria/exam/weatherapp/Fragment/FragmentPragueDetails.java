package com.bryanalegria.exam.weatherapp.Fragment;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bryanalegria.exam.weatherapp.API.GlobalVariables;

import com.bryanalegria.exam.weatherapp.R;

public class FragmentPragueDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView iv_icons;
    TextView tv_weather;
    TextView tv_desc;
    TextView tv_temp;
    TextView tv_speed;
    TextView tv_deg;
    FragmentManager manager;

    public FragmentPragueDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPragueDetails newInstance(String param1, String param2) {
        FragmentPragueDetails fragment = new FragmentPragueDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_prague_details, container, false);
        manager = getFragmentManager();
        // Inflate the layout for this fragment
        tv_weather = view.findViewById(R.id.tv_weather);
        tv_desc = view.findViewById(R.id.tv_desc);
        tv_temp = view.findViewById(R.id.tv_temp);
        tv_speed = view.findViewById(R.id.tv_speed);
        iv_icons = view.findViewById(R.id.iv_icons);

        tv_desc.setText(GlobalVariables.prague_data.getDescription());
        tv_speed.setText(GlobalVariables.prague_data.getSpeed());
        tv_temp.setText(GlobalVariables.prague_data.getTemp() + "°C");
        tv_weather.setText(GlobalVariables.prague_data.getMain());
        iv_icons.setImageBitmap(GlobalVariables.prague_data.getIcons());

        return view;
    }
}

