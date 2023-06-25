package models;

import java.util.HashMap;
import managers.ManageUsers;

public class Login {

	private String username = "";
	private String pwd = "";
	private boolean rememberMe = false;
	
	private HashMap<String,Boolean> error = null;
	
	public Login (){
		error = new HashMap<String, Boolean>();
		error.put("username", false);
		error.put("pwd", false);
	}

	ManageUsers manager = new  ManageUsers();
	
	/* GETTERS */
	public String getUsername() {
		return this.username;
	}

	public String getPwd() {
		return this.pwd;
	}
	
	/* SETTERS */
	public void setUsername(String username) {
		if (!manager.checkUser(username)) {
			error.put("username", true);
			System.out.println("The username doesn't exist.");
		} else {
			this.username = username;
			System.out.println("Welcome: "+username);
		}
	}

	public void setPwd(String pwd) {
		if (manager.loginUser(getUsername(), pwd)) {
			this.pwd = pwd;
			System.out.println("User is logged in successfully!");
		} else {
			error.put("pwd", true);
			System.out.println("Username or password is incorrect....");
		}
	}
	
	/* OTHER FUNCTIONS */
	public HashMap<String,Boolean> getError() {
		return this.error;
	}
	
	public void setError(String name, boolean error) {
		this.error.put(name, error);
	}
	
	public boolean isComplete() {
		if(hasValue(getUsername()) &&
			hasValue(getPwd())) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
	
	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		System.out.println("remembered");
		this.rememberMe = rememberMe;
	}
}