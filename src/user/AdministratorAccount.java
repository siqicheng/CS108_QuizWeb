package user;


public class AdministratorAccount extends UserAccount {

	public AdministratorAccount(String userName){
		super(userName);
		// TODO Auto-generated constructor stub
	}
	
	
	/*	In the AccountManager we can do the following 5 operations to announce
	 ****************************************************************************
	 * 	createAnnounce (String announce, String admin)
	 * 	getNewAnnounce return Announce
	 * 	getAllAnnounce return ArrayList<Announce>
	 * 	deleteAnnounce (String time, String admin)
	 * 	deleteAllAnnounce 
	 */
	public void setAnnouncement(String annoucement){
		AccountManager.createAnnounce(annoucement, this.userName);
	}
	
	public void deleteAccount(String userName) {
		AccountManager.deleteAccount(userName);
	}
	
	

}
