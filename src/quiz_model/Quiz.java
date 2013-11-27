package quiz_model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Quiz {
	int id;
	String name;
	String createrId;
	Timestamp createTime;
	String tag;
	String category;
	boolean canPractice;
	boolean onePage;
	boolean immediateCorrection;
	
	ArrayList<Question> questions;
	
	public Quiz(int id, String name, String createrId, Timestamp createTime, String tag, 
			String category, boolean canPractice, boolean onePage, boolean immediateCorrection){
		this.id = id;
		this.name = name;
		this.createrId = createrId;
		this.createTime = new Timestamp(createTime.getTime());
		this.tag = tag;
		this.category = category;
		this.canPractice = canPractice;
		this.onePage = onePage;
		this.immediateCorrection = immediateCorrection;
	}
	
	public insertQISql(){
		String sql = "INSERT INTO QI VALUES(";
		 (0, "FirstQuizEver", "Siqi", "2013-11-20 04:21:07", "#CommonSense##CS#","CommonSense",false,false,true,false),

		sql += Integer.toString(id) + ","; /* ID */		
		
		sql += "\"" + name + "\","; /* quiz name */
		sql += "\"" + question + "\",";  /* Question */
		
		/* Answers */
		sql += "\"";
		for(String answer : answers){
			sql += "#" + answer + "#";
		}
		sql += "\",";
		
		sql += Integer.toString(5) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */
		
		return sql;	
	}
}
