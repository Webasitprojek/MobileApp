package com.millenialzdev.CedasAPK;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import java.util.regex.Pattern;

import model.RegisterListItem;

public class RegisterActivity extends AppCompatActivity {

    DatePickerDialog picker;
    private EditText txtemail, txtusername, txtalamat, txtnohp, txtdate, txtpassword, confimpas, txtnama;
    private Button button_regis;
    private Spinner genderspinner;
    private String selectedGender;
    private TextView btnlogin;
    private String[] genderOptions = {"Laki-laki", "Perempuan", "Genderless", "Colorfull"};
    ImageView imageView;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnlogin = findViewById(R.id.btnlogin);
//        confimpas = findViewById(R.id.confimpas);

        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtalamat = findViewById(R.id.txtalamat);
        txtemail = findViewById(R.id.txtemail);
        txtnohp = findViewById(R.id.txtnohp);
        txtdate = findViewById(R.id.txtdate);
        txtnama = findViewById(R.id.txtnama);
        button_regis = findViewById(R.id.button_regis);
        genderspinner = findViewById(R.id.genderspin);

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@gmail\\.com");

        button_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kadang isi dari textfield berupa object
                String username = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                String alamat = txtalamat.getText().toString();
                String email = txtemail.getText().toString();
                String nohp = txtnohp.getText().toString();
                String nama = txtnama.getText().toString();
                String tglahir = txtdate.getText().toString();
                String gender = genderspinner.getSelectedItem().toString();
                byte[] imageBytes = convertImageToByteArray(imageView);
                String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Gson gson = new Gson();

                RegisterListItem user = new RegisterListItem(username, alamat, email, gender, password, nohp, tglahir, nama);

                if (!(username.isEmpty() || password.isEmpty() || alamat.isEmpty())){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                           final user = gson.toJson(response.toString());
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("",response.toString());
//                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("",error.toString());
                        }
                    })

                    {
                        @Override
                        public byte[] getBody() {
                            try {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("username_pembeli", username);
                                jsonBody.put("nama_pembeli", nama);
                                jsonBody.put("email_pembeli", email);
                                jsonBody.put("alamat_pembeli", alamat);
                                jsonBody.put("jenis_kelamin_pem", gender);
                                jsonBody.put("password_pembeli", password);
                                jsonBody.put("no_hp_pembeli", nohp);
                                jsonBody.put("tgl_lahir_pem", tglahir);
                                jsonBody.put("gambar_pembeli", base64Image);

                                return jsonBody.toString().getBytes("utf-8");
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return new byte[0]; // Return an empty byte array or handle the exception as needed
                            }
                        }



                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                }else{
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            }
        });


//        String email = txtemail.getText().toString();
//        if (!emailPattern.matcher(email).matches()) {
//            Toast.makeText(RegisterActivity.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
//            // Return from the function
//            return;
//        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(adapter);

        genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGender = genderOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        txtdate.setInputType(InputType.TYPE_NULL);
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //kata kata ketika data dipilih
                picker = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });



        imageView = findViewById(R.id.btcamera);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, CAMERA_REQUEST_CODE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setBackgroundColor(android.R.color.transparent);
                selectImageSource();
            }
        });



    }

    private void selectImageSource() {
        final CharSequence[] options = {"Kamera", "Galeri"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Sumber Gambar");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Kamera")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else if (options[item].equals("Galeri")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
                }
            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            android.net.Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    private byte[] convertImageToByteArray(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}