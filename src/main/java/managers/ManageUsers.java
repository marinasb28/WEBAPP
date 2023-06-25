package managers;

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
			System.out.println("DB connected");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB connection failed");
		}
	}
	
	public void finalize() {
		try {
			db.disconnectBD();
			System.out.println("DB disconnected");
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("DB disconnection failed");
		}
	}
	
	/* Get a user given its PK*/
	public User getUser(Integer id) {
		String query = "SELECT id,username,name,surname,mail,tel,dob,usertype,about FROM User WHERE id = ? ;";
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
				user.setUser(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setMail(rs.getString("mail"));
				user.setTel(rs.getString("tel"));
				user.setMail(rs.getString("mail"));
				user.setDob(rs.getString("dob"));
				user.setUsertype(rs.getString("usertype"));
				user.setAbout(rs.getString("about"));
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
		String query = "INSERT INTO User (username, name, mail, pwd, usertype, about, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getUser());
			statement.setString(2,user.getName());
			statement.setString(3,user.getMail());
			statement.setString(4,user.getPwd1());
			statement.setString(5,user.getUsertype());
			statement.setString(6, user.geAbout());
			statement.setDate(7, user.getDob());
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
		String query = "INSERT INTO Following (userId, followedId) VALUES (?, ?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,uid);
			statement.setInt(2,fid);
			
			statement.executeUpdate();
			statement.close();
			
			User user = new User();
			user = this.getUser(fid);
			user.setIsfollowed(true);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Unfollow a user */
	public void unfollowUser(Integer uid, Integer fid) {
		String query = "DELETE FROM Following WHERE userId = ? AND followedId = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,uid);
			statement.setInt(2,fid);
			statement.executeUpdate();
			statement.close();
			
			User user = new User();
			user = this.getUser(fid);
			user.setIsfollowed(false);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Get all the users */
	public List<User> getUsers(Integer start, Integer end) {
		 String query = "SELECT id,username FROM User ORDER BY username ASC LIMIT ?,?;";
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
				 user.setName(rs.getString("username"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}

	public List<User> getNotFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT id,username FROM User WHERE id NOT IN (SELECT u.id FROM User u JOIN Following f ON u.id = f.followedId AND f.userId = ?) AND id <> ? ORDER BY username LIMIT ?,?;";
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
				 user.setUser(rs.getString("username"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	public List<User> getFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT id,username FROM User,Following WHERE id = followedId AND userId = ? ORDER BY username LIMIT ?,?;";
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
				 user.setUser(rs.getString("username"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}

	public Pair<Boolean,User> checkLogin(User user) {
		
		String query = "SELECT id,mail from User where name=? AND pwd=?";
		PreparedStatement statement = null;
		boolean output = false;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getName());
			statement.setString(2,user.getPwd());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setMail(rs.getString("mail"));
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

	public Pair<Boolean,User> checkLogin(User user) {
		
		String query = "SELECT id,mail from User where name=? AND pwd=?";
		PreparedStatement statement = null;
		boolean output = false;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getName());
			statement.setString(2,user.getPwd1());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setMail(rs.getString("mail"));
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

	
	/* Update the values of the user profile with the new ones */
	public void editProfile(User user) {
		String query = "UPDATE User SET username = ?, `name` = ?, surname = ?, mail = ?, dob = ?, about = ? WHERE id = ?";


		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,user.getUser());
			statement.setString(2,user.getName());
			statement.setString(3,user.getSurname());
			statement.setString(4,user.getMail());
			statement.setString(5,user.getDob());
			statement.setDate(6,user.getAbout());
			statement.setInt(7,user.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Check everything is completed */
	public boolean editComplete(User user) {
		return(hasValue(user.getUser()) &&
		    	   hasValue(user.getName()) &&
				   hasValue(user.getSurname()) &&
		    	   hasValue(user.getMail()) &&
				   hasValue(user.getDob()) &&
				   hasValue(user.getAbout()) &&
		    	   user.getAbout() != null);
	}
	
	/* Delete an user - done by administrators and/or own user */
	public void deleteUser(Integer id) {
		String query1 = "DELETE FROM TweetLike WHERE TweetLike.userId=?;";
		String query2 = "DELETE FROM Tweet WHERE userId =?;";
		String query3 = "DELETE FROM Following WHERE userId = ? or followedId = ?;";
		String query4 = "DELETE FROM User WHERE id = ?;";
		
		PreparedStatement statement1 = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;
		PreparedStatement statement4 = null;

		try {
			statement1 = db.prepareStatement(query1);
			statement1.setInt(1,id);
			statement1.executeUpdate();
			statement1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement2 = db.prepareStatement(query2);
			statement2.setInt(1,id);
			statement2.executeUpdate();
			statement2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement3 = db.prepareStatement(query3);
			statement3.setInt(1,id);
			statement3.setInt(2,id);
			statement3.executeUpdate();
			statement3.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement4 = db.prepareStatement(query4);
			statement4.setInt(1,id);
			statement4.executeUpdate();
			statement4.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* Verify if the user associated with the current session is following another specified user */
	public boolean isFollowed(Integer uid, Integer fid) {
		boolean isFollowed = false;
		String query = "SELECT * FROM Following WHERE userId= ? AND followedId=?";
		PreparedStatement statement = null;
		try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,uid);
			 statement.setInt(2,fid);
			 ResultSet rs = statement.executeQuery();
			 if (rs.next()) {
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
	
	/* Checkif all the fields are filled during Login */
	public boolean LoginComplete(User user) {
	    return(hasValue(user.getUser()) &&
	    	   hasValue(user.getPwd1())
	    	  );
	}
}
