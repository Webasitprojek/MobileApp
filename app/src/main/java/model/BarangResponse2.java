package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarangResponse2 {

	@SerializedName("code")
	private int code;

	@SerializedName("status")
	private String status;

	@SerializedName("barang_list")
	private List<BarangListItem> barangList;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setBarangList(List<BarangListItem> barangList){
		this.barangList = barangList;
	}

	public List<BarangListItem> getBarangList(){
		return barangList;
	}
}