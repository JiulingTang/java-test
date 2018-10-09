package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;

import org.junit.Test;

import java.util.Arrays;

import junit.framework.Assert;

public class UserDaoUnitTest {

    @Test
    public void saveUserTest() throws Exception {
    	UserDao userDao = UserDao.getUserDao();
       
        userDao.saveUser(new User("Fake Name1", "fake1@email.com", Arrays.asList("admin", "master")));
        
        try {
        	userDao.saveUser(new User("Fake Name1", "fake1@email.com", Arrays.asList("admin", "master")));
        	Assert.fail();
        }
        catch (Exception e) {
        	
        }
        
        try {
        	userDao.saveUser(new User("Fake Name1", "", Arrays.asList("admin", "master")));
        	Assert.fail();
        }
        catch (Exception e) {
        	
        }
    }

    @Test
    public void deleteUserTest() throws Exception {
    	UserDao userDao = UserDao.getUserDao();

        User user = new User("Fake Name2", "fake2@email.com", Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        userDao.deleteUser(user.getEmail());
        
        Assert.assertNull(userDao.findUser(user.getEmail()));
        
    }
    
    @Test
    public void updateUserTest() throws Exception {
    	UserDao userDao = UserDao.getUserDao();

        User user = new User("Fake Name3", "fake3@email.com", Arrays.asList("admin", "master"));
        userDao.saveUser(user);
        
        user.setName("Fake Name3!");
        userDao.updateUser(user);
        
        Assert.assertEquals(userDao.findUser(user.getEmail()).getName(), "Fake Name3!");
    }
    
    @Test
    public void getUsersTest() throws Exception{
    	UserDao userDao = UserDao.getUserDao();
    	int num = userDao.getUsers().size();
    	
    	User user = new User("Fake Name4", "fake4@email.com", Arrays.asList("admin", "master"));
        userDao.saveUser(user);
        
        Assert.assertEquals(userDao.getUsers().size(), num + 1);
    }
}