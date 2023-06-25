package managers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import models.User;
import utils.DB;

public class ManageUsers {
	
	private DB db = null ;
	
	public ManageUsers() {
		try {
			db = new DB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			db.disconnectBD();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/* Get a user given its PK*/
	public User getUser(Integer id) {
		String query = "SELECT id,usr,name,mail,type,bio,bday FROM users WHERE id = ?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		User user = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,id);
			rs = statement.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUser(rs.getString("usr"));
				user.setName(rs.getString("name"));
				user.setMail(rs.getString("mail"));
				user.setType(rs.getString("type"));
				user.setBio(rs.getString("bio"));
				user.setBday(rs.getDate("bday"));
				//user.setPhone(rs.getString("phone"));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
		
	/* Add new user */
	public void addUser(User user) {
		String query = "INSERT INTO users (usr,name,mail,pwd,type,bio,bday) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getUser());
			statement.setString(2,user.getName());
			statement.setString(3,user.getMail());
			statement.setString(4,user.getPwd1());
			statement.setString(5, "Regular User");
			statement.setString(6, user.getBio());
			statement.setDate(7, user.getBday());
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Follow a user */
	public void followUser(Integer uid, Integer fid) {
		String query = "INSERT INTO follows (uid,fid) VALUES (?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,uid);
			statement.setInt(2,fid);
			
			statement.executeUpdate();
			statement.close();
			
			User user = new User();
			user = this.getUser(fid);
			user.setIsFollowed(true);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Unfollow a user */
	public void unfollowUser(Integer uid, Integer fid) {
		String query = "DELETE FROM follows WHERE uid = ? AND fid = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,uid);
			statement.setInt(2,fid);
			statement.executeUpdate();
			statement.close();
			
			User user = new User();
			user = this.getUser(fid);
			user.setIsFollowed(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/* Get a list with all the users */
	public List<User> getUsers(Integer start, Integer end) {
		 String query = "SELECT id,usr FROM users ORDER BY usr ASC LIMIT ?,?;";
		 PreparedStatement statement = null;
		 List<User> l = new ArrayList<User>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,start);
			 statement.setInt(2,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 User user = new User();
				 user.setId(rs.getInt("id"));
				 user.setName(rs.getString("usr"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	/* Get a list of not followed users given a user id */
	public List<User> getNotFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT id,usr FROM users WHERE id NOT IN (SELECT u.id FROM users u JOIN follows f ON u.id = f.fid AND f.uid = ?) AND id <> ? ORDER BY usr LIMIT ?,?;";
		 PreparedStatement statement = null;
		 List<User> l = new ArrayList<User>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,id);
			 statement.setInt(2,id);
			 statement.setInt(3,start);
			 statement.setInt(4,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 User user = new User();
				 user.setId(rs.getInt("id"));
				 user.setUser(rs.getString("usr"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	/* Get a list of followed users given a user id */
	public List<User> getFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT id,usr FROM users,follows WHERE id = fid AND uid = ? ORDER BY usr LIMIT ?,?;";
		 PreparedStatement statement = null;
		 List<User> l = new ArrayList<User>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,id);
			 statement.setInt(2,start);
			 statement.setInt(3,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 User user = new User();
				 user.setId(rs.getInt("id"));
				 user.setUser(rs.getString("usr"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	/* Update the values of the user profile with the new ones */
	public void editProfile(User user) {
		String query = "UPDATE users SET usr = ?, name = ?, mail = ?, bio = ?, bday = ? WHERE id = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getUser());
			statement.setString(2,user.getName());
			statement.setString(3,user.getMail());
			statement.setString(4,user.getBio());
			statement.setDate(5,user.getBday());
			statement.setInt(6,user.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Deletes a user given its id */
	public void deleteUser(Integer target_id) {
		String query = "DELETE FROM likes WHERE likes.usr=?;"; // Delete likes
		String query1 = "DELETE FROM tweets WHERE uid =?;"; // Delete the tweet
		String query2 = "DELETE FROM follows WHERE uid = ? or fid = ?;";
		String query3 = "DELETE FROM users WHERE id = ?;"; // Delete first the likes of that tweet(avoid fk errors)
		
		PreparedStatement statement = null;
		PreparedStatement statement1 = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;

		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,target_id);
			statement.executeUpdate();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement1 = db.prepareStatement(query1);
			statement1.setInt(1,target_id);
			statement1.executeUpdate();
			statement1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement2 = db.prepareStatement(query2);
			statement2.setInt(1,target_id);
			statement2.setInt(2,target_id);
			statement2.executeUpdate();
			statement2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement3 = db.prepareStatement(query3);
			statement3.setInt(1,target_id);
			statement3.executeUpdate();
			statement3.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/* Check given user of the session and another user, if the first follows the second one */
	public boolean isFollowed(Integer uid, Integer fid) {
		boolean isFollowed = false;
		String query = "SELECT * FROM follows WHERE uid= ? AND fid=?";
		PreparedStatement statement = null;
		try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,uid);
			 statement.setInt(2,fid);
			 ResultSet rs = statement.executeQuery();
			 if (rs.next()) { // If the query returns something
				 isFollowed = true;
			 } else {
				 isFollowed = false;
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return isFollowed;
	}
	
	/* Check if the user introduce the correct fields during login */
	public Pair<Boolean,User> checkLogin(User user) {
		String query = "SELECT id,name,mail,type,bio,bday from users where usr=? AND pwd=?";
		PreparedStatement statement = null;
		boolean output = false;			
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getUser());

			statement.setString(2,user.getPwd1());

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setMail(rs.getString("mail"));
				user.setType(rs.getString("type"));
				user.setBio(rs.getString("bio"));
				user.setBday(rs.getDate("bday"));
				output = true;
			} 
			rs.close();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Pair.of(output,user);
		
	}
	
	/* Check if the user exists given its username */
	public boolean checkUser(String user) {
		String query = "SELECT usr from users where usr=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean output = false;
		try {
			
			statement = db.prepareStatement(query);
			statement.setString(1,user);
			rs = statement.executeQuery();
			if (rs.isBeforeFirst()) {
				output = true;
			}
			rs.close();
			statement.close();
			return output;
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
		
	}
	
	/*Check if all the fields are filled correctly for updating user profile */
	public boolean isCompleteEdit(User user) {
		return(hasValue(user.getUser()) &&
		    	   hasValue(user.getName()) &&
		    	   hasValue(user.getMail()) &&
		    	   user.getBio() != null &&
		    	   correctBday(user.getBday()));
	}
	
	/* Checkif all the fields are filled during Login */
	public boolean isLoginComplete(User user) {
	    return(hasValue(user.getUser()) &&
	    	   hasValue(user.getPwd1())
	    	  );
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
		
	private boolean correctBday(Date date) {
		return (date.compareTo(new Date(System.currentTimeMillis())) != 0);
	}

	/*MODIFIED CODE EMPIEZA AQUÍ */

	/* ADDITIONAL FUNCTIONS WE NEED TO USE (User )*/
	public boolean userExists(String user) {
		/* We need to access the database and check if there's an user with that username */
		boolean exists = false;
		PreparedStatement ps = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM User WHERE username = ?");
	        ps.setString(1, user);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	int num = rs.getInt(1);
	        	if (num>0) exists=true;
	        }
	        rs.close();
	        ps.close();
	    } catch (SQLException e) {
	    	System.out.println("We are facing errors...");
	        e.printStackTrace();
	    }
	    return exists;
	}
	public boolean mailExists(String mail) {
		/* We need to access the database and check if there's an user with that email */
		boolean exists = false;
		PreparedStatement ps = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM User WHERE mail = ?");
	        ps.setString(1, mail);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	int num = rs.getInt(1);
	        	if (num>0) exists=true;
	        }
	        rs.close();
	        ps.close();
	    } catch (SQLException e) {
	    	System.out.println("We are facing errors...");
	        e.printStackTrace();
	    }
	    return exists;
	}
	public boolean telExists(String tel) {
		/* We need to access the database and check if there's an user with that telephone */
		boolean exists = false;
		PreparedStatement ps = null;
	    try {
	        ps = db.prepareStatement("SELECT COUNT(*) FROM User WHERE tel = ?");
	        ps.setString(1, tel);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	        	int num = rs.getInt(1);
	        	if (num>0) exists=true;
	        }
	        rs.close();
	        ps.close();
	    } catch (SQLException e) {
	    	System.out.println("We are facing errors...");
	        e.printStackTrace();
	    }
	    return exists;
	}
	public boolean isComplete(User user) {
		if(hasValue(user.getUser()) &&
			hasValue(user.getName()) &&
			hasValue(user.getSurname()) &&
			hasValue(user.getUsername()) &&
			hasValue(user.getMail()) &&
			hasValue(user.getTel()) &&
			has_DateValue(user.getDob()) && //modificar
			hasValue(user.getAbout()) &&
			hasValue(user.getUsertype()) &&
			hasValue(user.getPwd1()) &&
			hasValue(user.getPwd2())) {
			return true;
		} else {
			return false;
		}
	}
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
	private boolean has_DateValue(Date val) {
		return((val != null));
	}
}
