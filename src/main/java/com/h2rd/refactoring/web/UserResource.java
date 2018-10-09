package com.h2rd.refactoring.web;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Repository
public class UserResource{

    @GET
    @Path("add/")
    public Response addUser(@QueryParam("name") String name,
                            @QueryParam("email") String email,
                            @QueryParam("role") List<String> roles)  {
    	
    	User user = new User(name, email, roles);

        UserDao userDao = UserDao.getUserDao();
        
        try {
			userDao.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
        return Response.ok().build();
    }

    @GET
    @Path("update/")
    public Response updateUser(@QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("role") List<String> roles) {

        User user = new User(name, email, roles);

        UserDao userDao = UserDao.getUserDao();
            
        try {
			userDao.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
        return Response.ok().build();
    }

    @GET
    @Path("delete/")
    public Response deleteUser(@QueryParam("email") String email) {

        UserDao userDao = UserDao.getUserDao();

        try {
			userDao.deleteUser(email);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
        return Response.ok().build();
    }

    @GET
    @Path("find/")
    public Response getUsers() {
    	UserDao userDao = UserDao.getUserDao();
    	
    	List<User> users = userDao.getUsers();
    	if (users == null) {
    		users = new ArrayList<User>();
    	}

        GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(usersEntity).build();
    }

    @GET
    @Path("search/")
    public Response findUser(@QueryParam("email") String email) {
    	UserDao userDao = UserDao.getUserDao();
    	try {
    		User user = userDao.findUser(email);
            return Response.ok().entity(user).build();
    	}catch (Exception e) {
    		e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
    }

}
