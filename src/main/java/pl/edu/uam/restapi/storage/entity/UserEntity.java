package pl.edu.uam.restapi.storage.entity;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "users.findAll", query = "SELECT u FROM UserEntity u")
})
public class UserEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private Integer isActive = 1;

    @Id
    @Column(name="email_address")
    private String emailAddress;

    @Column(name="password")
    private String password;

    @Column(name="salt")
    private String salt;

    @Column(name="cell_phone")
    private String cellPhone;

    @Column(name="user_type_id")
    private Integer userTypeId;


    //Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String emailAddress, Integer isActive, String cellPhone, Integer userTypeId, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.cellPhone = cellPhone;
        this.userTypeId = userTypeId;
        this.password = password;
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer isActive() {
        return isActive;
    }

    public String getCellPhone() { return cellPhone; }

    public String getEmailAddress() { return emailAddress; }

    public Integer getUserTypeId() { return userTypeId; }

    public String getPassword() { return password; }

    public String getSalt() { return salt; }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("emailAddress", emailAddress)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("active", isActive)
                .toString();
    }
}
