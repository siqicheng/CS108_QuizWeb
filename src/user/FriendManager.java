package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import database_connection.DBConnection;

public class FriendManager {
	private static Statement statement;
	private static Connection db;
	
	
	private static void connect(){
		statement = (new DBConnection()).getStatement();
	}
	
	public static void close() {
		DBConnection.closeConnection();
	}
	
	
	public static boolean isFriend(String username1, String username2){
		connect();
		try {
			
			String query = "select username from friendTable where (username = ? and friendname = ?) or (friendname = ? and username = ?)";
			DBConnection dbConnect = new DBConnection();
			db = dbConnect.getConnection();
			PreparedStatement pst = db.prepareStatement(query);	
			pst.setString(1, username1);
			pst.setString(2, username2);
			pst.setString(3, username2);
			pst.setString(4, username1);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
		    }
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean isDuplicateFriendRequest(String username1, String username2){

		try {
			String query = "select UserName from friendRequestTable where UserName = ? and FriendName = ?";
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, username1);
			pst.setString(2, username2);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true; //duplicate
			}
			return false;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean sendFriendRequest(String username1, String username2, String msg){
		try {

			
			//Check recipient exists
			String query = "select UserName from User_login where UserName = ?";
			PreparedStatement pst = db.prepareStatement(query);	
			pst.setString(1, username1);
			ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				return false; //recipient does not exist
		    }
			
			//Make sure they are not friends already
			if (FriendManager.isFriend(username1, username2)) {
				return false; //relationship already exists
		    }
			
			//Check for a duplicate friend request
			query = "select username from friendRequestTable where username = ? and friendname = ?";
			pst = db.prepareStatement(query);	
			pst.setString(1, username1);
			pst.setString(2, username2);
			rs = pst.executeQuery();
			if (rs.next()) {
				return false; //duplicate
		    }
			
			//Insert request
			query = "insert into friendRequestTable (`UserName`, `FriendName`, `Message`) values (?,?,?)";
			pst = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			pst.setString(1, username1);
			pst.setString(2, username2);
			pst.setString(3,  msg);
			
			pst.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
