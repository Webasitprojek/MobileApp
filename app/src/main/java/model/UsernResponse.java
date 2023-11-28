package model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UsernResponse{

	@SerializedName("code")
	private int code;

	@SerializedName("user_list")
	private List<UserListItem> userList;

	@SerializedName("status")
	private String status;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setUserList(List<UserListItem> userList){
		this.userList = userList;
	}

	public List<UserListItem> getUserList(){
		return userList;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}