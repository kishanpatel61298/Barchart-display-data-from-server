package com.example.kishan.charts;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpRestClient {

    private static final String TAG = "HttpRestClient";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler handler){
        client.get(url,params,handler);
        Log.i(TAG, AsyncHttpClient.getUrlWithQueryString(false,url,params));
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler){
        client.post(url,params,handler);
        Log.i(TAG, AsyncHttpClient.getUrlWithQueryString(false,url,params));
    }
}
