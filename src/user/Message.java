package user;

import java.util.Date;

public class Message implements Comparable<Message>  {
	private String sender;
	private String receiver;
	private String message;
	private Date sentDate;
	
	public Message(String sender, String receiver, String message, Date sentDate){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.sentDate = sentDate;
	}
	
	public String getsender() {
		return sender;
	}

	public String getreceiver() {
		return receiver;
	}

	public String getMessage() {
		return message;
	}

	public Date getSentDate() {
		return sentDate;
	}
	
	
	@Override
	public int compareTo(Message o) {
		
		if (o.getSentDate().after(sentDate)){
			return -1;
		}else{
			return 1;
		}
	}
}

