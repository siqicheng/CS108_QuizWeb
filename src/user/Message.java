package user;

import java.util.Date;

public class Message implements Comparable<Message>  {
	private String sender;
	private String receiver;
	private String message;
	private Date sentDate;
	private String hasRead;
	
	public Message(String sender, String receiver, String message, Date sentDate, String hasRead){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.sentDate = sentDate;
		this.hasRead = hasRead;
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
	
	public String gethasRead(){
		return hasRead;
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

