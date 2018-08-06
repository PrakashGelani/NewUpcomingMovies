package com.prakashgelani.newupcomingmovies;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prakashgelani.newupcomingmovies.adapter.AdapterMovieList;
import com.prakashgelani.newupcomingmovies.common.Common;
import com.prakashgelani.newupcomingmovies.modal.ListItemModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieListActivity extends AppCompatActivity implements View.OnClickListener {

    private MovieListActivity activity;
    private RecyclerView recycleList;
    private TextView txtNoData;
    private RequestQueue queue;
    private ArrayList<ListItemModal> arrayListMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        activity = MovieListActivity.this;
        queue = Volley.newRequestQueue(activity);
        arrayListMovies = new ArrayList<ListItemModal>();
        init();
    }

    private void init() {
        ImageView info = findViewById(R.id.img_info);
        info.setOnClickListener(this);

        recycleList = findViewById(R.id.recycle_view_home);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(activity);
        recycleList.setLayoutManager(mLayoutManager);

        txtNoData = findViewById(R.id.txt_no_data);
        if (!Common.isNetworkConnected(activity)) {
            Common.toastMsg(activity, "No internet connection.");
            finish();
        } else {
            CallApiMovieList();
        }
    }

    private void CallApiMovieList() {
        // TODO Auto-generated method stub
        try {
            Common.dialog_view(activity);
            StringRequest strRequest1 = new StringRequest(Request.Method.POST,
                    "https://api.themoviedb.org/3/movie/upcoming", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("ResponseMovieList>>", "" + response);
                    Common.dialog_dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectResult = jsonArray.getJSONObject(i);
                            ListItemModal listItemModal = new ListItemModal();
                            listItemModal.setStrTitle(jsonObjectResult.getString("title"));
                            listItemModal.setStrVoteAverage(String.valueOf(jsonObjectResult.getInt("vote_average")));
                            listItemModal.setStrImgPosterPath(jsonObjectResult.getString("poster_path"));
                            listItemModal.setStrOverview(jsonObjectResult.getString("overview"));
                            listItemModal.setStrReleaseDate(jsonObjectResult.getString("release_date"));
                            listItemModal.setStrAdult(String.valueOf(jsonObjectResult.getBoolean("adult")));

                            arrayListMovies.add(listItemModal);
                        }
                        if (arrayListMovies.size() != 0) {
                            AdapterMovieList adapter = new AdapterMovieList(activity, arrayListMovies);
                            recycleList.setAdapter(adapter);
                            recycleList.setVisibility(View.VISIBLE);
                            txtNoData.setVisibility(View.GONE);
                        } else {
                            recycleList.setVisibility(View.GONE);
                            txtNoData.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Common.dialog_dismiss();
                    Log.e("Reponse....", "Reponse4:-" + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("api_key", "b7cd3340a794e5a2f35e3abb820b497f");
                    Log.e("params>>", "" + params);
                    return params;
                }
            };
            strRequest1.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(strRequest1);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_info:
                startActivity(new Intent(activity, InformationActivity.class));
                break;
        }
    }
}
