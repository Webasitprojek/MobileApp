package com.millenialzdev.CedasAPK;

import com.google.gson.annotations.SerializedName;

public class UserProfileData {

	@SerializedName("password")
	private String password;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("id")
	private String id;

	@SerializedName("jenis_kelamin")
	private String jenisKelamin;

	@SerializedName("email")
	private String email;

	@SerializedName("tgl_lahir")
	private String tglLahir;

	@SerializedName("username")
	private String username;

	@SerializedName("alamat")
	private String alamat;

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