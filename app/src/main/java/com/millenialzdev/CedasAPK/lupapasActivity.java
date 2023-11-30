package com.millenialzdev.CedasAPK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class lupapasActivity extends AppCompatActivity {
    private EditText txt_username, txt_password;
    private TextView bt_login;
    private Button btn_ganti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapas);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_pass);
        btn_ganti = findViewById(R.id.btn_ganti);
        bt_login = findViewById(R.id.bt_login);

        btn_ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    StringRequest stringRequest =  new StringRequest(Request.Method.POST, Db_Contract.UrlLupas, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("", response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("", error.toString());
                        }
                    }) {
                        @Override
                        public byte[] getBody() {
                            try {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("username_pembeli", username);
                                jsonBody.put("password_pembeli", password);

                                return jsonBody.toString().getBytes("utf-8");
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return new byte[0];
                            }
                        }

                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                    // Menutup aktivitas setelah menekan tombol ganti
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Mohon Isi Data Yang Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            }
        });



        // Mengganti fungsi tombol untuk pindah ke MainActivity
//        Button btn_back = findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                // Menutup aktivitas setelah berpindah ke MainActivity
//                finish();
//            }
//        });
    }
}
