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

import com.paf.n3bg4.dao.OrderDao;
import com.paf.n3bg4.models.Order;

@Path("/order")
public class OrderService {
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Order> GetOrderList() {
		OrderDao orderDao = new OrderDao();
		try {
			ArrayList<Order> orderList = new ArrayList<Order>();
			orderList = orderDao.getAllOrders();
			return orderList;
		} finally {
			orderDao.dispose();
		}
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order GetOrder(@PathParam("name") String name) {
		OrderDao orderDao = new OrderDao();
		try {
			Order order = new Order();
			order = orderDao.getOrder(name);
			return order;
		} finally {
			orderDao.dispose();
			System.out.println("[Info][OrderService][GetOrder] - orderDao disposed.");
		}
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean AddOrder(Order ord) {
		OrderDao ordDao = new OrderDao();
		try {
			
			return ordDao.addOrder(ord);
		} finally {
			ordDao.dispose();
		}
	}

	@PUT
	@Path("/{name}")
	public Response EditUserStatus(@PathParam("name") String name) {

		String output = "Jersey say : ";

		return Response.status(200).entity(output).build();

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

//	@POST
//	@Path("/login")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public AuthResponse Authenticate(AuthRequest authRequest) {
//
//		UserDao userDao = new UserDao();
//		try {
//			AuthResponse authResponse = new AuthResponse();
//			authResponse = userDao.authenticate(authRequest.getUsername(), authRequest.getPassword());
//			return authResponse;
//		} finally {
//			userDao.dispose();
//			System.out.println("[Info][UserService][Authenticate] - userDao disposed.");
//		}
//
//	}

}
