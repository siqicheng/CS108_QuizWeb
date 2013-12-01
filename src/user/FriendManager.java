package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;


import database_connection.DBConnection;

public class FriendManager {
	private static Statement statement;
	private static Connection db;
	
	
//	private static void connect(){
//		statement = (new DBConnection()).getStatement();
//	}
	
	public static void close() {
		DBConnection.closeConnection();
	}
	
	public FriendManager(){
		DBConnection dbConnect = new DBConnection();
		db = dbConnect.getConnection();
		statement = dbConnect.getStatement();
	}
	
	
	// checks if username1 and username2 are friends
	public static boolean isFriend(String username1, String username2){
		//connect();
		try {
			
			String query = "select username from friendTable where (username = ? and friendname = ?) or (friendname = ? and username = ?)";

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
			query = "insert into friendRequestTable (`UserName`, `FriendName`, `Message`, `SentTime`) values (?,?,?,?)";
			pst = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, username1);
			pst.setString(2, username2);
			pst.setString(3,  msg);
			Date SentTime = new Date();
			Timestamp ts = new Timestamp(SentTime.getTime());
			pst.setTimestamp(4, ts);
			
			pst.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public List<Friend_Request> getFriendRequests(String receivername){
		
		List<Friend_Request> list = new ArrayList<Friend_Request>();
		
		try {
			
			String query = "select * from friendRequestTable where friendname = ?";
			PreparedStatement pst = db.prepareStatement(query);	
			pst.setString(1, receivername);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String senderName = rs.getString("username");
				String message = rs.getString("message");
				Date date = rs.getTimestamp("senttime");
				
				Friend_Request r = new Friend_Request(senderName, receivername, message, date);
				list.add(r);
		    }
			
			Collections.sort(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public void deleteFriendRequest(String sendername, String receivername){
		try {
			//Delete Challenge
			String query = "delete from friendRequestTable where username = ? and friendname = ?";
			PreparedStatement pst = db.prepareStatement(query);
			pst.setString(1, sendername);
			pst.setString(2, receivername);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void acceptFriendRequest(){
		
	}
	
}
