package pl.edu.uam.restapi.storage.entity;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;

@Entity
@Table(name = "entries")
@NamedQueries({
        @NamedQuery(name = "entries.findAll", query = "SELECT u FROM EntryEntity u")
})
public class EntryEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntity.class);

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "latitude")
    private String latitude;

    @Column(name="longitude")
    private String longitude;

    @Column(name="category")
    private String category;

    @Column(name="description")
    private String description;

    @Column(name="images_arr")
    private String imagesArr;

    @Column(name="score")
    private Integer score;

    @Column(name="date")
    private String date;

    @Column(name="status")
    private String status;

    //Lifecycle methods -- Pre/PostLoad, Pre/PostPersist...
    @PostLoad
    private void postLoad() {
        LOGGER.info("postLoad: {}", toString());
    }

    public EntryEntity() {
    }

    public EntryEntity(Integer id, String userEmail, String category, String latitude, String longitude, String date, String description, String status, Integer score, String imagesArr) {
        this.id = id;
        this.userEmail = userEmail;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.status = status;
        this.score = score;
        this.imagesArr = imagesArr;
    }

    public EntryEntity(String userEmail, String category, String latitude, String longitude, String date, String description, String imagesArr) {
        this.userEmail = userEmail;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.imagesArr = imagesArr;
    }


    public Integer getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCategory() {
        return category;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Integer getScore() { return score; }

    public String getImagesArr() { return imagesArr; }

    public void setStatus(String status) { this.status = status; }

    public void setScore(Integer score) { this.score = score; }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("userEmail", userEmail)
                .add("category", category)
                .add("description", description)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .add("date", date)
                .add("status", status)
                .add("score", score)
                .add("imagesArr", imagesArr)
                .toString();
    }
}
