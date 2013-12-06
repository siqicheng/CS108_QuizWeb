package user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
	
	public static ArrayList<String> getFriends(String username){
		connect();
		ArrayList<String> friendlist = new ArrayList<String>();
		try {
						
			String query = "select friendname from friendTable where (username = " + "\"" + username +  "\"" +")";
			ResultSet rs = statement.executeQuery(query);//pst.executeQuery();
			while (rs.next()) {
				String friend = rs.getString("friendname");
				friendlist.add(friend);
		    }
			
			String query2 = "select username from friendTable where (friendname = " + "\"" + username +  "\"" +")";
			ResultSet rs2 = statement.executeQuery(query2);//pst.executeQuery();
			while (rs2.next()) {
				String friend = rs2.getString("username");
				friendlist.add(friend);
		    }
			
			close();
			return friendlist;
			
		} catch (SQLException e) {
			close();
			e.printStackTrace();
		}
		close();
		return friendlist;
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
			String query = "select UserName from userTable where UserName = \"" + username1 + "\"";
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
			query = "select username from friendRequestTable where UserName = \"" + username1 + "\" and FriendName = \"" + username2 + "\"";
			connect();
			rs = statement.executeQuery(query);
			if (rs.next()) {
				close();
				return false; //duplicate
		    }
			
			//Insert request
			Date SentTime = new Date();
			Timestamp ts = new Timestamp(SentTime.getTime());
			query = "insert into friendRequestTable values ('" + username1 + "','" + username2 + "','" + msg + "','" + ts + "')";
			
			connect();
			statement.executeUpdate(query);
			close();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
	}
	
	
	public static ArrayList<Friend_Request> getFriendRequests(String receivername){
		
		ArrayList<Friend_Request> list = new ArrayList<Friend_Request>();
		connect();
		try {
			
			String query = "select * from friendRequestTable where friendname = \"" + receivername + "\"";
			ResultSet rs = statement.executeQuery(query);
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
	
	public static void deleteFriendRequest(String sender, String receiver){
		connect();
		try {
			//Delete Challenge
			String query = "delete from friendRequestTable where username = \""+ sender + "\" and friendname = \"" + receiver + "\"";
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	public static void acceptFriendRequest(String user, String friend){
		connect();
		try {
			
			//Create relationship
			String query = "insert into friendTable values ('" + user + "','" + friend + "')";
			statement.executeUpdate(query);
			
			//Delete FriendRequest
			deleteFriendRequest(user, friend);
			
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	
	public static String getPrivacy(String username){
		connect();
		try {
			String query = "select username, isPrivate from privacyTable where (username = " + 
					"\"" + username + "\"" +")";
			
			
			ResultSet rs = statement.executeQuery(query);//pst.executeQuery();
			if (rs.next()) {
				String privacy = rs.getString("isPrivate");
				close();
				return privacy;
		    }else{
		    	createPrivacy(username);
		    }
			
			close();
			return "false";
			
		} catch (SQLException e) {
			close();
			e.printStackTrace();
		}
		close();
		return "false";
	}
	
	
	public static void setPrivacy(String username, String privacy){
		connect();
		try {
			//set hasRead from false to true
			String query = "update privacyTable set isPrivate =\""+ privacy + "\" where username = \"" + username + "\"";
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	public static void createPrivacy(String username){
		connect();
		try {			
			//Insert request
			String query = "insert into privacyTable values ('" + username + "','false')";
			statement.executeUpdate(query);
			close();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return;
	}
	
}
