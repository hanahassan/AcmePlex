package edu.ucalgary.movie;


public class Theatre {
    private int theatreID;
    private String name;
    private String location;

    // Constructor
    public Theatre(int theatreID, String name, String location) {
        this.theatreID = theatreID;
        this.name = name;
        this.location = location;
    }

    // Getters and Setters
    public int getTheatreID() {
        return theatreID;
    }

    public void setTheatreID(int theatreID) {
        this.theatreID = theatreID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "theatreID=" + theatreID +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
