package model;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class RegisterListItem{

	@SerializedName("jenis_kelamin_pem")
	private String jenisKelaminPem;

	@SerializedName("nama_pembeli")
	private String namaPembeli;

	@SerializedName("tgl_lahir_pem")
	private String tglLahirPem;

	@SerializedName("alamat_pembeli")
	private String alamatPembeli;

	@SerializedName("password_pembeli")
	private String passwordPembeli;

	@SerializedName("no_hp_pembeli")
	private String noHpPembeli;

	@SerializedName("gambar_pembeli")
	private String gambarPembeli;

	@SerializedName("username_pembeli")
	private String usernamePembeli;

	@SerializedName("email_pembeli")
	private String emailPembeli;

	@SerializedName("id_franchise")
	private Object idFranchise;


//	public RegisterListItem(String username, String email, String alamat, String jenis_kelamin, String password, String no_hp, String tgl_lahir, String nama){
//		this.usernamePembeli = username;
//		this.emailPembeli = email;
//		this.alamatPembeli = alamat;
//		this.jenisKelaminPem = jenis_kelamin;
//		this.passwordPembeli = password;
//		this.noHpPembeli = no_hp;
//		this.tglLahirPem = tgl_lahir;
//		this.namaPembeli = nama;
//	}

	public RegisterListItem(String username, String email, String alamat, String jenis_kelamin, String password, String no_hp, String tgl_lahir, String nama) {
		this.usernamePembeli = username;
		this.emailPembeli = email;
		this.alamatPembeli = alamat;
		this.jenisKelaminPem = jenis_kelamin;
		this.passwordPembeli = password;
		this.noHpPembeli = no_hp;
		this.tglLahirPem = tgl_lahir;
		this.namaPembeli = nama;
	}

	public void setJenisKelaminPem(String jenisKelaminPem){
		this.jenisKelaminPem = jenisKelaminPem;
	}

	public String getJenisKelaminPem(){
		return jenisKelaminPem;
	}

	public void setNamaPembeli(String namaPembeli){
		this.namaPembeli = namaPembeli;
	}

	public String getNamaPembeli(){
		return namaPembeli;
	}

	public void setTglLahirPem(String tglLahirPem){
		this.tglLahirPem = tglLahirPem;
	}

	public String getTglLahirPem(){
		return tglLahirPem;
	}

	public void setAlamatPembeli(String alamatPembeli){
		this.alamatPembeli = alamatPembeli;
	}

	public String getAlamatPembeli(){
		return alamatPembeli;
	}

	public void setPasswordPembeli(String passwordPembeli){
		this.passwordPembeli = passwordPembeli;
	}

	public String getPasswordPembeli(){
		return passwordPembeli;
	}

	public void setNoHpPembeli(String noHpPembeli){
		this.noHpPembeli = noHpPembeli;
	}

	public String getNoHpPembeli(){
		return noHpPembeli;
	}

	public void setGambarBlob(byte[] gambarBlob) {
		try {
			// Encode byte array to Base64 string with UTF-8 encoding
			this.gambarPembeli = Base64.encodeToString(gambarBlob, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getGambarBlob() {
		try {
			// Decode Base64 string to byte array
			return Base64.decode(gambarPembeli, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setUsernamePembeli(String usernamePembeli){
		this.usernamePembeli = usernamePembeli;
	}

	public String getUsernamePembeli(){
		return usernamePembeli;
	}

	public void setEmailPembeli(String emailPembeli){
		this.emailPembeli = emailPembeli;
	}

	public String getEmailPembeli(){
		return emailPembeli;
	}

	public void setIdFranchise(Object idFranchise){
		this.idFranchise = idFranchise;
	}

	public Object getIdFranchise(){
		return idFranchise;
	}
}