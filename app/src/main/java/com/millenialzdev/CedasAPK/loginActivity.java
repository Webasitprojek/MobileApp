package com.millenialzdev.CedasAPK;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import model.Register;

public class loginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView btnRegister, btnlupa;
    private Button btnLogin;
    ImageView passwordVisibility;
    ImageView imageView;
    private SharedPreferences sharedPreferences;
    EditText txt_user;
    CheckBox rememberCheckBox;
    EditText txtPassword;
    boolean isPasswordVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.txt_username);
        etPassword = findViewById(R.id.txt_pass);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.bt_login);
        btnlupa = findViewById(R.id.btnlupa);
        sharedPreferences = getSharedPreferences("username", Context.MODE_PRIVATE);


        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString();
                if (username.length() < 3) {
                    etUsername.setError("Username harus memiliki panjang minimal 3 karakter");
                } else if (username.length() > 32) {
                    etUsername.setError("Username harus memiliki panjang maksimal 32 karakter");
                } else {
                    etUsername.setError(null);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pass = s.toString();
                if (pass.length() < 3) {
                    etPassword.setError("Password harus memiliki panjang minimal 3 karakter");
                } else if (pass.length() > 32) {
                    etPassword.setError("Password harus memiliki panjang maksimal 32 karakter");
                } else {
                    etPassword.setError(null);
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        btnlupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), lupapasActivity.class));
            }
        });


        passwordVisibility = findViewById(R.id.passwordVisibility);


        passwordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    etPassword.setInputType(129);
                    passwordVisibility.setImageResource(R.drawable.eyeclose);
                } else {
                    etPassword.setInputType(128);
                    passwordVisibility.setImageResource(R.drawable.eyeopen);
                }

                isPasswordVisible = !isPasswordVisible;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Gson gson = new Gson();

                if (!(username.isEmpty() || password.isEmpty())) {

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Db_Contract.urlLogin + "?username_pembeli=" + username + "&password_pembeli=" + password, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                try {
                                    Register register = gson.fromJson(response, Register.class);
                                    if (register.getCode() == 200) {
                                        Toast.makeText(getApplicationContext(), "Selamat Datang : " + register.getRegisterList().get(0).getUsernamePembeli(), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        intent.putExtra("username", username);
                                        saveUsernameToSharedPreferences(username);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    Log.e("", response + e.getMessage());
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Password Atau Username Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void saveUsernameToSharedPreferences(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putBoolean("isLogin",true);
        editor.apply();
    }
}
