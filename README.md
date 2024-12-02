# AcmePlex Movie Server Application

## Overview
This application is a Movie Server that connects to a MySQL database and provides a server at `http://localhost:8080` to handle requests. The application uses Java and requires external libraries (JSON and MySQL Connector/J) to run successfully. The front-end can be accessed via a live server that runs `index.html`.

## Prerequisites
- Java Development Kit (JDK) installed (preferably JDK 8 or higher)
- MySQL database server running and accessible
- MySQL Connector/J JAR file
- JSON library JAR file
- `10.sql` script for setting up the database tables
- Code editor or web server that supports running `index.html` (e.g., Visual Studio Code with Live Server extension)

## Steps to Deploy and Run the Application

### 1. Install Java Development Kit (JDK)
Ensure that JDK is installed on your machine. If not, download and install it from the official [Java website](https://www.oracle.com/java/technologies/javase-downloads.html).

### 2. Download Required Libraries
Download the required libraries:
- **JSON Library** (for JSON handling)
  - Example: `json-20240303.jar`
- **MySQL Connector/J** (for MySQL database connection)
  - Example: `mysql-connector-j-9.1.0.jar`

### 3. Clone or Download the Application Code
Download or clone the project files to a local directory on your computer.

### 4. Set Up the Database
To set up the database tables, open MySQL Workbench.
- Copy and paste the contents of the `10.sql` file into the SQL editor of MySQL Workbench.
- Make sure you are using the following login credentials:
  - **Username**: `root`
  - **Password**: `password1`
- Now all the tables should be in a Schema called AcmePlex

### 5. Compile the Java Code
To compile the code, run the following command, replacing the paths to the `json-20240303.jar` and `mysql-connector-j-9.1.0.jar` libraries with the actual paths where you have saved them on your computer:
```bash
javac -cp ".:/path/to/json-20240303.jar:/path/to/mysql-connector-j-9.1.0.jar" /path/to/MovieServer.java
```

### 6. Run the Movie Server Application
Once the code is compiled successfully, run the Movie Server using the following command:
```bash
java -cp ".:/path/to/json-20240303.jar:/path/to/mysql-connector-j-9.1.0.jar" path.to.MovieServer
```

### 7. Access the Movie Server
After running the above command, you should see output similar to:
```bash
Server started at http://localhost:8080
Database connected successfully!
```
This means the server is up and running, and the connection to the database has been established.

### 8. Running the Front-End
To view the website, you need to run `index.html` in a live server. This will allow you to interact with the front-end of the application.

#### If you're using Visual Studio Code:
- Install the **Live Server** extension from the VSCode marketplace.
- Right-click on the `index.html` file in the project folder.
- Select **Open with Live Server**.
- This will open the front-end application in your web browser.

#### If you're using another method:
- Use any other method of running `index.html` through a live server (e.g., Python's `http.server`, or other local development servers).

### 11. Interacting with the Application
You can now interact with both the Movie Server and the front-end by accessing:
- **Backend**: `http://localhost:8080` from your browser to interact with the server.
- **Frontend**: The live server should automatically open `index.html` in your default browser.

## Troubleshooting
- **Server not starting:**
  - Ensure that MySQL is running and accessible.
  - Check that the correct JAR files are included in the classpath.
  - Ensure that the `10.sql` script has been run and the database is set up correctly.
- **Database connection errors:**
  - Verify that the database credentials are correct and the MySQL database is accessible.
- **Frontend not displaying correctly:**
  - Ensure that `index.html` is being served through a live server.
  - Check the browser console for any JavaScript errors or network issues.

## Additional Notes
- If you need to modify database connection settings, you may need to adjust the connection details within the code (`Database.MovieServer.java`).
- If you're running on a different operating system, ensure that the paths and commands are adjusted accordingly (e.g., use `;` instead of `:` for classpath separators on Windows).
