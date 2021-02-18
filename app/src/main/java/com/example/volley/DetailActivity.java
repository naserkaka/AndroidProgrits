package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    TextView txtId;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String endpoint = getIntent().getStringExtra("BookId");


         txtId = (TextView) findViewById(R.id.txtId);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitle);
        TextView txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        TextView txtGenre = (TextView) findViewById(R.id.txtGenre);
        TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
        TextView txtDate = (TextView) findViewById(R.id.txtDate);
        TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
        Button btnDelete = findViewById(R.id.btnSubmit);

        // Instantiate the RequestQueue.

           String url = "https://192.168.1.57:5001/api/Books/id/"+endpoint;

// Instantiate the RequestQueue.
         queue = Volley.newRequestQueue(this);
        // String url ="https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONArray jsonArray= null;
                        try {
                            jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String title = object.getString("title");
                                String author = object.getString("author");
                                String genre = object.getString("genre");
                                String date = object.getString("publishDate");
                                String price = object.getString("price");
                                String description = object.getString("description");

                                txtId.setText(id);
                                txtTitle.setText(title);
                                txtAuthor.setText(author);
                                txtGenre.setText(genre);
                                txtDate.setText(date);
                                txtPrice.setText(price);
                                txtDescription.setText(description);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.toString());
            }
        });
        queue.add(stringRequest);
    }
   public void btnBackAction(View view){
       Intent intent = new Intent(getBaseContext(), MainActivity.class);
       startActivity(intent);
   }

    public void btnDeleteAction(View View){
        String id = txtId.getText().toString();
        String url = "https://192.168.1.57:5001/api/Books/id/"+id;
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        queue.add(dr);
    }

}