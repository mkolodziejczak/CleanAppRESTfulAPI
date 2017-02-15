package pl.edu.uam.restapi.storage.database;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.uam.restapi.storage.entity.EntryEntity;
import pl.edu.uam.restapi.storage.entity.UserEntity;
import pl.edu.uam.restapi.storage.model.Entry;
import pl.edu.uam.restapi.storage.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class MysqlDB implements UserDatabase, EntryDatabase {

    private static final String HOST = "HOST_ADDRESS";
    private static final int PORT = 3306;
    private static final String DATABASE = "DB_NAME";
    private static final String USER_NAME = "DB_USER_NAME";
    private static final String PASSWORD = "DB_PASSWORD";

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            String dbUrl = "jdbc:mysql://" + HOST + ':' + PORT + "/" + DATABASE;

            Map<String, String> properties = new HashMap<String, String>();

            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            properties.put("hibernate.connection.url", dbUrl);
            properties.put("hibernate.connection.username", USER_NAME);
            properties.put("hibernate.connection.password", PASSWORD);
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false"); //PERFORMANCE TIP!
            properties.put("hibernate.hbm2ddl.auto", "update"); //update schema for entities (create tables if not exists)

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myUnit", properties);
            entityManager = emf.createEntityManager();
        }

        return entityManager;
    }

    @Override
    public User getUser(String email) {

        UserEntity userEntity = getEntityManager()
                .find(UserEntity.class, email);


        if (userEntity != null) {
            return buildUserResponse(userEntity);
        }

        return null;
    }

    @Override
    public Boolean loginUser(User user) {

        UserEntity userEntity = getEntityManager()
                .find(UserEntity.class, user.getEmailAddress());

        if (userEntity != null)
        {

            if(userEntity.getEmailAddress().equals(user.getEmailAddress()) && userEntity.getPassword().equals(md5Hashed(user.getPassword(), userEntity.getSalt())))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private String md5Hashed(String password, String salt)
    {
        String tempString = salt+password;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(tempString.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }
        catch(java.security.NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public User createUser(final User user) {
        UserEntity entity = buildUserEntity(user, false);

        try {
            getEntityManager().getTransaction().begin();

            // Operations that modify the database should come here.
            getEntityManager().persist(entity);

            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }

        return new User(entity.getFirstName(), entity.getLastName(), entity.getEmailAddress(), entity.getPassword(), entity.getSalt());
    }

    @Override
    public Collection<User> getUsers() {
        Query query = getEntityManager().createNamedQuery("users.findAll");
        List<UserEntity> resultList = query.getResultList();

        List<User> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (UserEntity user : resultList) {
                list.add(buildUserResponse(user));
            }
        }

        return list;
    }

    private User buildUserResponse(UserEntity userEntity) {
        return new User(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmailAddress(), userEntity.getPassword(), userEntity.getSalt());
    }

    private UserEntity buildUserEntity(User user, boolean active) {
        return new UserEntity(user.getFirstName(), user.getLastName(), user.getEmailAddress(), user.getIsActive(), user.getCellPhone(), user.getUserTypeId(), md5Hashed(user.getPassword(), user.getSalt()), user.getSalt());
    }

    @Override
    public Entry getEntry(Integer id) {

        EntryEntity entryEntity = getEntityManager()
                .find(EntryEntity.class, id);

        if (entryEntity != null) {
            return buildEntryResponse(entryEntity);
        }

        return null;
    }

    @Override
    public void archiveEntry (Integer id) {

        EntityManager em =getEntityManager();

        EntryEntity entryEntity = em.find(EntryEntity.class, id);

        em.getTransaction().begin();
        entryEntity.setStatus("ARCHIVED");
        em.getTransaction().commit();

    }

    @Override
    public void deleteEntry(Integer id) {

        EntityManager em =getEntityManager();

        EntryEntity entryEntity = em.find(EntryEntity.class, id);

        em.getTransaction().begin();
        entryEntity.setStatus("DELETED");
        em.getTransaction().commit();

    }

    @Override
    public Entry createEntry(final Entry entry) {
        EntryEntity entity = buildEntryEntity(entry);

        entity.setStatus("OPEN");
        entity.setScore(1);

        try {
            getEntityManager().getTransaction().begin();

            // Operations that modify the database should come here.
            getEntityManager().persist(entity);

            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }

        return new Entry(entity.getUserEmail(), entity.getCategory(), entity.getLatitude(), entity.getLongitude(), entity.getDate(), entity.getDescription(), entity.getStatus());
    }

    @Override
    public Collection<Entry> getEntries() {
        Query query = getEntityManager().createNamedQuery("entries.findAll");
        List<EntryEntity> resultList = query.getResultList();

        List<Entry> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (EntryEntity entry : resultList) {
                list.add(buildEntryResponse(entry));
            }
        }

        return list;
    }

    private Entry buildEntryResponse(EntryEntity entryEntity) {
        return new Entry(entryEntity.getId(), entryEntity.getUserEmail(), entryEntity.getCategory(), entryEntity.getLatitude(), entryEntity.getLongitude(), entryEntity.getDate(), entryEntity.getDescription(), entryEntity.getScore(), entryEntity.getImagesArr(), entryEntity.getStatus());
    }

    private EntryEntity buildEntryEntity(Entry entry) {
        return new EntryEntity(entry.getUserEmail(), entry.getCategory(), entry.getLatitude(), entry.getLongitude(), entry.getDate(), entry.getDescription(), entry.getImagesArr());
    }
}
