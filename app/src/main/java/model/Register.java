package model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Register{

	@SerializedName("code")
	private int code;

	@SerializedName("register_list")
	private List<RegisterListItem> registerList;

	@SerializedName("status")
	private String status;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setRegisterList(List<RegisterListItem> registerList) {
		this.registerList = registerList;
	}

	public List<RegisterListItem> getRegisterList(){
		return registerList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}