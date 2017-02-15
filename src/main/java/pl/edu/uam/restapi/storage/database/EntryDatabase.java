package pl.edu.uam.restapi.storage.database;

import pl.edu.uam.restapi.storage.model.Entry;


import java.util.Collection;

public interface EntryDatabase {
    Entry getEntry(Integer id);

    Entry createEntry(Entry entry);

    void archiveEntry(Integer id);

    void deleteEntry(Integer id);

    Collection<Entry> getEntries();
}
