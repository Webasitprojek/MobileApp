










package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.millenialzdev.CedasAPK.Db_Contract;
import com.millenialzdev.CedasAPK.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.BarangListItem;

public class ProdukFragment extends Fragment {
    private RecyclerView rvProdukkabeh;
    private ArrayList<BarangListItem> dataBarang;
    private BarangAdapter adapter;

    public ProdukFragment() {
        // Konstruktor kosong yang diperlukan
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produkk, container, false);
        rvProdukkabeh = view.findViewById(R.id.rvProdukkabeh);
        rvProdukkabeh.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rvProdukkabeh.setHasFixedSize(true);

        dataBarang = new ArrayList<>();
        adapter = new BarangAdapter(getContext(), dataBarang);
        rvProdukkabeh.setAdapter(adapter);

        // Set listener untuk membuka Detail_barangFragment
        adapter.setOnItemClickListener(new BarangAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BarangListItem barang) {
                openDetailBarangFragment(barang);
            }
        });

        getdata();

        return view;
    }

    private void openDetailBarangFragment(BarangListItem barang) {
        Intent intent = new Intent(requireContext(), DetailbarangActivity.class);

        // Mengirim data ke DetailbarangActivity menggunakan Intent
        intent.putExtra("nama_barang", barang.getNamaBarang());
        intent.putExtra("harga", barang.getHarga());
//        intent.putExtra("gambar_blob", barang.getGambarBlob());
        // Jika diperlukan, kirim data lainnya ke DetailbarangActivity menggunakan putExtra

        // Memulai aktivitas untuk menampilkan DetailbarangActivity
        startActivity(intent);
    }

    private void getdata() {
        StringRequest request = new StringRequest(Request.Method.GET, Db_Contract.urlbarang2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("barang_list")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("barang_list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BarangListItem barangModel = new BarangListItem();
                            JSONObject data = jsonArray.getJSONObject(i);
                            barangModel.setNamaBarang(data.getString("nama_barang"));
                            barangModel.setHarga(data.getString("harga"));

                            String gambarBase64 = data.getString("gambar");
                            byte[] gambarBlob = android.util.Base64.decode(gambarBase64, android.util.Base64.DEFAULT);
                            barangModel.setGambarBlob(gambarBlob);

                            dataBarang.add(barangModel);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle kesalahan format JSON di sini
                        // Contoh: Toast.makeText(requireContext(), "Format JSON tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // Handle kesalahan parsing JSON
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                // Contoh: Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }
}
