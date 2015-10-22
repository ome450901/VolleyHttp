package com.willy.httpconnect;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();

        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        String url = "http://private-299d3-youtake.apiary-mock.com/auth/login";

        //String Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //Json Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://private-299d3-youtake.apiary-mock.com/googles/pushnotification",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //自定義Gson Request with json object
        GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
                Request.Method.GET,
                "http://www.weather.com.cn/data/sk/101010100.html", Weather.class,
                new Response.Listener<Weather>() {
                    @Override
                    public void onResponse(Weather weather) {
                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                        Log.d("TAG", "city is " + weatherInfo.getCity());
                        Log.d("TAG", "temp is " + weatherInfo.getTemp());
                        Log.d("TAG", "time is " + weatherInfo.getTime());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) {
        };

        //自定義Gson Request with json array by Method GET
        GsonRequest<PushNotification> push = new GsonRequest<PushNotification>(
                Request.Method.GET,
                "http://private-299d3-youtake.apiary-mock.com/googles/pushnotification", PushNotification.class,
                new Response.Listener<PushNotification>() {
                    @Override
                    public void onResponse(PushNotification pushNotification) {
                        Advertisements[] advertisements = pushNotification.getAdvertisements();
                        Log.d("TAG", "id is " + advertisements[0].getAdvertise_id());
                        Log.d("TAG", "view is " + advertisements[0].getAdvertise_type());
                        Log.d("TAG", "url is " + advertisements[0].getVideo_uri());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) {
        };


        Map<String, String> mParams = new HashMap<>();
        mParams.put("device_id", "123456");
        mParams.put("gender", "female");
        mParams.put("age", "20");
        GsonRequest<String> activities = new GsonRequest<String>(
                Request.Method.POST,
                "http://private-299d3-youtake.apiary-mock.com/activities", String.class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.e("TAG", response);
                        } else {
                            Log.e("TAG", "null response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }, mParams) {
        };
//        activities.setShouldCache(false);

        mQueue.add(activities);

        UiChangeListener();
    }

    public void UiChangeListener()
    {
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(flags);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        decorView.setSystemUiVisibility(flags);
//                    }
//                });
//            }
//        },0,100);
    }

    private Handler handler = new Handler();


}
