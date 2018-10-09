package test.com.h2rd.refactoring.integration;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.web.UserResource;

public class UserIntegrationTest {
	private UserResource userResource = new UserResource();
	
	@Test
	public void createUserTest() {
		
		User integration = new User("integration", "initial@integration.com", Arrays.asList("admin") );
        
        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(200, response.getStatus());
        
        response = userResource.findUser(integration.getEmail());
        Assert.assertNotNull(response.getEntity());
        
        response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(500, response.getStatus());
        
        userResource.addUser(integration.getName(), integration.getEmail(), new ArrayList<String>());
        Assert.assertEquals(500, response.getStatus());
	}

	@Test
	public void updateUserTest() {
		
		User integration = new User("integration2", "initial2@integration.com", Arrays.asList("admin") );
        
		userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
		
        userResource.updateUser("integration2!", integration.getEmail(), integration.getRoles());
        Response response = userResource.findUser(integration.getEmail());
        Assert.assertEquals(((User)response.getEntity()).getName(), "integration2!");
        
        response = userResource.updateUser("integration2!", "nosuchemail@integration.com", integration.getRoles());
        Assert.assertEquals(500, response.getStatus());
	}
	
	public void deleteUserTest() {
		
		User integration = new User("integration3", "initial3@integration.com", Arrays.asList("admin") );
	
		userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
		
		Response response = userResource.deleteUser( integration.getEmail());		
		Assert.assertNull(response.getEntity());
		
		response = userResource.deleteUser(integration.getEmail());
		Assert.assertEquals(500, response.getStatus());
	}
}
