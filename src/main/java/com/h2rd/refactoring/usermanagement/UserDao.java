package com.h2rd.refactoring.usermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDao {

    private List<User> users;

    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
        	synchronized(UserDao.class){
        		if (userDao == null) {
        			ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
        		    		"classpath:/application-config.xml"	
        		    	});
        			userDao = context.getBean(UserDao.class);
        		}
        	}
        }
        return userDao;
    }

    public void saveUser(User user) throws Exception {
    	if (user.getEmail() == null || user.getEmail().isEmpty()) {
    		throw new Exception("Email should not be empty");
    	}
    	if (user.getRoles() == null || user.getRoles().isEmpty()) {
    		throw new Exception("There should be at least one role");    		
    	}
        if (findUser(user.getEmail()) != null)
        	throw new Exception("The email already exists");
        if (users == null) {
            users = new CopyOnWriteArrayList<User>();
        }
        users.add(user);
    }

    public List<User> getUsers() {
    	if (users != null)
    		return new ArrayList<User>(users);
    	else
    		return new ArrayList<User>();
    }

    public void deleteUser(String email) throws Exception {
    	if (email == null || email.isEmpty()) {
    		throw new Exception("Email should not be empty");
    	}
    	User user = findUser(email);
    	if (user == null) {  
    		throw new Exception("No user with the email");
    	}
    	users.remove(user);
	}
    	

    public void updateUser(User userToUpdate) throws Exception {
    	if (userToUpdate.getEmail() == null || userToUpdate.getEmail().isEmpty()) {
    		throw new Exception("Email should not be empty");
    	}
      	if (userToUpdate.getRoles() == null || userToUpdate.getRoles().isEmpty()) {
    		throw new Exception("There should be at least one role");    		
    	}
    	User user = findUser(userToUpdate.getEmail());
    	if (user == null) {  
    		throw new Exception("No user with the email");
    	}
        user.setName(userToUpdate.getName());
        user.setRoles(userToUpdate.getRoles());
    }

    public User findUser(String email) throws Exception {
    	if (email == null || email.isEmpty()){
    		throw new Exception("Email should not be empty");
    	}
        if (users != null)
	        for (User user : users) {
	            if (user.getEmail().equals(email)) {
	                return user;
	            }
	        }
        
        return null;
    }
}
