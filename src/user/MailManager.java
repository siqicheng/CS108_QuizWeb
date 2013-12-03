package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import database_connection.DBConnection;


public class MailManager {
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
	
	public MailManager(){
		super();
	}
	
	public static boolean sendMessage(String username1, String username2, String msg){
		connect();
		//TODO
		msg.replaceAll("'", "\'");
		msg.replaceAll("\"", "\\\"");
		try {
			if (msg.length() == 0)
				return false;
			
			//Check sender exists
			String query = "select username from userTable where UserName = \"" + username1 + "\"";
			ResultSet rs = statement.executeQuery(query);
			if (!rs.next()) {
				close();
				return false; //recipient does not exist
		    }
			
			//Check recipient exists
			query = "select username from userTable where UserName = \"" + username2 + "\"";
			rs = statement.executeQuery(query);
			if (!rs.next()) {
				close();
				return false; //recipient does not exist
		    }
			
			
			//Insert msg
			Date SentTime = new Date();
			Timestamp ts = new Timestamp(SentTime.getTime());
			query = "insert into mailTable values ('" + username1 + "','" + username2 + "','" + msg + "','" + ts + "')";
			
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
	
	public static ArrayList<Message> getMessages(String receivername){

		ArrayList<Message> list = new ArrayList<Message>();
		connect();
		try {

			String query = "select * from mailTable where receiver = \"" + receivername + "\"";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				String sendername = rs.getString("sender");
				String message = rs.getString("message");
				Date date = rs.getTimestamp("senttime");

				Message m = new Message(sendername, receivername, message, date);


				list.add(m);
			}

			Collections.sort(list);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return list;
	}
	
	public static void deleteMessage(String sender, String receiver, String date){
		connect();
		try {
			//Delete message
			String query = "delete from mailTable where sender = \""+ sender + "\" and receiver = \"" + receiver + "\" and senttime = \"" + date + "\"";
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

}
