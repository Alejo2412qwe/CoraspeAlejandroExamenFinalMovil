package com.example.coraspealejandroexamenfinalmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddProductos extends AppCompatActivity implements View.OnClickListener {


    private EditText titulo, descripcion, precio;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productos);

        titulo = findViewById(R.id.txtTitulo);
        descripcion = findViewById(R.id.txtDescripcion);
        precio = findViewById(R.id.txtPrecio);

        queue = Volley.newRequestQueue(this);
    }


    public void InsertarDatos() {
        String url = "https://fakestoreapi.com/products";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("title", titulo.getText().toString());
            jsonObject.put("price", precio.getText().toString());
            jsonObject.put("description", descripcion.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddProductos.this, "CARGA DE DATOS EXITOSA", Toast.LENGTH_SHORT).show();
                        titulo.setText("");
                        descripcion.setText("");
                        precio.setText("");
                        Intent intent = new Intent(getApplication(),MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddProductos.this, "NO SE HA PODIDO REALIZAR LA CARGA DE DATOS", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        InsertarDatos();
    }
}