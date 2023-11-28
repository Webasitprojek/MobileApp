package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.millenialzdev.CedasAPK.Db_Contract;
import com.millenialzdev.CedasAPK.R;
import com.millenialzdev.CedasAPK.Slide;
import com.millenialzdev.CedasAPK.SlideAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.BarangListItem;
import model.BarangListItem2;

public class HomeFragment extends Fragment {
    private RecyclerView rvProduk;
    private RecyclerView rvProduksemua;
    private ArrayList<BarangListItem> dataBarang;
    private ArrayList<BarangListItem2> dataBarang2;
    private BarangAdapter adapter;
    private BarangAdapter2 adapter2;

    private ViewPager viewPagerSlideshow;
    private SlideAdapter slideAdapter;
    private ArrayList<Slide> slideList;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 500; // Delay sebelum slide berikutnya (dalam milidetik)
    private final long PERIOD_MS = 3000; // Waktu antara setiap slide otomatis (dalam milidetik)

    private Handler handler = new Handler(Looper.getMainLooper());

    // Runnable untuk mengupdate tampilan slide
    private final Runnable update = new Runnable() {
        public void run() {
            if (currentPage == slideList.size()) {
                currentPage = 0;
            }
            viewPagerSlideshow.setCurrentItem(currentPage++, true);
        }
    };

    public HomeFragment() {
        // Konstruktor kosong yang diperlukan
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvProduk = view.findViewById(R.id.rvProduk);
        rvProduk.setHasFixedSize(true);
        rvProduksemua = view.findViewById(R.id.rvProduksemua);
        rvProduksemua.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProduk.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProduksemua.setLayoutManager(layoutManager2);

        // Initialize data for slideshow
        viewPagerSlideshow = view.findViewById(R.id.viewPager);
        slideList = new ArrayList<>();
        slideList.add(new Slide(R.drawable.slide_image_1));
        slideList.add(new Slide(R.drawable.slide_image_2));
        slideList.add(new Slide(R.drawable.slide_image_3));
        slideAdapter = new SlideAdapter(getContext(), slideList);
        viewPagerSlideshow.setAdapter(slideAdapter);

        // Start auto-scroll for slideshow
        startAutoScroll();

        dataBarang = new ArrayList<>();
        dataBarang2 = new ArrayList<>();  // Inisialisasi dataBarang2
        adapter = new BarangAdapter(getContext(), dataBarang);
        adapter2 = new BarangAdapter2(getContext(), dataBarang2);
        rvProduk.setAdapter(adapter);
        rvProduksemua.setAdapter(adapter2);

        adapter.setOnItemClickListener(new BarangAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BarangListItem barang) {
                openDetailBarangFragment(barang);
            }
        });

        getdata();
        getdata2();

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
        StringRequest request = new StringRequest(Request.Method.GET, Db_Contract.urlbarang, new Response.Listener<String>() {
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

    private void getdata2() {
        StringRequest request = new StringRequest(Request.Method.GET, Db_Contract.urlbarang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", "Response from urlbarang2: " + response); // Tambahkan log ini

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("barang_list")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("barang_list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BarangListItem2 barangModel2 = new BarangListItem2();
                            JSONObject data = jsonArray.getJSONObject(i);
                            barangModel2.setNamaBarang(data.getString("nama_barang"));
                            barangModel2.setHarga(data.getString("harga"));

                            String gambarBase64 = data.getString("gambar");
                            byte[] gambarBlob = android.util.Base64.decode(gambarBase64, android.util.Base64.DEFAULT);
                            barangModel2.setGambarBlob(gambarBlob);

                            dataBarang2.add(barangModel2);
                        }
                        adapter2.notifyDataSetChanged();
                    } else {
                        // Handle kesalahan format JSON di sini
                        Toast.makeText(requireContext(), "Format JSON tidak sesuai", Toast.LENGTH_SHORT).show();
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
                Log.e("VolleyError", "Error: " + error.getMessage()); // Tambahkan log ini
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

    private void startAutoScroll() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hentikan timer saat fragmen dihancurkan
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
