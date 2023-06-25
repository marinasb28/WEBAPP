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
	
	/* not sure si esto es necesario */
	public DB db = null;
	ManageUsers manager = new ManageUsers();
	
	public void init() throws ServletException{
    	try {
    		db = new DB();
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("Connection failed in User.");
    	}
    }
	/* ESTO DE ARRIBA ES LO QUE HABRÍA QUE BORRAR SI NO FUNCIONA  */

	private static final long serialVersionUID = 1L;
	
	private int id = 0; //se le asignará automáticamente
	private String username = "";
	private String name = "";
	private String surname = "";
	private String phone = "";
	private String mail = "";
	private Date datebirth = null; // no tiene error inicial
	private String pwd = "";
	private String pwd2 = "";
	
	private String about = "";
	private int city = 0;
	private String gender = "";
	
	/*private ¿photo? picture = null; */

	private HashMap<String,Boolean> error = null;
	
	public User() {
		error = new HashMap<String, Boolean>();
		error.put("username_wrong_format", false);
		error.put("username_exists", false);
		error.put("name", false);
		error.put("surname", false);
		error.put("phone_wrong_format", false);
		error.put("phone_exists", false);
		error.put("mail_wrong_format", false);
		error.put("mail_exists", false);
		error.put("age", false);
		error.put("pwd_wrong_format", false);
		error.put("pwd2_wrong_format", false);
		error.put("pwd2_match", false);
		error.put("about", false);
		error.put("gender", false);
		
		/* LOS SIGUIENTES SON INTEGERS, ns si estrán bien */
		error.put("id",false);
		error.put("city",false);
	}
	
	/* GETTERS */
	public int getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public Date getDatebirth() {
		return this.datebirth;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	
	public String getPwd2() {
		return this.pwd2;
	}
	
	public String getAbout() {
		return this.about;
	}
	
	public int getCity() {
		return this.city;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	/* SETTERS */
	public void setId(int id) {
		String idString = String.valueOf(id);
		if (idString.matches("\\d+")) {
			this.id = id; // en el caso de que sea un número entero
		} else {
			error.put("id", true);
			System.out.println("El valor introducido no es válido. Debe ser un número entero.");
		}

	}
	
	public void setUsername(String username) {
		/* We can simulate that a user with the same name exists in our DB and mark error[0] as true  */
		String regex = "^[a-z0-9._]{5,20}$";
		if (!username.matches(regex)) {
			error.put("username_wrong_format", true);
			System.out.println("the username does not match the format of 5-20 character with at least one letter.");
		} else if (manager.checkUser(username)) {
			error.put("username_exists", true);
			System.out.println("The username has already been taken.");
		} else {
			this.username = username;
			System.out.println("Welcome: "+username);
		}
	}
	
	public void setName(String name) {
		String regex = "^[\\p{L}]{1,20}$"; //"^[a-zA-Z]{1,20}$";
	    if (!name.matches(regex)) {
			error.put("name", true);
	        System.out.println("The name must have between 1 and 20 letters.");
	    } else {
	    	this.name = name;
			System.out.println("Welcome: "+ name);
	    }
	}
	
	public void setSurname(String surname) {
		String regex = "^[\\p{L}]{1,20}$"; //"^[a-zA-Z]{1,20}$";
	    if (!surname.matches(regex)) {
			error.put("surname", true);
	        System.out.println("The surname must have between 1 and 20 letters.");
	    } else {
	    	this.surname = surname;
	    }
	}
	
	public void setPhone(String phone) {
		String regex = "\\d{9}$";		//here we need two \ because otherwise java recognises it as an escape sequence
	    if (!phone.matches(regex)) {
			error.put("phone_wrong_format", true);
			System.out.println("The phone's length MUST BE 9.");
	    } else if (manager.checkPhone(phone)) {
			error.put("phone_exists", true);
			System.out.println("The phone has already been used.");
	    } else {
	    	this.phone = phone;
	    }
	}
	
	public void setMail(String mail) {
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mail);
		
		if (matcher.matches()) {
			System.out.println("Mail matches the pattern");
			if (manager.checkMail(mail)) {
				error.put("mail_exists", true);
				System.out.println("The mail has already been used.");
			} else {
				this.mail = mail;
			}
		} else {
			error.put("mail_wrong_format", true);
			System.out.println("Mail doesn't match the pattern");
		}
	}
	
	public void setDatebirth(Date datebirth) {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(datebirth);
	    int age = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
	    if (age < 13) {
			error.put("datebirth", true);
	        System.out.println("User must be 13 or older to create an account.");
	    } else {
	        this.datebirth = datebirth;
			System.out.println(name +"is "+age+" years old");
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
			error.put("pwd_wrong_format", true);
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
			error.put("pwd2_wrong_format", true);
	    	System.out.println("The password does not fulfill the restriction");
	    }
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public void setCity(int city) {
		String idString = String.valueOf(city);
		if (idString.matches("\\d+")) {
			error.put("city", true);
			this.city = city; // en el caso de que sea un número entero
		} else {
			System.out.println("El valor introducido no es válido. Debe ser un número entero.");
		}

	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}


	/* OTHER FUNCTIONS */
	public HashMap<String,Boolean> getError() {
		return this.error;
	}
	
	public void setError(String name, boolean error) {
		this.error.put(name, error);
	}
		
}
