package models;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Calendar;
import javax.servlet.ServletException;
import utils.DB;
import managers.ManageUsers;
import java.sql.Date;

public class User implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String user = "";

	private String name = "";
	private String surname = "";

	private String mail = "";
	private String tel = "";
	private Date dob = null;
	//private Date dob = new Date(System.currentTimeMillis());

	private String pwd1 = "";
	private String pwd2 = "";

	private String usertype = "Common user";
	private String about = null;
	private boolean isFollowed;

	private HashMap<String,Boolean> error = null;
	
	public User() {
		error = new HashMap<String, Boolean>();
		error.put("user", false);
		error.put("user_format", false);
		error.put("user_exists", false);
		error.put("name", false);
		error.put("surname", false);
		error.put("mail_format", false);
		error.put("mail_exists", false);
		error.put("tel_format", false);
		error.put("tel_exists", false);
		error.put("dob", false);
		error.put("pwd1_format", false);
		error.put("pwd2_format", false);
		error.put("pwd2_match", false);
		error.put("about", false);
	}
	
	/* ALL GETTERS */
	public int getId(){
		return this.id;
	}
	public String getUser(){
		return this.user;
	}
	public String getName(){
		return this.name;
	}
	public String getSurname(){
		return this.surname;
	}
	public String getMail(){
		return this.mail;
	}
	public String getTel(){
		return this.tel;
	}
	public Date getDob(){
		return this.dob;
	}
	public String getDob(){
		return this.dob;
	}
	public String getPwd1(){
		return this.pwd1;
	}
	public String getPwd2(){
		return this.pwd2;
	}
	public String getUsertype(){
		return this.usertype;
	}
	public String getAbout(){
		return this.about;
	}
	public boolean getIsfollowed(){
		return this.isFollowed;
	}
	
	/* ALL SETTERS */
	public void setId(int id){
		this.id = id;
	}
	public void setUser(String user){
		/* the username needs to follow a specific format */
		String regex = "^[a-z0-9._]{5,20}$";
		if(!user.matches(regex)){
			error.put("user_format",true);
			System.out.println("Username doesn't match format.");
		}
		/* the username already exists in our DB */
		else if(manager.userExists(user)){
			error.put("user_exists",true);
			System.out.println("Username already in our DB.");
		} else{
			this.user = user;
		}
	}
	public void setName(String name){
		String regex = "^[\\p{L}]{1,20}$"; // acentos permitidos para nombre
		if (!name.matches(regex)) {
			error.put("name", true);
			System.out.println("The name must have between 1 and 20 letters.");
		} else {
			this.name = name;
			System.out.println("Welcome: "+ name);
		}
	}
	public void setSurname(String surname){
		String regex = "^[\\p{L}]{1,20}$"; // acentos permitidos para apellidos
		if (!surname.matches(regex)) {
			error.put("surname", true);
			System.out.println("The surname must have between 1 and 20 letters.");
		} else {
			this.surname = surname;
		}
	}
	public void setMail(String mail) {
		/* the mail needs to follow a specific format */
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		if(!mail.matches(regex)){
			error.put("mail_format",true);
			System.out.println("Mail doesn't match format.");
		}
		/* the mail already exists in our DB */
		else if(manager.mailExists(mail)){
			error.put("mail_exists",true);
			System.out.println("Mail already in our DB.");
		} else{
			this.mail = mail;
		}
	}
	public void setTel(String tel) {
		/* the telephone needs to follow a specific format */
		String regex = "\\d{9}$";
		if(!tel.matches(regex)){
			error.put("tel_format",true);
			System.out.println("Telephone doesn't match format.");
		}
		/* the telephone already exists in our DB */
		else if(manager.telExists(tel)){
			error.put("tel_exists",true);
			System.out.println("Telephone already in our DB.");
		} else{
			this.tel = tel;
		}
	}
	public void setDob(Date dob) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(dob);
	    int age = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
	    if (age < 13) {
			error.put("dob", true);
	        System.out.println("User must be 13 or older to create an account.");
	    } else {
	        this.dob = dob;
			System.out.println("User "+name +"is "+age+" years old.");
	    }
	}
	public void setPwd(String pwd) {
		/* ST COMMENT:
		 * at least 6 characters, max 12
		 * must have a lower & an upper case letters
		 * must have a number & any symbols: @, $, !, %, *, ?, &
		 */

	    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{6,12}$";

		
	    if (pwd.matches(regex)) {
			this.pwd = pwd;
			System.out.println("Your password is: " + pwd);
		} else {
			error.put("pwd1_format", true);
			System.out.println("The password does not fulfill the restriction");			
		}
	}
	public void setPwd2(String pwd2) {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{6,12}$";	    
	    if (pwd2.matches(regex)) {
	    	// checking passwords match
	    	if (pwd != "" && pwd.equals(pwd2)) {
	    		this.pwd2 = pwd2;
	    		System.out.println("Your password is: " + pwd2);
	    	} else {
				error.put("pwd2_match", true);
	    		System.out.println("Passwords don't match");
	    	}
	    } else {
			error.put("pwd2_format", true);
	    	System.out.println("The password does not fulfill the restriction");
	    }
	}
	public void setUsertype(String usertype){
		//no debemos checkear nada ya que tendremos un menú
		this.usertype = usertype;
	}
	public void setAbout(String about){
		int len = about.length()
		if(len > 150){
			error.put("about",true);
			System.out.println("Biography is too long.");
		}else{
			this.about = about;
		}
	}
	public void setIsfollowed(boolean isFollowed){
		this.isFollowed = isFollowed;
	}

	/* OTHER FUNCTIONS */
	public HashMap<String,Boolean> getError() {
		return this.error;
	}
	public void setError(String name, boolean error) {
		this.error.put(name, error);
	}
}
