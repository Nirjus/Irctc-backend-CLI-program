package irctcbackend.entities;

import java.util.Date;

public class Ticket{
    private String userId;
    private String ticketId;

    private String source;

    private String destination;

    private Date dateOfTravel;
    private Train train;

    public Ticket(){}

    public Ticket(String userId, String ticketId, String source, String destination, Date dateOfTravel, Train train) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getTicketInfo(){
        return ticketId + " : source " + source + " to " + destination;
    }
    
}