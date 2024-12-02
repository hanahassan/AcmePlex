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
        
        // Create the "/theatres" context and associate it with TheatreHandler
        server.createContext("/theatres", new TheatreHandler());

        // Register movie handler
        server.createContext("/movies", new MoviesHandler());

        // Register search handler
        server.createContext("/movies/search", new SearchHandler());

        
        
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
    
class TheatreHandler implements HttpHandler {
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

        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<String[]> theatres = DatabaseRetriever.getAllTheatres();  // or TheatreHandler.getAllTheatres()
                JSONArray jsonTheatres = new JSONArray();

                // Add each theatre to the JSON array
                for (String[] theatre : theatres) {
                    JSONObject theatreJson = new JSONObject();
                    theatreJson.put("theatre_id", theatre[0]);
                    theatreJson.put("name", theatre[1]);
                    theatreJson.put("location", theatre[2]);
                    jsonTheatres.put(theatreJson);
                }

                // Send the response with JSON
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonTheatres.toString().getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonTheatres.toString().getBytes());
                os.close();
            } catch (SQLException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, -1);
            }
        }
    }
}class SearchHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            // Add CORS headers
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            // Handle OPTIONS request (preflight request)
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, -1); // No content
                return;
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                String queryString = exchange.getRequestURI().getQuery();
                if (queryString == null || !queryString.contains("q=")) {
                    exchange.sendResponseHeaders(400, -1); // Bad Request
                    return;
                }

                String query = queryString.split("q=")[1];
                query = URLDecoder.decode(query, "UTF-8");

                JSONArray results = searchMovies(query);

                // Send JSON response
                String response = results.toString();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
        }
    }

    private JSONArray searchMovies(String query) {
        JSONArray moviesJson = new JSONArray();
        try (Connection conn = DatabaseConnection.connect()) {
            // Use SQL to check for all letters in the query
            StringBuilder sqlBuilder = new StringBuilder("SELECT MovieID, Title FROM Movies WHERE Title LIKE ?");
            for (int i = 1; i < query.length(); i++) {
                sqlBuilder.append(" AND Title LIKE ?");
            }

            String sql = sqlBuilder.toString();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters for each character in the query
            for (int i = 0; i < query.length(); i++) {
                stmt.setString(i + 1, "%" + query.charAt(i) + "%");
            }

            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                JSONObject movieJson = new JSONObject();
                movieJson.put("id", rs.getInt("MovieID"));
                movieJson.put("title", rs.getString("Title"));
                moviesJson.put(movieJson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moviesJson;
    }
}