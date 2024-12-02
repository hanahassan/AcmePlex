-- SQL Dump to Create Database Schema for AcmePlex

DROP DATABASE IF EXISTS `AcmePlex`;
CREATE DATABASE `AcmePlex`;
USE `AcmePlex`;

-- Create Theatres Table
CREATE TABLE Theatres (
    TheatreID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Location VARCHAR(255) NOT NULL
);

-- Create Movies Table
CREATE TABLE Movies (
    MovieID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255) NOT NULL
);

-- Create Showtimes Table
CREATE TABLE Showtimes (
    ShowtimeID INT AUTO_INCREMENT PRIMARY KEY,
    TheatreID INT NOT NULL,
    MovieID INT NOT NULL,
    ShowDate DATE NOT NULL,
    ShowTime TIME NOT NULL,
    AvailableSeats INT NOT NULL,
    RUReservedSeats INT NOT NULL,
    FOREIGN KEY (TheatreID) REFERENCES Theatres(TheatreID),
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID)
);

-- Create Users Table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Phone VARCHAR(20),
    IsRegistered BOOLEAN NOT NULL,
    CardNumber VARCHAR(16),
    CardExpiry DATE,
    RUJoinDate DATE,
    AccountFeePaid BOOLEAN,
    Credits DECIMAL(10, 2) DEFAULT 0.00
);

-- Create Tickets Table
CREATE TABLE Tickets (
    TicketID INT AUTO_INCREMENT PRIMARY KEY,
    ShowtimeID INT NOT NULL,
    UserID INT NOT NULL,
    SeatNumber VARCHAR(10) NOT NULL,
    PurchaseDate DATETIME NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    IsCancelled BOOLEAN DEFAULT FALSE,
    CancelledDate DATETIME,
    FOREIGN KEY (ShowtimeID) REFERENCES Showtimes(ShowtimeID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create Payments Table
CREATE TABLE Payments (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    TicketID INT NOT NULL,
    UserID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATETIME NOT NULL,
    FOREIGN KEY (TicketID) REFERENCES Tickets(TicketID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create AdminSettings Table
CREATE TABLE AdminSettings (
    SettingID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Value DECIMAL(10, 2) NOT NULL,
    LastUpdated DATETIME NOT NULL
);

-- Create Credit Table
CREATE TABLE Credit (
    CreditID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    CreditDate DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Insert initial shared employee user into Users Table
INSERT INTO Users (Name, Email, Phone, IsRegistered, CardNumber, CardExpiry, Password) 
VALUES ('AcmePlex Admin', 'admin@acmeplex.com', '555-0000', FALSE, '0000000000000000', '2025-12-31', 'adminpass123');

-- Insert initial data for Theatres
INSERT INTO Theatres (Name, Location) 
VALUES ('AcmePlex Downtown', 'Stephen Avenue'), 
       ('AcmePlex NW', 'University Ave');

-- Insert initial data for Movies
INSERT INTO Movies (Title) 
VALUES ('The Great Adventure'), 
       ('Space Odyssey'), 
       ('Mystery Island');
