package pl.edu.uam.restapi.storage.resources;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.uam.restapi.storage.database.MysqlDB;
import pl.edu.uam.restapi.storage.database.EntryDatabase;


@RestController
@RequestMapping("/mysql/entries")
@Api(value = "/mysql/entries", description = "Operations about entries using mysql")
@Component
public class MysqlEntriesResource extends AbstractEntryResource {

    private static final EntryDatabase database = new MysqlDB();

    @Override
    protected EntryDatabase getDatabase() {
        return database;
    }

}
