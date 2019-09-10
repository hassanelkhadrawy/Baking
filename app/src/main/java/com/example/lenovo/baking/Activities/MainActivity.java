package com.example.lenovo.baking.Activities;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.lenovo.baking.Adapters.MainRecyclerAdapter;
import com.example.lenovo.baking.Model.IngredientsModel;
import com.example.lenovo.baking.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    RecyclerView MainRecyclerItem;
    String URL;
    MainRecyclerAdapter mainRecyclerAdapter;
    ArrayList<String> list_item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainRecyclerItem = findViewById(R.id.MainRecycle);
        URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        new JsonUtils().execute();


    }


    class JsonUtils extends AsyncTask<Void, Void, String> {
        String urlJson = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            urlJson = URL;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlJson);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {

                    sb.append(line + "\n");

                }
                is.close();
                bufferedReader.close();
                con.disconnect();
                return sb.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s == null) {
                Toast.makeText(MainActivity.this, "Please,Check Internet Connection..", Toast.LENGTH_LONG).show();
            } else {


                loadItemsList(s);

            }


        }

        private void loadItemsList(String json) {
            IngredientsModel ingredientsModel=new IngredientsModel();
            Paper.init(MainActivity.this);
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject getData = jsonArray.getJSONObject(i);
                    String id = getData.getString("id");
                    String name = getData.getString("name");
                    list_item.add(name);
                    ingredientsModel.setIngredient1(i);
                    ingredientsModel.setIngredient2(name);

                }

                mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, list_item);

                if (MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    MainRecyclerItem.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                }else {
                    MainRecyclerItem.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));

                }
                MainRecyclerItem.setAdapter(mainRecyclerAdapter);


            } catch (Exception e) {
                Log.d("x", e.toString());


            }


        }

    }

}


