package Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.millenialzdev.CedasAPK.Db_Contract;
import com.millenialzdev.CedasAPK.R;
import com.millenialzdev.CedasAPK.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import model.Register;

public class SettingFragment extends Fragment {

    private EditText usernameTextView, usernameatas, txtemal, txtemailatas,txttang,txtnohap,txtalam,txtidus;
    DatePickerDialog picker;
    private Spinner genderspinner;
    private String selectedGender;
    private SharedPreferences sharedPreferences;
    Gson gson = new Gson();
    ImageView imageprofil;
    private RequestQueue requestQueue;
    private Button btn_ganti, btn_keluar;
    private StringRequest stringRequest;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 101;
    private String[] genderOptions = {"Laki-laki", "Perempuan"};
    private byte[] selectedImageByteArray;
    private static final long MAX_IMAGE_SIZE = 4 * 1024 * 1024;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Initialize UI elements
        usernameTextView = view.findViewById(R.id.txtusernama);
        usernameatas = view.findViewById(R.id.txtnamatas);
        imageprofil = view.findViewById(R.id.imageprofil);
        txtemal = view.findViewById(R.id.txtemal);
        txtemailatas = view.findViewById(R.id.txtemailatas);
        genderspinner = view.findViewById(R.id.txtjk);
        txttang = view.findViewById(R.id.txttang);
        txtnohap = view.findViewById(R.id.txtnohap);
        txtalam = view.findViewById(R.id.txtalam);
        btn_ganti = view.findViewById(R.id.btn_ganti);
        txtidus = view.findViewById(R.id.txtidus);
        btn_keluar = view.findViewById(R.id.btn_hapus);

        // Retrieve username from SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Set the retrieved username in the TextView

        usernameatas.setText(username);
        showProfile(username);

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), loginActivity.class);
                startActivity(intent);
            }
        });

        btn_ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String email = txtemal.getText().toString();
                String nohp = txtnohap.getText().toString();
                String tanggal = txttang.getText().toString();
                String alama = txtalam.getText().toString();
                String namaatas = usernameatas.getText().toString();
                String jk = genderspinner.getSelectedItem().toString();


                if (!username.isEmpty() && !email.isEmpty() && !nohp.isEmpty()) {
                    // Buat permintaan ke server untuk menyimpan data
                    saveDataToServer(username, email, nohp, tanggal, alama, namaatas, jk);
                } else {
                    Toast.makeText(requireContext(), "Mohon Isi Data Yang Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txttang.setInputType(InputType.TYPE_NULL);
        txttang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // Gunakan requireActivity() sebagai konteks
                picker = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txttang.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, year, month, day);
                picker.show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderOptions);
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

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                    Manifest.permission.CAMERA
            }, CAMERA_REQUEST_CODE);
        }

        imageprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageSource();
            }
        });

        return view;
    }

    private void showProfile(String username) {
        StringRequest request = new StringRequest(Request.Method.GET, Db_Contract.urldetail + "?username_pembeli=" +username , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Register register = gson.fromJson(response, Register.class);

                if (register != null && register.getRegisterList().size() > 0) {
                    // Set email to TextView
                    usernameTextView.setText(register.getRegisterList().get(0).getNamaPembeli());
                    txtemal.setText(register.getRegisterList().get(0).getEmailPembeli());
                    txtemailatas.setText(register.getRegisterList().get(0).getEmailPembeli());
                    String selectedJenisKelamin = register.getRegisterList().get(0).getJenisKelaminPem();
                    int position = getIndex(genderspinner, selectedJenisKelamin);

                    if (position != -1) {
                        genderspinner.setSelection(position);
                    }
                    txttang.setText(register.getRegisterList().get(0).getTglLahirPem());
                    txtnohap.setText(register.getRegisterList().get(0).getNoHpPembeli());
                    txtalam.setText(register.getRegisterList().get(0).getAlamatPembeli());

                    // Set image to ImageView using Glide
                    byte[] gambarBlob = register.getRegisterList().get(0).getGambarBlob();
                    String base64Image = new String(gambarBlob, StandardCharsets.UTF_8);
                    String imageUrl = "data:image/jpeg;base64," + base64Image;

                    Glide.with(requireContext())
                            .load(imageUrl)
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.basreng1)
                                    .error(R.drawable.gambar1)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .transforms(new CenterCrop(), new CircleCrop())) // Combine CenterCrop and CircleCrop
                            .into(imageprofil);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    private String convertByteArrayToUrl(byte[] byteArray) {
        return "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return -1;
    }

    private void selectImageSource() {
        final CharSequence[] options = {"Kamera", "Galeri"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Ambil URI gambar dari galeri
            android.net.Uri selectedImageUri = data.getData();
            try {
                // Ubah URI menjadi byte array
                selectedImageByteArray = getByteArrayFromUri(selectedImageUri);
                // Tampilkan gambar di ImageView
                Glide.with(requireContext())
                        .load(selectedImageUri)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop())
                        .into(imageprofil);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Ambil bitmap dari hasil kamera
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            // Ubah bitmap menjadi byte array
            selectedImageByteArray = getByteArrayFromBitmap(bitmap);
            // Tampilkan gambar di ImageView
            Glide.with(requireContext())
                    .load(bitmap)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .circleCrop())
                    .into(imageprofil);
        }
    }

    private byte[] getByteArrayFromUri(android.net.Uri uri) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
        // Ubah ukuran gambar menjadi sesuai kebutuhan

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Tidak perlu mengubah ukuran atau melakukan kompresi
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private Bitmap resizeBitmap(Bitmap originalBitmap) {
        // Sesuaikan ukuran gambar sesuai kebutuhan
        int targetWidth = 800;
        int targetHeight = (int) (originalBitmap.getHeight() * (targetWidth / (double) originalBitmap.getWidth()));
        return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true);
    }

    private void saveDataToServer(String username, String email, String nohp, String tanggal, String alama, String namaatas, String jk) {
        if (selectedImageByteArray != null && selectedImageByteArray.length > 0) {
            // Cek ukuran gambar sebelum mengirim ke server
            if (selectedImageByteArray.length > MAX_IMAGE_SIZE) {
                Toast.makeText(requireContext(), "Ukuran gambar terlalu besar", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Buat permintaan ke server untuk menyimpan data termasuk gambar
        stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urldetail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();
                Log.e("", response.toString());

                // Lakukan tindakan lain setelah respon berhasil
                // Sebagai contoh, tutup fragment
                getParentFragmentManager().popBackStack();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Volley Error", error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("username_pembeli", namaatas);
                    jsonBody.put("nama_pembeli", username);
                    jsonBody.put("email_pembeli", email);
                    jsonBody.put("no_hp_pembeli", nohp);
                    jsonBody.put("tgl_lahir_pem", tanggal);
                    jsonBody.put("alamat_pembeli", alama);
                    jsonBody.put("jenis_kelamin_pem", jk);
                    if (selectedImageByteArray != null) {
                        jsonBody.put("gambar_pembeli", Base64.encodeToString(selectedImageByteArray, Base64.DEFAULT));
                    }

                    return jsonBody.toString().getBytes("utf-8");
                } catch (JSONException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return new byte[0];
                }
            }

            @Override
            public String getBodyContentType() {
                // Set content type to "application/json" for JSON data
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }
}