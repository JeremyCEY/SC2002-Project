package main.model.user;

import main.controller.account.password.PasswordHashManager;


//A factory class for creating User objects based on the given parameters
public class UserFactory {
    public static User create(UserType userType, String userID, String password, String name, String faculty){
        String hashedPassword = PasswordHashManager.hashPassword(password);
        return switch (userType){
            case STUDENT -> new Student(userID, name, faculty, hashedPassword);
            case STAFF -> new Staff(userID, name, faculty, hashedPassword);
        };
    }
}
