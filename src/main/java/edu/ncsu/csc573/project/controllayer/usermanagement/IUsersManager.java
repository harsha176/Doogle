/**
 * 
 */
package edu.ncsu.csc573.project.controllayer.usermanagement;

/**
 * This acts as an interface for maintaining users.
 * 
 * @author doogle-dev
 *
 */
public interface IUsersManager {
	/**
	 * Allows to add user
	 * @param user
	 */
	public void addUser(IUser user);
	
	/**
	 * Allows to getUser details
	 * @return
	 */
	public IUser getUser();
	
	/**
	 * Deletes the user
	 * @param user_id
	 */
	public void deleteUser(int user_id);
	
	/**
	 * allows user to login to the system
	 * @param user_id
	 */
	public void userLogin(int user_id);
	
	/**
	 * allows user to logout
	 * @param user_id
	 */
	public void userLogout(int user_id);
	
	/**
	 * Checks if given has logged into the system.
	 */
	public boolean isUserLoggedin(String username);
	
}
