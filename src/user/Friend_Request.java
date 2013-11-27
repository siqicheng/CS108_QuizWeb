package user;

import java.util.*;


public class Friend_Request implements Comparable<Friend_Request>  {
	private String userName;
	private String friendName;
	private String message;
	private Date sentDate;
	
	public Friend_Request(String userName, String friendName, String message, Date sentDate){
		this.userName = userName;
		this.friendName = friendName;
		this.message = message;
		this.sentDate = sentDate;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getFriendName() {
		return friendName;
	}

	public String getMessage() {
		return message;
	}

	public Date getSentDate() {
		return sentDate;
	}
	
	
	@Override
	public int compareTo(Friend_Request o) {
		
		if (o.getSentDate().after(sentDate)){
			return -1;
		}else{
			return 1;
		}
	}
}
