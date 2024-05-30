package com.alignedcookie88.real_time.api;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SunriseSunsetIO {


    public static APIResponse makeRequest(float latitude, float longitude) {
        try {
            URL url = new URL(String.format("https://api.sunrisesunset.io/json?lat=%f&lng=%f", latitude, longitude));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Gson gson = new Gson();
            APIResponseWrapper wrapper = gson.fromJson(in, APIResponseWrapper.class);
            return wrapper.results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static class APIResponseWrapper {

        public APIResponse results;

        public String status;

    }


    public static class APIResponse {

        public String date;

        public String sunrise;

        public String sunset;

        public String first_light;

        public String last_light;

        public String dawn;

        public String dusk;

        public String solar_noon;

        public String golden_hour;

        public String day_length;

        public String timezone;

        public Integer utc_offset;

    }

}
