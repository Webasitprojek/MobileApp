package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("transaksi_list")
	private List<TransaksiListItem> transaksiList;

	@SerializedName("status")
	private String status;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setTransaksiList(List<TransaksiListItem> transaksiList){
		this.transaksiList = transaksiList;
	}

	public List<TransaksiListItem> getTransaksiList(){
		return transaksiList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}