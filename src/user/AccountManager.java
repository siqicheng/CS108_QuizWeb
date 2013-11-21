package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
	private Map<String, String> accountMap;
	public final static String ATTRIBUTE_NAME = "Account Manager";
	
	public AccountManager() {
		accountMap = new HashMap <String, String>();
		initialAccountData();
	}

	public void createNewAccount(String name, String password){
        accountMap.put(name,getHashCodeSHA(password));
    }
    
    public boolean hasAccount(String name){
        return accountMap.containsKey(name);
    }
    
    public boolean isCorrectAccount(String name, String password){
        return hasAccount(name) && accountMap.get(name).equals(getHashCodeSHA(password));
    }
    
    private void initialAccountData(){
        createNewAccount("Patrick", "1234");
        createNewAccount("Molly", "FloPup");
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
