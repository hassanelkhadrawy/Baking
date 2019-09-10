package com.example.lenovo.baking.Fragments;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.baking.Adapters.IngredientAdapter;
import com.example.lenovo.baking.BakingWidget;
import com.example.lenovo.baking.Model.IngredientsModel;
import com.example.lenovo.baking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {

    RecyclerView Ingredient_Recycler;
    int POSITION;
    StringBuilder sb = new StringBuilder();

    ArrayList<IngredientsModel> list=new ArrayList<>();
    IngredientsModel model=new IngredientsModel();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view=inflater.inflate(R.layout.fragment_tab1, container, false);
        Ingredient_Recycler=view.findViewById(R.id.ingredients_recycler);
        preferences= getActivity().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor=preferences.edit();
       POSITION=model.getId();
        select();
        Paper.init(getActivity());

        Context context = getActivity();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        ComponentName thisWidget = new ComponentName(context, BakingWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, preferences.getString("list",null));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        return view;
    }

    public interface OnFragmentInteractionListener {
    }


    void select() {
        String URLLINK="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URLLINK, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {

                    JSONObject getData = response.getJSONObject(POSITION);
                    JSONArray jsonArray = getData.getJSONArray("ingredients");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject getData2 = jsonArray.getJSONObject(i);

                        String quantity=getData2.getString("quantity");
                        String measure=getData2.getString("measure");
                        String ingredient=getData2.getString("ingredient");
                        sb.append(ingredient+"\n");
                        list.add(new IngredientsModel(quantity,measure,ingredient));

                    }
                    IngredientsModel ingredientsModel=new IngredientsModel();
                    ingredientsModel.setIngredient2(sb.toString());
                    editor.putString("list",sb.toString());
                    editor.apply();
                    Paper.book().write("list",list);
                    IngredientAdapter adapter=new IngredientAdapter(getActivity(),list);

                    if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Ingredient_Recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }else {
                        Ingredient_Recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

                    }

                    Ingredient_Recycler.setAdapter(adapter);





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }



}
