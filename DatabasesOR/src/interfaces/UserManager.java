package interfaces;

import java.util.List;

import pojos.*;

public interface UserManager {
	
	
	public void disconnect();
	
	public void newUser(User u);
	
	public void newRole(Role r);
	
	public List <Role> getRoles();
	
	public User checkPassword (String email, String password);

	public Role getRole(String name);
	
	
	
	
	

}
