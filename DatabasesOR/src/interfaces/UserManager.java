package interfaces;

import java.util.List;

import javax.management.relation.Role;

import pojos.User;

public interface UserManager {
	
	public void connect();
	
	public void disconnect();
	
	public void newUser(User u);
	
	public void newRole(Role r);
	
	public List <Role> getRoles();
	
	public User checkPassword (String email, String password);
	
	
	
	

}
