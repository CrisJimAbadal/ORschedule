package jpa;

import java.util.List;

import javax.management.relation.Role;

import interfaces.UserManager;
import pojos.User;

public class JPAUserManager implements UserManager {

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newRole(Role r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User checkPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
