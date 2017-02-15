package pl.edu.uam.restapi.storage.database;

import pl.edu.uam.restapi.storage.model.User;

import java.util.Collection;

public interface UserDatabase {
    User getUser(String email);

    User createUser(User user);

    Boolean loginUser(User user);

    Collection<User> getUsers();
}
