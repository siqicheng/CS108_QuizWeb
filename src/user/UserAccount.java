package user;

public class UserAccount {
	public final String userName;
	
	public UserAccount(String userName){
		this.userName = userName;
	}
	
	public boolean isCorrectAccount(String password){
		return AccountManager.isCorrectAccount(userName, password);
	}
	
	public String getUserType(){
		return AccountManager.getUserType(userName);
	}
	
	public boolean hasQuiz(String quizID) {
		return AccountManager.hasQuiz(quizID);
	}

}
