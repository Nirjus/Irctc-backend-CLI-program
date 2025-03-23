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

public class UserBookingServices {

    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "app/src/main/java/irctcbackend/localDB/users.json";

    public List<User> loadUsers() throws IOException{
        File users = new File(USER_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }
    // default constructor
    public UserBookingServices() throws IOException{
        this.userList = new ArrayList<>();
    loadUsers();
    }

    public UserBookingServices(User user) throws IOException {
        this.user = user;
        this.userList = new ArrayList<>();
        loadUsers();
    }
      public Boolean loginUser(){
      Optional<User> foundUser = userList.stream().filter(user1 -> {
        return user1.getName().equals(user.getName()) && UserServicesUtil.checkPassword(user.getPassword(), user1.getHashPassword());
     }).findFirst();
      return foundUser.isPresent();
     }

     public  void saveUserListFile() throws IOException{
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userList);
     }

        public void signUp(User user1){
        try {
            userList.add(user1);
            saveUserListFile();
        }catch (IOException ignored){
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
            // TODO: complete this function
        return Boolean.FALSE;
        }

}
