package quiz_model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Activity implements Comparable<Activity>{
	List<String> info;
	Timestamp time;
	
	public List<String> getInfo() {
		return info;
	}

	public void setInfo(List<String> info) {
		this.info = info;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	
	public Activity(List<String> info, Timestamp time){
		this.info = new ArrayList<String>(info);
		this.time = new Timestamp(time.getTime());
	}

	@Override
	public int compareTo(Activity other) {
		if(time.after(other.getTime())) return -1;
		else return 1;
	}
	
	public String toString(){
		String time_str = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time);
		String sender = info.get(1);
		String sender_str = "<a href=\"CreateAccount_welcome.jsp?name=" + sender +"\">" + sender + "</a>";

		if(info.get(0).equals("take")){
			String id = info.get(2);
			String name = info.get(3);
			String quiz_str = "<a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender +"\">" + name + "</a>";
			String output = time_str + "	" + sender_str + " completes the quiz " + quiz_str;
			return output;
		}
		
		if(info.get(0).equals("create")){
			String id = info.get(2);
			String name = info.get(3);
			String quiz_str = "<a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender +"\">" + name + "</a>";
			String output = time_str + "	" + sender_str + " creates the quiz " + quiz_str;
			return output;
		}
		
		if(info.get(0).equals("achieve")){
			String achievement = info.get(2);
			//String name = info.get(3);
			//String quiz_str = "<a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender +"\">" + name + "</a>";
			String output = time_str + "	" + sender_str + " earns the achivement \"" + achievement + "\"";
			return output;
		}
		
		return "{" + time.toString() + "," + info.toString() + "}";
	}
}
