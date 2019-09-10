package com.example.lenovo.baking.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.baking.Adapters.StepsItemsAdapter;
import com.example.lenovo.baking.Model.DetailsModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
    RecyclerView recyclerViewSteps;
    ArrayList<DetailsModel> list_steps = new ArrayList<>();
    StepsItemsAdapter stepsItemsAdapter;
    String shortDescription, description, videoURL;
    VideoInTablet videoInTablet;
    int POSITION;
    String URL;
    IngredientsModel model=new IngredientsModel();


    public Tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_steps, container, false);

        recyclerViewSteps =view.findViewById(R.id.recycler_steps);
        videoInTablet = new VideoInTablet();
        URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        POSITION=model.getId();
        new JsonUtils().execute();

        return view;

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
                java.net.URL url = new URL(urlJson);
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
                Toast.makeText(getActivity(), "Please,Check Internet Connection..", Toast.LENGTH_LONG).show();
            } else {


                loadItemsList(s);

            }


        }

        private void loadItemsList(String json) {

            try {
                JSONArray jsonArray = new JSONArray(json);
                JSONObject getData = jsonArray.getJSONObject(POSITION);

                JSONArray jsonArray1 = getData.getJSONArray("steps");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject object3 = jsonArray1.getJSONObject(i);

                    shortDescription = object3.getString("shortDescription");
                    description = object3.getString("description");
                    videoURL = object3.getString("videoURL");

                    list_steps.add(new DetailsModel(shortDescription, description, videoURL));

                }

                stepsItemsAdapter = new StepsItemsAdapter(getActivity(), list_steps);
                recyclerViewSteps.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                recyclerViewSteps.setAdapter(stepsItemsAdapter);
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {


                } else {

                    FragmentManager fragMan = getActivity().getFragmentManager();
                    FragmentTransaction fragTransaction = fragMan.beginTransaction();
                    fragTransaction.add(R.id.container, videoInTablet);
                    fragTransaction.commit();

                }


            } catch (Exception e) {
                Log.d("x", e.toString());


            }


        }

    }


    public interface OnFragmentInteractionListener {
    }


}
