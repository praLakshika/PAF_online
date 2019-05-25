CREATE DATABASE IF NOT EXISTS paf_n3b_g4;

use paf_n3b_g4;

CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) NOT NULL,
    contactNo VARCHAR(12),
    userType VARCHAR(10) NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    email VARCHAR(50),
    profilePicture BLOB,
    isActive TINYINT(1),
    PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS products (
    productId INT NOT NULL AUTO_INCREMENT,
    creator VARCHAR(255) NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productImage BLOB,
    productDescription VARCHAR(255),
    productPrice DECIMAL(13,2) NOT NULL,
    productCount INT NOT NULL,
    PRIMARY KEY (productId),
    FOREIGN KEY (creator) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS orders(
    orderId INT NOT NULL AUTO_INCREMENT,
    buyer VARCHAR(255) NOT NULL,
    orderDate DATETIME NOT NULL,
    orderTotal DECIMAL(13,2) NOT NULL,
    PRIMARY KEY (orderId),
    FOREIGN KEY (buyer) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS orderedItems(
    orderId INT NOT NULL,
    productId INT NOT NULL,
    units INT NOT NULL,
    PRIMARY KEY (orderId, productId),
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (productId) REFERENCES products(productId)
);

CREATE TABLE IF NOT EXISTS shipment( 
    shipmentId INT NOT NULL AUTO_INCREMENT,
    orderId INT NOT NULL,
    seller VARCHAR(255) NOT NULL,
    buyer VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL,
    PRIMARY KEY (shipmentId),
    FOREIGN KEY (seller) REFERENCES users(username),
    FOREIGN KEY (buyer) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS keywords(
	productId INT NOT NULL,
    keyword VARCHAR(100) NOT NULL,
    PRIMARY KEY (productId, keyword),
    FOREIGN KEY (productId) REFERENCES products(productId)
);

CREATE TABLE IF NOT EXISTS addresses(
	addressId INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    address VARCHAR(300) NOT NULL,
    PRIMARY KEY (addressId),
    FOREIGN KEY (username) REFERENCES users(username)
);

ALTER TABLE users MODIFY profilePicture MEDIUMBLOB;
ALTER TABLE products MODIFY productImage MEDIUMBLOB;