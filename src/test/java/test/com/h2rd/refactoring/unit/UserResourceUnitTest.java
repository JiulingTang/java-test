package test.com.h2rd.refactoring.unit;

import java.util.Arrays;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import com.h2rd.refactoring.web.UserResource;

import junit.framework.Assert;

import org.junit.Test;

import javax.ws.rs.core.Response;

public class UserResourceUnitTest {

    UserResource userResource = new UserResource();
    UserDao userDao = UserDao.getUserDao();

    @Test
    public void getUsersTest() throws Exception {
    	
        userDao.saveUser(new User("fake user", "fake@user.com", Arrays.asList("admin")));

        Response response = userResource.getUsers();
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void getUserTest() throws Exception {

        User user = new User("fake user2", "fake2@user.com", Arrays.asList("admin"));
        userDao.saveUser(user);

        Response response = userResource.findUser(user.getEmail());
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        
        response = userResource.findUser("nosuchemail@user.com");
        Assert.assertNull(response.getEntity());
    }
}