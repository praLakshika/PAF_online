package com.paf.n3bg4.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import com.paf.n3bg4.database.DbConnection;
import com.paf.n3bg4.models.Product;
import com.paf.n3bg4.models.ProductUpdateModel;
import com.paf.n3bg4.models.Enums.UserType;

public class ProductDao {

	private Connection _dbConnection;

	public ProductDao() {
		this._dbConnection = new DbConnection().getConnection();
	}

	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> productList = new ArrayList<Product>();

		try {
			Statement stmt = this._dbConnection.createStatement();
			String sql;
			sql = "SELECT * FROM products";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Product pro = new Product();

				pro.setProductId(rs.getInt("productId"));
				pro.setCreator(rs.getString("creator"));
				pro.setProductName(rs.getString("productName"));
				pro.setProductImage(rs.getString("productImage"));
				pro.setProductDescription(rs.getString("productDescription"));
				pro.setProductPrice(rs.getString("productPrice"));
				pro.setProductCount(rs.getString("productCount"));
				
				productList.add(pro);
			}

		} catch (Exception ex) {
			System.out.println("[Error][ProductDao][getAllProducts] - " + ex.getMessage());
		}
		return productList;
	}

	public Product getProduct(String productname) {
		Product pro = new Product();

		try {

			String sql;
			sql = "SELECT * FROM products WHERE productId = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, productname);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				pro.setProductId(rs.getInt("productId"));
				pro.setCreator(rs.getString("creator"));
				pro.setProductName(rs.getString("productName"));
				pro.setProductImage(rs.getString("productImage"));
				pro.setProductDescription(rs.getString("productDescription"));
				pro.setProductPrice(rs.getString("productPrice"));
				pro.setProductCount(rs.getString("productCount"));

				
			}

		} catch (Exception ex) {
			System.out.println("[Error][ProductDao][getProduct] - " + ex.getMessage());
		}

		return pro;
	}

	public boolean addProduct(Product pro) {
		boolean isSuccess;
		try {

			String sql;
			sql = "INSERT INTO products (creator,productName,productImage,productDescription,productPrice,productCount) VALUES (?,?,?,?,?,?)";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, pro.getCreator());
			stmt.setString(2, pro.getProductName());
			stmt.setBlob(3, new ByteArrayInputStream(pro.getProductImage().getBytes()));
			stmt.setString(4, pro.getProductDescription());
			stmt.setString(5, pro.getProductPrice());
			stmt.setString(6, pro.getProductCount());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][ProductDao][addProduct] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	}
	
	public boolean updateProduct(ProductUpdateModel pro) {
		boolean isSuccess;
		try {

			String sql;
			sql = "UPDATE products SET productPrice = ? , productCount= ? WHERE productName = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			//stmt.setString(4, user.getUsername());
			stmt.setString(1, pro.getProductPrice());
			stmt.setString(2, pro.getProductCount());
			stmt.setString(3, pro.getProductName());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][ProductDao][updateProduct] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	}

	
	public boolean deleteProduct(String productname) {
		boolean isSuccess = false;
		try {

			String sql;
			sql = "DELETE FROM products WHERE productId = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, productname);

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][ProductDao][deleteProduct] - " + ex.toString());
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

