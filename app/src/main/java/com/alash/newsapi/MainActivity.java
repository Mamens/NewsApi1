package com.alash.newsapi;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    String ApiKey = "0812fde4a615422b98f6e1d23b1a52a1";
    RecyclerView recyclerView;
    private static JSONArray currentJsonArray = new JSONArray();

    public static JSONArray getCurrentJsonArray() {
        return currentJsonArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        String Url = "https://newsapi.org/v1/articles?source=techcrunch&apiKey="+ApiKey;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        textView_News.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONObject res = new JSONObject(response);
                            Log.d("myLogs",res.toString());
                            if(res.getString("status").equals("ok")){

                                recyclerView.setAdapter(new MyAdapter(res.getJSONArray("articles"),getBaseContext()));
                                currentJsonArray = res.getJSONArray("articles");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myLogs", error.toString());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
