package irctcbackend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import irctcbackend.entities.Train;
import irctcbackend.entities.User;
import irctcbackend.utils.UserServicesUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingServices {

    private User user;

    private final List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "app/src/main/java/irctcbackend/localDB/users.json";

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

        user.printTickets();
        }
        public List<Train> getTrains(String source, String destination) {
            TrainServices trainServices = null;
            try {
                trainServices = new TrainServices();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return trainServices.searchTrains(source, destination);
        }
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
