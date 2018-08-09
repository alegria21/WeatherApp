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

public class WeatherDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private London london;
    private Bitmap icon;
    ImageView iv_icons;
    TextView tv_weather;
    TextView tv_desc;
    TextView tv_temp;
    TextView tv_speed;
    TextView tv_deg;
    FragmentManager manager;
    private Bitmap london_icon = null;


    public WeatherDetailFragment() {
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
    public static WeatherDetailFragment newInstance(String param1, String param2) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            london = (London) bundle.getSerializable("MY_BUNDLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        manager = getFragmentManager();
        // Inflate the layout for this fragment
         tv_weather = view.findViewById(R.id.tv_weather);
         tv_desc = view.findViewById(R.id.tv_desc);
         tv_temp = view.findViewById(R.id.tv_temp);
         tv_speed = view.findViewById(R.id.tv_speed);
        iv_icons = view.findViewById(R.id.iv_icons);

        tv_desc.setText(GlobalVariables.london_data.getDescription());
        tv_speed.setText(GlobalVariables.london_data.getSpeed());
        tv_temp.setText(GlobalVariables.london_data.getTemp()+"°C");
        tv_weather.setText(GlobalVariables.london_data.getMain());
        iv_icons.setImageBitmap(GlobalVariables.london_data.getIcons());
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
                //GET DETAILS FOR LONDON WEATHER
                JSONObject london = new ApiMethods(getContext()).GetWeatherLondon();

                JSONObject data = new JSONObject(london.getString("Data").replaceAll("null","\"\""));

                JSONArray weather = data.getJSONArray("weather");
                JSONObject JSONWeather = weather.getJSONObject(0);
                GlobalVariables.london_data =  new London();
                GlobalVariables.london_data.setId(String.valueOf(JSONWeather.get("id")));
                GlobalVariables.london_data.setMain(String.valueOf(JSONWeather.get("main")));
                GlobalVariables.london_data.setDescription(String.valueOf(JSONWeather.get("description")));
                GlobalVariables.london_data.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject main = new JSONObject(data.getString("main"));
                GlobalVariables.london_data.setTemp(String.valueOf(main.get("temp")));

                JSONObject wind = new JSONObject(data.getString("wind"));
                GlobalVariables.london_data.setSpeed(String.valueOf(wind.get("speed")));



                String urlOfLondonicon = "http://openweathermap.org/img/w/"+GlobalVariables.london_data.getIcon()+".png";
                try {
                    is = new URL(urlOfLondonicon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                london_icon = BitmapFactory.decodeStream(is);
                GlobalVariables.london_data.setIcons(london_icon);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_desc.setText(GlobalVariables.london_data.getDescription());
            tv_speed.setText(GlobalVariables.london_data.getSpeed());
            tv_temp.setText(GlobalVariables.london_data.getTemp()+"°C");
            tv_weather.setText(GlobalVariables.london_data.getMain());
            iv_icons.setImageBitmap(GlobalVariables.london_data.getIcons());

            progressDialog.dismiss();
        }



    }


}
