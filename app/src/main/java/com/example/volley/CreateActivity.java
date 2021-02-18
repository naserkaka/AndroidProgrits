package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    public void btnSubmitAction(View view){


        EditText txtId = (EditText) findViewById(R.id.txtId);
        EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
        EditText txtAuthor = (EditText) findViewById(R.id.txtAuthor);
        EditText txtGenre = (EditText) findViewById(R.id.txtGenre);
        EditText txtPrice = (EditText) findViewById(R.id.txtPrice);
        EditText txtDate = (EditText) findViewById(R.id.txtDate);
        EditText txtDescription = (EditText) findViewById(R.id.txtDescription);

        String id = txtId.getText().toString();
        String title = txtTitle.getText().toString();
        String author = txtAuthor.getText().toString();
        String genre = txtGenre.getText().toString();
        String price = txtPrice.getText().toString();
        String date = txtDate.getText().toString();
        String description = txtDescription.getText().toString();

        JSONObject postData = new JSONObject();

        try {
            postData.put("id",id);
            postData.put("title",title);
            postData.put("author",author);
            postData.put("genre",genre);
            postData.put("price",price);
            postData.put("publish_Date",date);
            postData.put("description",description);

        } catch (JSONException e) {
            e.printStackTrace();
        }
       RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://192.168.1.57:5001/api/Books/";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        queue.add(jsonObjReq);


        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void btnBackTwoAction(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}