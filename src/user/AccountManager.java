package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.tools.javac.util.List;

import database_connection.DBConnection;

public class AccountManager {
	
	public final static String ATTRIBUTE_NAME = "Account Manager";
	public static List<String> protectedNameList;
	private static Statement statement; 
	
	public AccountManager() {
		addProtectedName();
		initialAccountData();
	}

	private static void connect() {
		statement = (new DBConnection()).getStatement();
	}
	
	public static void close() {
		DBConnection.closeConnection();
	}
	
	public static boolean createNewAccount(String UserName, String Password, String Status, char Gender, String Email){
		if (isProtectedName(UserName)) return false;
		connect();
		try {
			
			String query = "INSERT INTO userTable VALUES (\""
				+ UserName.toLowerCase() + "\", \"" + getHashCodeSHA(Password) + "\", \"" + Status + "\", \"" + Gender + "\", \"" + Email +  "\");";
			System.out.print(query);
		statement.executeUpdate(query);
		
		} catch (SQLException e) {
			close();
			return false;
		}
		close();
		return true;
    }
    
    public static boolean hasAccount(String name){
    	if (isProtectedName(name)) return false;
    	connect();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM userTable " +
					"WHERE UserName = \"" + name + "\"");
			if(rs.next()) {
				close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
    }
    
    public static boolean isCorrectAccount(String name, String password){
    	if (!hasAccount(name)) return false;
    	connect();
		try {
			ResultSet rs = statement.executeQuery("SELECT * FROM userTable " +
					"WHERE UserName = \"" + name + "\" AND password = \"" + getHashCodeSHA(password) + "\"");
			
			if(rs.next()) {
				close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return false;
    }
    
    private void initialAccountData(){
        createNewAccount("Patrick", getHashCodeSHA("1234"),"s",'m',"siqicheng.fdu@gmail.com");
        createNewAccount("Molly", getHashCodeSHA("1111"),"s",'f',"siqicheng.fdu@gmail.com");
    }
    
    private static boolean isProtectedName(String UserName){
    	return false;
//    	if(protectedNameList.contains(UserName)) return true;
//    	return false;
    }
    
    private void  addProtectedName(){
		protectedNameList.add("guest");
		protectedNameList.add("admin");
    }
    
    
	/*	Password to hashcode.
	 *	Use MessageDigest in SHA mode and get the hashcode
	 */
	private static String getHashCodeSHA(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
	        md.update(password.getBytes());
	        return hexToString(md.digest()); 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}
