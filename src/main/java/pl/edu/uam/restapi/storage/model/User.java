package pl.edu.uam.restapi.storage.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javafx.scene.input.DataFormat;
import sun.security.util.Password;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

@ApiModel(value = "User")
public class User {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Integer isActive;
    private String cellPhone;
    private Integer userTypeId;
    private String salt;
    private String password;
    private DateFormat createdAt;
    private DateFormat updatedAt;
    private DateFormat lastLogin;

    public User() {
    }

    public User(String firstName, String lastName, String emailAddress, String cellPhone, Integer userTypeId, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.cellPhone = cellPhone;
        this.userTypeId = userTypeId;
        this.password = password;

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        this.salt = sb.toString();

    }

    public User(String firstName, String lastName, String emailAddress, String password, String salt)
    {
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress=emailAddress;
        this.password=password;
        this.salt=salt;

    }

    public User(String firstName, String lastName, String emailAddress, Integer isActive, String cellPhone, Integer userTypeId, String salt, String password, DateFormat createdAt, DateFormat updatedAt, DateFormat lastLogin) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.isActive = isActive;
        this.cellPhone = cellPhone;
        this.userTypeId = userTypeId;
        this.salt = salt;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
    }

    public User(String emailAddress, String password)
    {
        this.emailAddress=emailAddress;
        this.password=password;
    }

    @ApiModelProperty(value = "User email", required = true)
    public String getEmailAddress() {
        return emailAddress;
    }

    @ApiModelProperty(value = "If user active?", required = true)
    public Integer getIsActive() {
        return isActive;
    }

    @ApiModelProperty(value = "User cellphone", required = true)
    public String getCellPhone() {
        return cellPhone;
    }

    @ApiModelProperty(value = "User type id", required = true)
    public Integer getUserTypeId() {
        return userTypeId;
    }

    @ApiModelProperty(value = "User first name", required = true)
    public String getFirstName() {
        return firstName;
    }

    @ApiModelProperty(value = "User last name", required = true)
    public String getLastName() {
        return lastName;
    }

    @ApiModelProperty(value = "Password salt", required = true)
    public String getSalt() {
        return salt;
    }

    @ApiModelProperty(value = "User password", required = true)
    public String getPassword() {
        return password;
    }

    @ApiModelProperty(value = "User created at", required = true)
    public DateFormat getCreatedAt() {
        return createdAt;
    }

    @ApiModelProperty(value = "User updated at", required = true)
    public DateFormat getUpdatedAt() {
        return updatedAt;
    }

    @ApiModelProperty(value = "User last login", required = true)
    public DateFormat getLastLogin() {
        return lastLogin;
    }
}
