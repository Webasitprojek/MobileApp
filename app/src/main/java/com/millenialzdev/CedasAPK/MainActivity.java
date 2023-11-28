package com.millenialzdev.CedasAPK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigationrail.NavigationRailView;

import Fragment.HomeFragment;
import Fragment.KeranjangFragment;
import Fragment.ProdukFragment;
import Fragment.SettingFragment;
import Fragment.TransaksiFragment;

public class MainActivity extends AppCompatActivity implements NavigationRailView.OnItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
//    private HomeFragment homeFragment = new HomeFragment();
//    private SettingFragment settingFragment = new SettingFragment();
//    private ProdukFragment produkFragment = new ProdukFragment();
//    private KeranjangFragment keranjangFragment = new KeranjangFragment();
//    private TransaksiFragment transaksiFragment = new TransaksiFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomview);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);


    }
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.home) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.flfragment, homeFragment).commit();
//            return true;
//        }  else if (itemId == R.id.setting) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.flfragment, settingFragment).commit();
//            return true;
//        } else if (itemId == R.id.produk) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.flfragment, produkFragment).commit();
//            return true;
//        } else if (itemId == R.id.market) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.flfragment, keranjangFragment).commit();
//            return true;
//        }
//        return false;
//    }


}