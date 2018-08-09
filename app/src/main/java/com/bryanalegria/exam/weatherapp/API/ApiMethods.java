package com.bryanalegria.exam.weatherapp.API;

import android.content.Context;


import com.bryanalegria.exam.weatherapp.R;

import org.json.JSONObject;

import java.util.HashMap;

public class ApiMethods {
    Context mContext;
    public ApiMethods(Context context) {
        this.mContext = context;
    }
    public JSONObject GetWeatherLondon() {
        String result = (new HttpConnection()).httpGetResponse(mContext.getString(R.string.api_url_london));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }
    public JSONObject GetWeatherPorgue() {
        String result = (new HttpConnection()).httpGetResponse(mContext.getString(R.string.api_url_porgue));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }
    public JSONObject GetWeatherSF() {
        String result = (new HttpConnection()).httpGetResponse(mContext.getString(R.string.api_url_sf));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }
}
