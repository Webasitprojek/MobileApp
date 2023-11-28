package model;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;


public class BarangListItem2 {

	@SerializedName("harga")
	private String harga;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("link")
	private String link;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("gambar")
	private String gambar;



	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public byte[] getGambarBlob() {
		try {
			// Decode Base64 string to byte array
			return Base64.decode(gambar, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setGambarBlob(byte[] gambarBlob) {
		try {
			// Encode byte array to Base64 string
			this.gambar = Base64.encodeToString(gambarBlob, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}