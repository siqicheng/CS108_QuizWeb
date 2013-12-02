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
	
	
	private static void connect(){
		DBConnection dbConnect = new DBConnection();
		db = dbConnect.getConnection();
		statement = dbConnect.getStatement();
	}
	
	public static void close() {
		DBConnection.closeConnection();
	}
	
	public FriendManager(){
//		DBConnection dbConnect = new DBConnection();
//		db = dbConnect.getConnection();
//		statement = dbConnect.getStatement();
	}
	
	
	// checks if username1 and username2 are friends
	public static boolean isFriend(String username1, String username2){
		connect();
		try {
			
			//String query = "select username from friendTable where (username = ? and friendname = ?) or (friendname = ? and username = ?)";

			//PreparedStatement pst = db.prepareStatement(query);	
//			pst.setString(1, "\"" + username1 + "\"");
//			pst.setString(2, "\"" + username2 + "\"");
//			pst.setString(3, "\"" + username2 + "\"");
//			pst.setString(4, "\"" + username1 + "\"");
			
			
//			pst.setString(1, username1);
//			pst.setString(2, username2);
//			pst.setString(3, username2);
//			pst.setString(4, username1);
			
			
			
//			pst.setString(1, "thefirst");
//			pst.setString(2, "thesecond");
//			pst.setString(3, "thefirst");
//			pst.setString(4, "thesecond");
			
			
			String query = "select username from friendTable where (username = " + 
					"\"" + username1 + "\"" +
					" and friendname = " +
					"\"" + username2 + "\"" +
					") or (friendname = " +
					"\"" + username1 + "\"" +
					" and username = " +
					"\"" + username2 + "\"" +")";
			
			
			ResultSet rs = statement.executeQuery(query);//pst.executeQuery();
			if (rs.next()) {
				close();
				return true;
		    }
			close();
			return false;
			
		} catch (SQLException e) {
			close();
			e.printStackTrace();
		}
		close();
		return false;
	}
	
	
	public static boolean isDuplicateFriendRequest(String username1, String username2){
		connect();
		try {
			String query = "select UserName from friendRequestTable where UserName = \"" +
					username1 + "\" and FriendName = \"" + username2 + "\"";
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				close();
				return true; //duplicate
			}
			close();
			return false;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
	}
	
	public static boolean sendFriendRequest(String username1, String username2, String msg){
		connect();
		try {

			//Check recipient exists
			String query = "select UserName from User_login where UserName = \"" + username1 + "\"";
			ResultSet rs = statement.executeQuery(query);
			if (!rs.next()) {
				close();
				return false; //recipient does not exist
		    }
			
			//Make sure they are not friends already
			if (FriendManager.isFriend(username1, username2)) {
				close();
				return false; //relationship already exists
		    }
			
			//Check for a duplicate friend request
			query = "select username from friendRequestTable where UserName = \"" +
					username1 + "\" and FriendName = \"" + username2 + "\"";
			rs = statement.executeQuery(query);
			if (rs.next()) {
				close();
				return false; //duplicate
		    }
			
			//Insert request
			Date SentTime = new Date();
			Timestamp ts = new Timestamp(SentTime.getTime());
			query = "insert into friendRequestTable values ('" + username1 +
					"','" + username2 + "','" + msg + "','" + ts + "')";

//			pst = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//			pst.setString(1, username1);
//			pst.setString(2, username2);
//			pst.setString(3,  msg);
//			pst.setTimestamp(4, ts);
			
			statement.executeUpdate(query);
			close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
	}
	
	
	public static List<Friend_Request> getFriendRequests(String receivername){
		
		List<Friend_Request> list = new ArrayList<Friend_Request>();
		connect();
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
		close();
		return list;
		
	}
	
	public static void deleteFriendRequest(String sendername, String receivername){
		connect();
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
		close();
	}
	
	public static void acceptFriendRequest(Friend_Request request){
		connect();
		try {
			
			//Create relationship
			String query = "insert into friendTable (`username`, `friendname`) values (?,?)";
			PreparedStatement pst = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);	
			pst.setString(1, request.getUserName());
			pst.setString(2, request.getFriendName());
			pst.executeUpdate();
			
			//Delete FriendRequest
			deleteFriendRequest(request.getUserName(), request.getFriendName());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	
	public static void rmFriendship(String username1, String username2){
		connect();
		try {
			
			//Delete Challenge
			
			String query = "delete from friendTable where (username = " + 
			"\"" + username1 + "\"" +
			" and friendname = " +
			"\"" + username2 + "\"" +
			") or (friendname = " +
			"\"" + username1 + "\"" +
			" and username = " +
			"\"" + username2 + "\"" +")";
	
	
			statement.executeUpdate(query);//pst.executeQuery();
			
			
			
			
//			String query = "delete from friendTable where (username = ? and friendname = ?) or (friendname = ? and username = ?)";
//			PreparedStatement pst = db.prepareStatement(query);
//			pst.setString(1, username1);
//			pst.setString(2, username2);
//			pst.setString(3, username1);
//			pst.setString(4, username2);
//			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	
}
