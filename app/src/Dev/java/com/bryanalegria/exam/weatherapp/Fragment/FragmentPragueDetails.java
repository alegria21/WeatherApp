package com.bryanalegria.exam.weatherapp.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;

import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bryanalegria.exam.weatherapp.API.ApiMethods;
import com.bryanalegria.exam.weatherapp.API.GlobalVariables;

import com.bryanalegria.exam.weatherapp.Model.London;
import com.bryanalegria.exam.weatherapp.Model.Prague;
import com.bryanalegria.exam.weatherapp.Model.San_Francisco;
import com.bryanalegria.exam.weatherapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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



    private Bitmap london_icon = null;
    private Bitmap prague_icon = null;
    private Bitmap sf_icon = null;

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
        Button back =view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment main = new MainFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container, main, "A");
                transaction.commit();
            }
        });

        Button btn_Refresh = view.findViewById(R.id.btn_Refresh);
        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDetails task = new GetDetails();
                task.execute();
            }
        });

        return view;
    }

    private class GetDetails extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Weather App Message");
            progressDialog.setMessage("Gathering Information..");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            InputStream is = null;
            try {
                //GET DETAILS FOR PRAGUE WEATHER
                JSONObject porgue = new ApiMethods(getContext()).GetWeatherPorgue();

                JSONObject porgue_data = new JSONObject(porgue.getString("Data").replaceAll("null","\"\""));

                JSONArray porgue_weather = porgue_data.getJSONArray("weather");
                JSONObject JSONPorgueWeather = porgue_weather.getJSONObject(0);
                GlobalVariables.prague_data =  new Prague();
                GlobalVariables.prague_data.setId(String.valueOf(JSONPorgueWeather.get("id")));
                GlobalVariables.prague_data.setMain(String.valueOf(JSONPorgueWeather.get("main")));
                GlobalVariables.prague_data.setDescription(String.valueOf(JSONPorgueWeather.get("description")));
                GlobalVariables.prague_data.setIcon(String.valueOf(JSONPorgueWeather.get("icon")));

                JSONObject porgue_main = new JSONObject(porgue_data.getString("main"));
                GlobalVariables.prague_data.setTemp(String.valueOf(porgue_main.get("temp")));

                JSONObject porgue_wind = new JSONObject(porgue_data.getString("wind"));
                GlobalVariables.prague_data.setSpeed(String.valueOf(porgue_wind.get("speed")));


                String urlOfpragueicon = "http://openweathermap.org/img/w/"+GlobalVariables.prague_data.getIcon()+".png";
                try {
                    is = new URL(urlOfpragueicon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                prague_icon = BitmapFactory.decodeStream(is);
                GlobalVariables.prague_data.setIcons(prague_icon);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv_desc.setText(GlobalVariables.prague_data.getDescription());
            tv_speed.setText(GlobalVariables.prague_data.getSpeed());
            tv_temp.setText(GlobalVariables.prague_data.getTemp() + "°C");
            tv_weather.setText(GlobalVariables.prague_data.getMain());
            iv_icons.setImageBitmap(GlobalVariables.prague_data.getIcons());

            progressDialog.dismiss();
        }



    }
}

