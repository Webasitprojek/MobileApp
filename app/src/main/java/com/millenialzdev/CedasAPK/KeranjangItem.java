package com.millenialzdev.CedasAPK;

public class KeranjangItem {
    private String nama;
    private String harga;
    private String jumlah;
    private String id;


    public KeranjangItem(String nama, String harga, String jumlah, String id) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
