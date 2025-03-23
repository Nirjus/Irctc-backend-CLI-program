package irctcbackend.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserServicesUtil {
    public  static String hashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean checkPassword(String planPassword, String hashedPassword){
        return BCrypt.checkpw(planPassword,hashedPassword);
    }
    public static Date dateConversion(String dateInput){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateInput);
        dateFormat.setLenient(false); // Ensure strict date validation

        try{
            return dateFormat.parse(dateInput);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        return null;
        }
    }
}
