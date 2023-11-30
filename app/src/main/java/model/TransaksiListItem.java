package model;

import com.google.gson.annotations.SerializedName;

public class TransaksiListItem{

	@SerializedName("harga")
	private String harga;

	@SerializedName("status_pembayaran")
	private String statusPembayaran;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("qty")
	private String qty;

	@SerializedName("username_pembeli")
	private String usernamePembeli;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("bukti_pembayaran")
	private Object buktiPembayaran;

	public TransaksiListItem(String usernamepem, String idbarang, String namabarang, String totalharga, String hargasatuan, String jumlahbarang) {
		this.usernamePembeli = usernamepem;
		this.idBarang = idbarang;
		this.totalHarga = totalharga;
		this.namaBarang = namabarang;
		this.harga = hargasatuan;
		this.qty = jumlahbarang;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setStatusPembayaran(String statusPembayaran){
		this.statusPembayaran = statusPembayaran;
	}

	public String getStatusPembayaran(){
		return statusPembayaran;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setQty(String qty){
		this.qty = qty;
	}

	public String getQty(){
		return qty;
	}

	public void setUsernamePembeli(String usernamePembeli){
		this.usernamePembeli = usernamePembeli;
	}

	public String getUsernamePembeli(){
		return usernamePembeli;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setBuktiPembayaran(Object buktiPembayaran){
		this.buktiPembayaran = buktiPembayaran;
	}

	public Object getBuktiPembayaran(){
		return buktiPembayaran;
	}
}