package com.paf.n3bg4.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.paf.n3bg4.database.DbConnection;
import com.paf.n3bg4.models.AuthResponse;
import com.paf.n3bg4.models.Enums.UserType;
import com.paf.n3bg4.models.User;
import com.paf.n3bg4.models.UserUpdateModel;

public class UserDao {

	private Connection _dbConnection;

	public UserDao() {
		this._dbConnection = new DbConnection().getConnection();
	}

	public ArrayList<User> getAllUsers() {
		ArrayList<User> userList = new ArrayList<User>();

		try {
			Statement stmt = this._dbConnection.createStatement();
			String sql;
			sql = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				User usr = new User();

				usr.setUsername(rs.getString("username"));
				usr.setContactNo(rs.getString("contactNo"));
				usr.setUserType(UserType.valueOf(rs.getString("userType")));
				usr.setPasswordHash("********");
				usr.setEmail(rs.getString("email"));
				usr.setProfilePicture(rs.getString("profilePicture"));
				usr.setIsActive(rs.getBoolean("isActive"));

				userList.add(usr);
			}

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][getAllUsers] - " + ex.getMessage());
		}
		return userList;
	}

	public User getUser(String username) {
		User usr = new User();

		try {

			String sql;
			sql = "SELECT * FROM users WHERE username = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				usr.setUsername(rs.getString("username"));
				usr.setContactNo(rs.getString("contactNo"));
				usr.setUserType(UserType.valueOf(rs.getString("userType")));
				usr.setPasswordHash("********");
				usr.setEmail(rs.getString("email"));
				usr.setProfilePicture(rs.getString("profilePicture"));
				usr.setIsActive(rs.getBoolean("isActive"));

			}

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][getUser] - " + ex.getMessage());
		}

		return usr;
	}

	public boolean addUser(User user) {
		boolean isSuccess;
		try {

			String sql;
			sql = "INSERT INTO users (username,contactNo,userType,passwordHash,email,profilePicture,isActive) VALUES (?,?,?,?,?,?,?)";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getContactNo());
			stmt.setString(3, user.getUserType().name());
			stmt.setString(4, user.getPasswordHash());
			stmt.setString(5, user.getEmail());
			stmt.setBlob(6, new ByteArrayInputStream(user.getProfilePicture().getBytes()));
			stmt.setBoolean(7, user.getIsActive());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][addUser] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	}

	public boolean updateUser(UserUpdateModel user) {
		boolean isSuccess;
		try {

			String sql;
			sql = "UPDATE users SET contactNo = ? ,email = ? , isActive = ? WHERE username = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(4, user.getUsername());
			stmt.setString(1, user.getContactNo());
			stmt.setString(2, user.getEmail());
			stmt.setBoolean(3, user.getIsActive());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][updateUser] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	}

	public boolean isRegisteredUser(String username) {

		boolean isUserRegistered = false;

		try {
			String sql;
			sql = "SELECT * FROM users WHERE username = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				isUserRegistered = true;
			} else {
				isUserRegistered = false;
			}

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][isRegisteredUser] - " + ex.toString());
			isUserRegistered = false;
		}

		return isUserRegistered;

	}

	public boolean deleteUser(String username) {
		boolean isSuccess = false;
		try {

			String sql;
			sql = "DELETE FROM users WHERE username = ? AND userType <> ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, username);
			stmt.setString(2, UserType.Admin.name());

			isSuccess = stmt.executeUpdate() > 0;

		} catch (Exception ex) {
			System.out.println("[Error][UserDao][addUser] - " + ex.toString());
			isSuccess = false;
		}
		return isSuccess;
	} 
	
	public AuthResponse authenticate(String username, String password) {
		AuthResponse authResponse = new AuthResponse();
		try {

			String sql;
			sql = "SELECT passwordHash, userType FROM users WHERE username = ?";

			PreparedStatement stmt = this._dbConnection.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				String pwFromDb = rs.getString("passwordHash");

				if (pwFromDb.toUpperCase().equals(password.toUpperCase())) {
					authResponse.setIsAuthenticated(true);
					authResponse.setUserType(UserType.valueOf(rs.getString("userType")));
				} else {
					authResponse.setIsAuthenticated(false);
				}

			}

		} catch (Exception ex) {
			authResponse.setIsAuthenticated(false);
			System.out.println("[Error][UserDao][getUser] - " + ex.getMessage());
		}
		return authResponse;
	}

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