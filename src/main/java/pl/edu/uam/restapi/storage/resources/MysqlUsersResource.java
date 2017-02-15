package pl.edu.uam.restapi.storage.resources;

        import io.swagger.annotations.Api;
        import org.springframework.stereotype.Component;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import pl.edu.uam.restapi.storage.database.MysqlDB;
        import pl.edu.uam.restapi.storage.database.UserDatabase;


@RestController
@RequestMapping("/mysql/users")
@Api(value = "/mysql/users", description = "Operations about users using mysql")
@Component
public class MysqlUsersResource extends AbstractUsersResource {

    private static final UserDatabase database = new MysqlDB();

    @Override
    protected UserDatabase getDatabase() {
        return database;
    }

}
