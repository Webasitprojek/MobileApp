package Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.millenialzdev.CedasAPK.DatabaseHelper;
import com.millenialzdev.CedasAPK.KeranjangAdapter;
import com.millenialzdev.CedasAPK.KeranjangItem;
import com.millenialzdev.CedasAPK.R;
import com.millenialzdev.CedasAPK.UploadbuktiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KeranjangFragment extends Fragment {

    private TextView btnpesan;
    private EditText userpembeli, numberItemsTxt, hargakeranjang, namakeranjang, idkeranjang, totalFeeTxt;
    private DatabaseHelper databaseHelper;
    private List<KeranjangItem> keranjangItems;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private KeranjangAdapter keranjangAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);
        View vi = inflater.inflate(R.layout.itemkeranjang_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerView4);
        btnpesan = view.findViewById(R.id.btnpesan);
        numberItemsTxt = vi.findViewById(R.id.numberItemsTxt);
        hargakeranjang = vi.findViewById(R.id.Hargakeranjang);
        namakeranjang = vi.findViewById(R.id.Namakeranjang);
        idkeranjang = vi.findViewById(R.id.Idkeranjang);
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);

        userpembeli = view.findViewById(R.id.Userpembeli);
        sharedPreferences = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        userpembeli.setText(username);
        keranjangItems = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getContext());

        keranjangAdapter = new KeranjangAdapter(keranjangItems, totalFeeTxt, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(keranjangAdapter);

        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        updateTotalFee();

        Cursor cursor = databaseHelper.getAllUserData();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int namaIndex = cursor.getColumnIndex("nama");
            int hargaIndex = cursor.getColumnIndex("harga");
            int jumlahIndex = cursor.getColumnIndex("jumlah");

            if (namaIndex != -1 && hargaIndex != -1 && jumlahIndex != -1 && idIndex != -1) {
                String id = cursor.getString(idIndex);
                String nama = cursor.getString(namaIndex);
                String harga = cursor.getString(hargaIndex);
                String jumlah = cursor.getString(jumlahIndex);

                KeranjangItem keranjangItem = new KeranjangItem(nama, harga, jumlah, id);
                keranjangItems.add(keranjangItem);
            }
        }
        cursor.close();

        keranjangAdapter = new KeranjangAdapter(keranjangItems, totalFeeTxt, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(keranjangAdapter);

        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keranjangItems = new ArrayList<>();
                databaseHelper = new DatabaseHelper(getContext());
                Cursor cursor = databaseHelper.getAllUserData();

                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("id");
                    int namaIndex = cursor.getColumnIndex("nama");
                    int hargaIndex = cursor.getColumnIndex("harga");
                    int jumlahIndex = cursor.getColumnIndex("jumlah");

                    if (namaIndex != -1 && hargaIndex != -1 && jumlahIndex != -1 && idIndex != -1) {
                        String id = cursor.getString(idIndex);
                        String nama = cursor.getString(namaIndex);
                        String harga = cursor.getString(hargaIndex);
                        String jumlah = cursor.getString(jumlahIndex);

                        KeranjangItem keranjangItem = new KeranjangItem(nama, harga, jumlah, id);
                        keranjangItems.add(keranjangItem);
                    }
                }

                keranjangAdapter = new KeranjangAdapter(keranjangItems, totalFeeTxt, recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(keranjangAdapter);

                String usernamepem = userpembeli.getText().toString();
                String totalharga = totalFeeTxt.getText().toString();

                // Membuat JSONObject untuk menyimpan data
                JSONObject jsonObject = new JSONObject();

                // Memasukkan data ke dalam JSONObject
                try {
                    jsonObject.put("username_pembeli", usernamepem);
                    jsonObject.put("total_harga", totalharga);

                    // Memasukkan array barang ke dalam JSONObject
                    JSONArray jsonArrayBarang = new JSONArray();
                    for (KeranjangItem item : keranjangItems) {
                        JSONObject jsonItem = new JSONObject();
                        jsonItem.put("id_barang", item.getId());
                        jsonItem.put("nama_barang", item.getNama());
                        jsonItem.put("qty", item.getJumlah());
                        jsonItem.put("harga", item.getHarga());
                        jsonArrayBarang.put(jsonItem);
                    }
                    jsonObject.put("detail_transaksi", jsonArrayBarang);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Mengirim JSONObject ke server
                sendToServer(usernamepem, jsonObject);

            }
        });


        return view;
    }


    private List<KeranjangItem> getKeranjangItemsFromDatabase() {
        List<KeranjangItem> items = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllUserData();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int namaIndex = cursor.getColumnIndex("nama");
            int hargaIndex = cursor.getColumnIndex("harga");
            int jumlahIndex = cursor.getColumnIndex("jumlah");

            if (namaIndex != -1 && hargaIndex != -1 && jumlahIndex != -1 && idIndex != -1) {
                String id = cursor.getString(idIndex);
                String nama = cursor.getString(namaIndex);
                String harga = cursor.getString(hargaIndex);
                String jumlah = cursor.getString(jumlahIndex);

                KeranjangItem keranjangItem = new KeranjangItem(nama, harga, jumlah, id);
                items.add(keranjangItem);
            }
        }
        cursor.close();

        return items;
    }

    private void updateTotalFee() {
        int totalFee2 = 0;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            TextView hargaBarangTxt = itemView.findViewById(R.id.Hargakeranjang);

            // Mendapatkan nilai dari hargaBarangTxt di setiap item
            int harga = Integer.parseInt(hargaBarangTxt.getText().toString());
            totalFee2 += harga;
        }

        // Setel nilai totalFeeTxt dengan total biaya yang dihitung
        totalFeeTxt.setText(String.valueOf(totalFee2));
    }

    private void sendToServer(String usernamepem, JSONObject jsonObject) {
        // URL server endpoint
        String ip = "10.0.2.2";
        String serverUrl = "http://" + ip + "/phpdasar/cedasfixpol/cedasapi/Transaksi_Registration.php"; // Ganti dengan URL sesuai dengan server Anda

        // Buat RequestQueue untuk Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Buat objek JsonObjectRequest untuk POST request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, serverUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Tanggapi respon dari server setelah pengiriman berhasil
                try {
                    // Handle respon di sini sesuai kebutuhan
                    int code = response.getInt("code");
                    String status = response.getString("status");

                    // Contoh penanganan respon
                    if (code == 200) {
                        // Sukses
                        Toast.makeText(getContext(), "Sukses: " + status, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), UploadbuktiActivity.class);
                        startActivity(intent);

                    } else {
                        // Gagal, tampilkan pesan kesalahan
                        Toast.makeText(getContext(), "Error: " + status, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), UploadbuktiActivity.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Tanggapi kesalahan jika pengiriman gagal
                Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("", error.toString());
            }
        });

        // Menambahkan permintaan ke antrian
        requestQueue.add(jsonObjectRequest);
    }





}

