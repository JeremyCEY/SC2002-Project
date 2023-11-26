package main.controller.account.user;

import main.model.user.*;

/**
 * This class gives us utility for getting the domain of a user.
 */
public class UserDomainGetter {
    /**
     * Gets the domain of the specific user.
     *
     * @param user the user whose domain is to be retrieved
     * @return the domain of the user
     */
    public static UserType getUserDomain(User user) {
        if (user instanceof Student) {
            return UserType.STUDENT;
        } else if (user instanceof Staff) {
            return UserType.STAFF;
        } else {
            throw new RuntimeException("No such domain");
        }
    }
}
