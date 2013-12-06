package quiz_model;

import java.sql.Timestamp;
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
}
