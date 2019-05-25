package com.paf.n3bg4.services;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.paf.n3bg4.dao.UserDao;
import com.paf.n3bg4.models.AuthRequest;
import com.paf.n3bg4.models.AuthResponse;
import com.paf.n3bg4.models.Enums.UserType;
import com.paf.n3bg4.models.User;
import com.paf.n3bg4.models.UserUpdateModel;

@Path("/user")
public class UserService {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> GetUserList() {
		UserDao userDao = new UserDao();
		try {
			ArrayList<User> userList = new ArrayList<User>();
			userList = userDao.getAllUsers();
			return userList;
		} finally {
			userDao.dispose();
		}
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public User GetUser(@PathParam("name") String name) {
		UserDao userDao = new UserDao();
		try {
			User user = new User();
			user = userDao.getUser(name);
			return user;
		} finally {
			userDao.dispose();
			System.out.println("[Info][UserService][GetUser] - userDao disposed.");
		}
	}
	
	@DELETE
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean DeleteUser(@PathParam("name") String name) {
		UserDao userDao = new UserDao();
		try {
			return userDao.deleteUser(name);
		} finally {
			userDao.dispose();
			System.out.println("[Info][UserService][GetUser] - userDao disposed.");
		}
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean AddUser(User user) {
		UserDao userDao = new UserDao();
		try {
			user.setIsActive(true);
			user.setUserType(UserType.Buyer);
			return userDao.addUser(user);
		} finally {
			userDao.dispose();
		}
	}

	@PUT
	@Path("")
	public boolean EditUser(UserUpdateModel user) {
		UserDao userDao = new UserDao();
		try {
			if (userDao.isRegisteredUser(user.getUsername())) {
				return userDao.updateUser(user);
			} else {
				return false;
			}
		} finally {
			userDao.dispose();
		}
	}

	@POST
	@Path("/{name}/address")
	public Response AddAddressStatus(@PathParam("name") String name) {

		String output = "Jersey say : ";

		return Response.status(200).entity(output).build();

	}

	@DELETE
	@Path("/{name}/address/{id}")
	public Response DeleteAddressStatus(@PathParam("name") String name, @PathParam("id") int id) {

		String output = "Jersey say : ";

		return Response.status(200).entity(output).build();

	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public AuthResponse Authenticate(AuthRequest authRequest) {

		UserDao userDao = new UserDao();
		try {
			AuthResponse authResponse = new AuthResponse();
			authResponse = userDao.authenticate(authRequest.getUsername(), authRequest.getPassword());
			return authResponse;
		} finally {
			userDao.dispose();
			System.out.println("[Info][UserService][Authenticate] - userDao disposed.");
		}

	}

}