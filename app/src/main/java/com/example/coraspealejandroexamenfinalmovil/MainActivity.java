package com.example.coraspealejandroexamenfinalmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coraspealejandroexamenfinalmovil.modelo.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<String> Datos = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listaDatos);

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, Datos);

        listView.setAdapter(arrayAdapter);

        obtenerDatos();
    }

    public void obtenerDatos() {
        String url = "https://fakestoreapi.com/products";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //manejo el json
                manejarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    public void manejarJson(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                Productos productos = new Productos();

                productos.setId(jsonObject.getInt("id"));
                productos.setTitle(jsonObject.getString("title"));
                productos.setDescription(jsonObject.getString("description"));
                productos.setPrice(jsonObject.getDouble("price"));

                Datos.add(String.valueOf(productos.getId()));
                Datos.add(productos.getTitle());
                Datos.add(productos.getDescription());
                Datos.add(String.valueOf(productos.getPrice()));

            } catch (JSONException jsonException) {
                jsonException.getMessage();
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplication(),AddProductos.class);
        startActivity(intent);
    }
}