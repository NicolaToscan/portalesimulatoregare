package it.unitn.lpsmt2020.portalesimulatoregare.datasource;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.unitn.lpsmt2020.portalesimulatoregare.models.ChampionshipItemLight;

public class DataSource {
    private static final String apiEndpoint = "http://nicolatoscan.altervista.org/api/";
    private static RequestQueue requestQueue;
    private static Context ctx;

    public static void initDataSource(Context ctx) {
        DataSource.ctx = ctx;
    }
    private static void addToRequestQueue(Request r) {
        RequestSingleton.getInstance(ctx).addToRequestQueue(r);
    }





    public static void getSubscribedChampionship(final MutableLiveData<List<ChampionshipItemLight>> championshipList) {
        String url = apiEndpoint + "championship/current.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray champArray = response.getJSONArray("campionati");
                            ArrayList<ChampionshipItemLight> res = new ArrayList<ChampionshipItemLight>();

                            for (int i = 0; i < champArray.length(); i++) {
                                JSONObject champJSON = champArray.getJSONObject(i);
                                int id = Integer.parseInt(champJSON.getString("id"));
                                String name = champJSON.getString("nome");
                                res.add(new ChampionshipItemLight(id, name, InternalDB.isSubscribed(id)));
                            }
                            championshipList.postValue(res);

                        } catch (Exception e) {
                            championshipList.postValue(new ArrayList<ChampionshipItemLight>());
                            //TODO: qualcosina qui
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        championshipList.postValue(new ArrayList<ChampionshipItemLight>());
                        //TODO: qualcosina qui
                    }
                });

        addToRequestQueue(jsonObjectRequest);

    }
}
