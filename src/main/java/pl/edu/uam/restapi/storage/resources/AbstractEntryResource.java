package pl.edu.uam.restapi.storage.resources;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.uam.restapi.storage.database.EntryDatabase;
import pl.edu.uam.restapi.storage.database.UserDatabase;
import pl.edu.uam.restapi.storage.entity.UserEntity;
import pl.edu.uam.restapi.storage.model.Entry;

import pl.edu.uam.restapi.dokumentacjaibledy.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;

@RestController
public abstract class AbstractEntryResource {

    protected abstract EntryDatabase getDatabase();

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "Get entries collection", notes = "Get entries collection", response = Entry.class, responseContainer = "LIST")
    public Collection<Entry> list() {
        return getDatabase().getEntries();
    }


    @RequestMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ApiOperation(value = "Get entry by id", notes = "[note]Get entry by id", response = Entry.class)
    public Entry getEntry(@PathVariable("id") Integer id) throws Exception {
        Entry entry = getDatabase().getEntry(id);

        if (entry.equals("db")) {
            throw new Exception("Database error");
        }

        if (entry == null) {
            throw new UserException("Entry not found", "Zgłoszenie nie zostało znalezione", "http://docu.pl/errors/user-not-found");
        }

        return entry;
    }

    @RequestMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ApiOperation(value = "Mark entry by deleted", notes = "[note]Mark entry as deleted", response = Entry.class)
    public Entry deleteEntry(@PathVariable("id") Integer id) throws Exception {
        Entry entry = getDatabase().getEntry(id);

        if (entry.equals("db")) {
            throw new Exception("Database error");
        }

        if (entry == null) {
            throw new UserException("Entry not found", "Zgłoszenie nie zostało znalezione", "http://docu.pl/errors/user-not-found");
        }
        getDatabase().deleteEntry(id);
        entry.setStatus("DELETED");
        return entry;
    }

    @RequestMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ApiOperation(value = "Mark entry by archived", notes = "[note]Mark entry as archived", response = Entry.class)
    public Entry archiveEntry(@PathVariable("id") Integer id) throws Exception {
        Entry entry = getDatabase().getEntry(id);

        if (entry.equals("db")) {
            throw new Exception("Database error");
        }

        if (entry == null) {
            throw new UserException("Entry not found", "Zgłoszenie nie zostało znalezione", "http://docu.pl/errors/user-not-found");
        }

        getDatabase().archiveEntry(id);
        entry.setStatus("ARCHIVED");
        return entry;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create entry", notes = "Create entry", response = Entry.class)
    public ResponseEntity createEntry(@RequestBody Entry entry, HttpServletRequest request) {
        Entry dbEntry = new Entry(entry.getUserEmail(), entry.getCategory(), entry.getLatitude(), entry.getLongitude(), entry.getDate(), entry.getDescription(), entry.getImagesArr());

        Entry createdEntry = getDatabase().createEntry(dbEntry);

        return ResponseEntity.created(URI.create(request.getPathInfo() + "/" )).body(createdEntry);
    }
}
