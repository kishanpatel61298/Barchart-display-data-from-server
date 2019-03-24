package com.example.kishan.charts;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    String URL = "URL";
    ArrayList<String> countryName;
    ArrayList<String> id;
    public static ArrayList<String> name;
    public static ArrayList<String> value;
    LinearLayout lnrFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        lnrFragment = findViewById(R.id.lnrFragment);

        getCountry();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 1) {
                    String countyId = id.get(spinner.getSelectedItemPosition());

                  //  Toast.makeText(getApplicationContext(), countyId, Toast.LENGTH_LONG).show();

                    getState(countyId);
                } else {
                    lnrFragment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

    }

    // private void fragments()
//    {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        empty empty = new empty();
//        fragmentTransaction.add(R.id.fragment, empty);
//        fragmentTransaction.commit();
//    }

    private void changeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Chart blankFragment = new Chart();
        fragmentTransaction.replace(R.id.fragment, blankFragment);
        fragmentTransaction.commit();
    }

    private void getCountry() {

        HttpRestClient.get(URL, new RequestParams(), new AsyncHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                Log.i("TAG", "onSuccess: " + s);
                try {
                    countryName = new ArrayList<String>();

                    id = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("data");
                    countryName.add(0, "Choose Country");
                    id.add("0");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        countryName.add(object.getString("country"));
                        id.add(object.getString("country_id"));
                    }
                    makeSpinner();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void makeSpinner() {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, countryName);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

    }


    private void getState(String state_id) {
        RequestParams params = new RequestParams();
        params.put("id", state_id);
        HttpRestClient.post(URL, params, new AsyncHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                super.onStart();
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
                Log.i("TAG", "onSuccess: " + s);
                try {
                    name = new ArrayList<>();
                    value = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        name.add(object.getString("name"));
                        value.add(object.getString("value"));
                    }
                    lnrFragment.setVisibility(View.VISIBLE);
                    changeFragment();
                  //  makeSpinner2(name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }




}


