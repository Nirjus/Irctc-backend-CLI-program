package irctcbackend.utils;

import org.mindrot.jbcrypt.BCrypt;

public class UserServicesUtil {
    public  static String hashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPassword(String planPassword, String hashedPassword){
        return BCrypt.checkpw(planPassword,hashedPassword);
    }
}
