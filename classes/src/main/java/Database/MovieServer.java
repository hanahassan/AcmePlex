package Database;

import com.sun.net.httpserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieServer {
    public static void main(String[] args) throws Exception {
        // Create the server that listens on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Register movie handler
        server.createContext("/movies", new MoviesHandler());
        
        // Set the server's executor and start it
        server.setExecutor(null);
        server.start();
        
        System.out.println("Server started at http://localhost:8080");
    }
}

class MoviesHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Enable CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");  // Allow all origins
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");  // Allowed HTTP methods
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");  // Allowed headers
        
        // Handle OPTIONS request (preflight request for CORS)
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, -1);  // No content
            return;
        }
        
        // For a GET request, fetch the movie data
        if ("GET".equals(exchange.getRequestMethod())) {
            // Get the list of movies from the database
            JSONArray moviesJson = getMoviesFromDatabase();
            
            // Convert movies data to string
            String response = moviesJson.toString();
            
            // Send the response
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // Handle unsupported HTTP methods (just return 405 Method Not Allowed)
            exchange.sendResponseHeaders(405, -1);
        }
    }
    
    private JSONArray getMoviesFromDatabase() {
        JSONArray moviesJson = new JSONArray();
        
        // Your database connection logic here (example with JDBC)
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT MovieID, Title FROM Movies";  // Example query to fetch movies
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                // Create a JSONObject for each movie
                JSONObject movieJson = new JSONObject();
                movieJson.put("id", rs.getInt("MovieID"));
                movieJson.put("title", rs.getString("Title"));
                
                // Add to the movies array
                moviesJson.put(movieJson);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return moviesJson;
    }
}

class DatabaseConnection {
    // Database connection details (example with MySQL)
    private static final String URL = "jdbc:mysql://localhost:3306/AcmePlex";
    private static final String USER = "root";
    private static final String PASSWORD = "password1";  // Replace with your actual password

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
