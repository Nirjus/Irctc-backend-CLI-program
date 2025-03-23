package irctcbackend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import irctcbackend.entities.Ticket;
import irctcbackend.entities.Train;
import irctcbackend.entities.User;
import irctcbackend.utils.UserServicesUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingServices {

    private User user;

    private final List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "app/src/main/java/irctcbackend/localDB/users.json";

    TrainServices trainServices = new TrainServices();

    public List<User> loadUsers() throws IOException{
        File users = new File(USER_PATH);
        if(!users.exists()){
            return new ArrayList<>();
        }
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    // default constructor
    public UserBookingServices() throws IOException{
    this.userList = loadUsers();
    }

      public void loginUser(String name, String password){
      Optional<User> foundUser = userList.stream().filter(user1 -> user1.getName().equals(name) && UserServicesUtil.checkPassword(password, user1.getHashPassword())).findFirst();
            if(foundUser.isPresent()){
                this.user = foundUser.get();
                System.out.println("Login successful, welcome " + foundUser.get().getName());
            }else {
                System.out.println("Login failed Invalid userName or password");
            }
      }

     public  void saveUserListFile() throws IOException{
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userList);
     }

        public boolean signUp(String password, String name){
        try {
            boolean userExists = userList.stream().anyMatch(
                    user2 -> user2.getName().equalsIgnoreCase(name)
            );
            if(userExists){
                System.out.println("User already present");
                return false;
            }
            User newUser = new User(name, UserServicesUtil.hashedPassword(password), password, new ArrayList<>(), UUID.randomUUID().toString());
            userList.add(newUser);
            saveUserListFile();
            System.out.println("User sign up successfully");
            return  true;
        }catch (IOException ignored){
            return false;
        }
        }

        public void fetchBooking(){
            if(!isLogin()){
                return;
            }
        user.printTickets();
        }
        public List<Train> getTrains(String source, String destination) {
            return trainServices.searchTrains(source, destination);
        }
        private boolean isLogin(){
        return this.user != null;
        }
        // Book a seat
        public void bookSeat(String source, String destination, Date dateOfTravel){
          if(!isLogin()){
              return;
          }
           Train train = trainServices.findTrain(source, destination);
          if(train == null){
              System.out.println("No train available in this route");
              return;
          }
           int[] findAvailableSeats = trainServices.findAvailableSeat(train);
            if (findAvailableSeats == null) {
                System.out.println("No available seats on this train.");
                return;
            }
            train.getSeats().get(findAvailableSeats[0]).set(findAvailableSeats[1],0 );

            Ticket newTicket = new Ticket(user.getUserId(),UUID.randomUUID().toString(), source, destination,dateOfTravel, train);
            user.getTicketBooked().add(newTicket);
            try {
                saveUserListFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println("Seat booked successfully, TicketId: " + newTicket.getTicketId());
        }

//        Cancel booking
        public Boolean cancelBooking(String ticketId){

            if (ticketId == null || ticketId.isEmpty()) {
                System.out.println("Ticket ID cannot be null or empty.");
                return Boolean.FALSE;
            }

            String finalTicketId1 = ticketId;  //Because strings are immutable
            boolean removed = user.getTicketBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

            String finalTicketId = ticketId;
            user.getTicketBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
            if (removed) {
                System.out.println("Ticket with ID " + ticketId + " has been canceled.");
                return Boolean.TRUE;
            }else{
                System.out.println("No ticket found with ID " + ticketId);
                return Boolean.FALSE;
            }
        }

}
