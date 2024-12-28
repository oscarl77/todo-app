# Todo App - React, Spring Boot, MySQL

A simple Todo application that allows users to create, read, update, delete and set email reminders on tasks. The application uses **React** for the frontend, **Java Spring Boot** for the backend, and **MySQL** for the database.

## Table of Contents
1. [Technologies Used](#technologies-used)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [Dependencies](#dependencies)

## Technologies Used

- **Frontend**: React
- **Backend**: Java Spring Boot
- **Database**: MySQL
- **Build tools**: Gradle (Java) and Vite (React)

## Features

- User authentication (sign up, login, and logout) and verification
- Create, read, update, and delete tasks
- Tasks can be marked as completed
- Reminders can be sent to the user's email address
- User-specific tasks (users can only see and manage their own tasks)
- Input validation (both client-side and server-side)

## Prerequisites

To run this application, the following tools need to be installed:

- **Java** (JDK 17)
- **Node.js** (v22.9.0)
- **MySQL**
- **Gradle**
- **Vite**

## Dependencies

### Backend (Spring Boot):
- **Spring Boot**: The main framework for building the backend.
- **Spring Web**: For creating REST APIs.
- **Spring Security**: For handling authentication and authorization.
- **Spring Data JPA**: To interact with the MySQL database.
- **MySQL Connector/J**: MySQL database driver.

These dependencies are managed using **Gradle** through the `build.gradle` file.

### Frontend (React):
- **React**: The core library for building user interfaces.
- **React Router**: For routing and navigation.
- **Axios**: For making HTTP requests to the backend.
- **Vite**: The frontend bundler used to build and serve the React application.
- **Validator**: For client-side form validation.
- **CSS Modules** (optional): For styling the components.

These dependencies are managed using **npm** through the `package.json` file.
