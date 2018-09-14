package factory;

import Model.User;

/**
 * Created by hiteshs on 9/13/18.
 */
public class UserFactory {

    private static User user;

    public static synchronized User getValidUser() {
        if (user == null) {
            user = new User();
            user.setEmail("abcd123@asd.com");
            user.setPassword("abcd123");
            user.setFirstName("Shopaholic");
            user.setLastName("User");
            user.setAddress("2nd Street,sector 2,abcd");
            user.setAddressState("Newyork, Alaska 10001");
            user.setCountry("United States");
            user.setPhoneNumber("1-202-555-0117");
        }
        return user;
    }
}
