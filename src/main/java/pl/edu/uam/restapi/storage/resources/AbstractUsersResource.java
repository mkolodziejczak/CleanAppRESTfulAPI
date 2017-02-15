package pl.edu.uam.restapi.storage.resources;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.uam.restapi.storage.database.UserDatabase;
import pl.edu.uam.restapi.storage.entity.UserEntity;
import pl.edu.uam.restapi.storage.model.ErrorMessage;
import pl.edu.uam.restapi.storage.model.User;
import pl.edu.uam.restapi.dokumentacjaibledy.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;

@RestController
public abstract class AbstractUsersResource {

    protected abstract UserDatabase getDatabase();

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "Get users collection", notes = "Get users collection", response = User.class, responseContainer = "LIST")
    public Collection<User> list() {
        return getDatabase().getUsers();
    }


    @RequestMapping(value="/{emailAddress}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "Get user by email address", notes = "[note]Get user by emailAddress", response = User.class)
    public User getUser(@PathVariable("emailAddress") String emailAddress) throws Exception {
        User user = getDatabase().getUser(emailAddress);

        if (emailAddress.equals("db")) {
            throw new Exception("Database error");
        }

        if (user == null) {
            throw new UserException("User not found", "Użytkownik nie został znaleziony", "http://docu.pl/errors/user-not-found");
        }

        return user;
    }



    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create user", notes = "Create user", response = User.class)
    public ResponseEntity createUser(@RequestBody User user, HttpServletRequest request) {
        User dbUser = new User(user.getFirstName(), user.getLastName(), user.getEmailAddress(), user.getCellPhone(), user.getUserTypeId(), user.getPassword());

        User createdUser = getDatabase().createUser(dbUser);

        return ResponseEntity.created(URI.create(request.getPathInfo() + "/" )).body(createdUser);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create user", notes = "Create user", response = ErrorMessage.class)
    public ErrorMessage loginUser(@RequestBody User user, HttpServletRequest request) {
        User dbUser = new User(user.getEmailAddress(), user.getPassword());

        if(getDatabase().loginUser(dbUser))
        {
            ErrorMessage mess = new ErrorMessage("200", "success");
            return mess;
        }
        else
        {
            ErrorMessage mess = new ErrorMessage("404", "authorisation failed");
            return mess;
        }



    }
}
