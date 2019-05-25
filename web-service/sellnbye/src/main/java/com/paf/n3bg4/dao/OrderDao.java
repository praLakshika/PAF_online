package com.paf.n3bg4.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.paf.n3bg4.database.DbConnection;
import com.paf.n3bg4.models.Order;

public class OrderDao {
	private Connection _dbConnection;

	public OrderDao() {
		this._dbConnection = new DbConnection().getConnection();
	}

	public ArrayList<Order> getAllOrders() {
		ArrayList<Order> productList = new ArrayList<Order>();

		try {
			Statement stmt = this._dbConnection.createStatement();
			String sql;
			sql = "SELECT * FROM orders";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Order ord = new Order();
				
				ord.setSupplierName(rs.getString("supplierName"));
				ord.setCompanyName(rs.getString("companyName"));
				ord.setEmail(rs.getString("email"));
				ord.setPhone(rs.getString("phone"));
				ord.setProduct(rs.getString("product"));
				ord.setQty(rs.getString("qty"));
				
				productList.add(ord);
			}

		} catch (Exception ex) {
			System.out.println("[Error][OrderDao][getAllOrders] - " + ex.getMessage());
		}
		return productList;
	}

	public Order getOrder(String supplierName) {
		Order ord = new Order();

		try {

			String sql;
			sql = "SELECT * FROM orders WHERE supplierName = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, supplierName);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				
				ord.setSupplierName(rs.getString("supplierName"));
				ord.setCompanyName(rs.getString("companyName"));
				ord.setEmail(rs.getString("email"));
				ord.setPhone(rs.getString("phone"));
				ord.setProduct(rs.getString("product"));
				ord.setQty(rs.getString("qty"));
				
			}

		} catch (Exception ex) {
			System.out.println("[Error][OrderDao][getOrder] - " + ex.getMessage());
		}

		return ord;
	}

	public boolean addOrder(Order ord) {
		boolean isSuccess;
		try {

			String sql;
			sql = "INSERT INTO orders (supplierName,companyName,email,phone,product,qty) VALUES (?,?,?,?,?,?)";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);
			

			stmt.setString(1, ord.getSupplierName());
			stmt.setString(2, ord.getCompanyName());
			stmt.setString(3, ord.getEmail());
			stmt.setString(4, ord.getPhone());
			stmt.setString(5, ord.getProduct());
			stmt.setString(6, ord.getQty());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][OrderDao][addOrder] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	}
	
//	public AuthResponse authenticate (String username, String password) {
//		AuthResponse authResponse = new AuthResponse();
//		try {
//
//			String sql;
//			sql = "SELECT passwordHash, userType FROM users WHERE username = ?";
//
//			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);
//
//			stmt.setString(1, username);
//
//			ResultSet rs = stmt.executeQuery();
//
//			if (rs.next()) {
//				
//				String pwFromDb = rs.getString("passwordHash");
//				
//				if(pwFromDb.toUpperCase().equals(password.toUpperCase())) {
//					authResponse.setIsAuthenticated(true);
//					authResponse.setUserType(UserType.valueOf(rs.getString("userType")));
//				}else {
//					authResponse.setIsAuthenticated(false);
//				}
//
//			}
//
//		} catch (Exception ex) {
//			authResponse.setIsAuthenticated(false);
//			System.out.println("[Error][UserDao][getUser] - " + ex.getMessage());
//		}		
//		return authResponse;
//	}

	public void dispose() {
		try {
			if (!this._dbConnection.isClosed()) {
				System.out.println("[Info][UserDao][dispose] - Closing DB connection");
				this._dbConnection.close();
			}
		} catch (Exception e) {
			System.out.println("[Error][UserDao][dispose] - " + e.getMessage());
		}
	};
}


