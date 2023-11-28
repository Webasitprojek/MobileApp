package model;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class UserListItem{

	@SerializedName("password")
	private String password;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("id")
	private String id;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("email")
	private String email;

	@SerializedName("tgl_lahir")
	private String tglLahir;

	@SerializedName("username")
	private String username;

	@SerializedName("alamat")
	private String alamat;

	public class User {
		private String username;
		private String alamat;
		private String email;
		private String nohp;
		private String tanggal;

		// Tambahkan konstruktor yang sesuai
		public User(String username, String alamat, String email, String nohp, String tanggal) {
			this.username = username;
			this.alamat = alamat;
			this.email = email;
			this.nohp = nohp;
			this.tanggal = tanggal;
		}

		// Sisanya dari kelas User...
	}


	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setJenisKelamin(String jenisKelamin){
		this.jenisKelamin = jenisKelamin;
	}

	public String getJenisKelamin(){
		return jenisKelamin;
	}

	public void setGambarBlob(byte[] gambarBlob) {
		try {
			// Encode byte array to Base64 string with UTF-8 encoding
			this.gambar = Base64.encodeToString(gambarBlob, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTglLahir(String tglLahir){
		this.tglLahir = tglLahir;
	}

	public String getTglLahir(){
		return tglLahir;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}
}