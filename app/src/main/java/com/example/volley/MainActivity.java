package com.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayListID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Api api = new Api(this);
        api.trustmanager();
      TextView textView= findViewById(R.id.textView);
      listView = findViewById(R.id.listview);
        arrayListID  = new ArrayList<String>();

        Spinner bookspinner = findViewById(R.id.spinnerid);
        bookspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                arrayListID =new ArrayList<String>(); // töm listan
               String info = bookspinner.getSelectedItem().toString();
               getBooksById(info);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String main = listView.getSelectedItem().toString();
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
               String ItemId = arrayListID.get(position);
               intent.putExtra("BookId", ItemId);
                startActivity(intent);

            }
        });


    }
   public void btnNewAction(View view){
       Intent intent = new Intent(getBaseContext(), CreateActivity.class);
       startActivity(intent);

   }
    public void listId(String response,String endpoint){
        ArrayList<String> arrayList= new ArrayList<String>();
        if(endpoint.equals("published")){
            endpoint="publishDate"; // Api endpoint för date
        }
       // Toast.makeText(this, endpoint, Toast.LENGTH_LONG).show();

        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(response);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                String objectinfo = object.getString(endpoint);
                String id = object.getString("id");

                arrayList.add(objectinfo);
                arrayListID.add(id);
                Log.i("teest",id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,arrayList);
        listView.setAdapter(arrayAdapter);

    }

    public void getBooksById(String endpoint){
        TextView textView = (TextView) findViewById(R.id.textView);
        // Instantiate the RequestQueue.

         String url = "https://192.168.1.57:5001/api/Books/"+endpoint;
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // String url ="https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listId(response,endpoint);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!" + error.toString());
            }
        });

        queue.add(stringRequest);

    }


 }


