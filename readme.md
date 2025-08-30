# Barber App – Landing Page & Booking System (React + Spring Boot)
## Project in progress
## Description

A web application for a barbershop with a React frontend and Spring Boot backend.
Customers can book appointments without registering, while admins manage services and bookings through a control panel.

## 📌 Technologies Used

Backend: Spring Boot (Web, Data JPA, Security, Mail)

Frontend: React + TailwindCSS (with FullCalendar or React Big Calendar)

Database: MySQL

Security: Spring Security with JWT (admin only)

Email: Spring Boot Starter Mail

## 📌 Main Features
👤 Customer

✅ Book appointments by selecting a service, date, and time.

✅ Receive a confirmation email with links to confirm or cancel the appointment.

✅ View available services.

## 🧑‍💼 Admin

✅ Control panel with a list or calendar view of appointments.

✅ Filter appointments by status (Pending, Confirmed, Cancelled).

✅ Create, edit, and delete barbershop services.

✅ Confirm or cancel appointments manually.

## 📌 Database Structure (MySQL)
USE barber_db;

CREATE TABLE services (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description VARCHAR(255),
duration_minutes INT NOT NULL,
price DECIMAL(10,2) NOT NULL
);

CREATE TABLE appointments (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
customer_name VARCHAR(100) NOT NULL,
customer_email VARCHAR(150) NOT NULL,
service_id BIGINT NOT NULL,
date DATE NOT NULL,
time TIME NOT NULL,
status ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',
confirmation_token VARCHAR(255) UNIQUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES services(id)
);

CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
email VARCHAR(150) UNIQUE NOT NULL,
role ENUM('ADMIN') DEFAULT 'ADMIN'
);
