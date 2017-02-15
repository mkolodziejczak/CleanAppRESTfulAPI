package pl.edu.uam.restapi.storage.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.uam.restapi.storage.entity.UserEntity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ApiModel(value = "User")
public class Entry {
    private Integer id;
    private String userEmail;
    private String category;
    private String latitude;
    private String longitude;
    private String date;
    private String description;
    private String status;
    private Integer score;
    private String imagesArr;

    private static final Logger LOGGER = LoggerFactory.getLogger(Entry.class);

    public Entry() {
    }


    public Entry(Integer id, String userEmail, String category, String latitude, String longitude, String date, String description, Integer score, String imagesArr, String status) {

        this.id = id;
        this.userEmail = userEmail;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.score = score;
        this.imagesArr = imagesArr;
        this.status = status;
    }

    public Entry(String userEmail, String category, String latitude, String longitude, String date, String description, String imagesArr) {

        this.userEmail = userEmail;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.imagesArr = imagesArr;

        String[] photoArray = imagesArr.split("#image_separator#");
        byte[] firstPhoto = Base64.decodeBase64(photoArray[0]);

        OutputStream stream = null;
        String fileName=(System.currentTimeMillis() / 1000L)+"";
        try {
            stream = new FileOutputStream((fileName + "_1.bmp"));
            stream.write(firstPhoto);
            stream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(photoArray.length > 1) {
            byte[] secondPhoto = Base64.decodeBase64(photoArray[1]);
            try {
                stream = new FileOutputStream((fileName + "_2.bmp"));
                stream.write(secondPhoto);
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.imagesArr="["+fileName + "_1.bmp,"+fileName + "_2.bmp"+"]";
        }
        else
        {
            this.imagesArr="["+fileName + "_1.bmp]";
        }

    }

    public Entry(String userEmail, String category, String latitude, String longitude, String date, String description, String photoOne, String photoTwo) {

        this.userEmail = userEmail;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        byte[] firstPhoto = Base64.decodeBase64(photoOne);
        byte[] secondPhoto = Base64.decodeBase64(photoTwo);
        OutputStream stream = null;
        String fileName=(System.currentTimeMillis() / 1000L)+"";
        try {
            stream = new FileOutputStream((fileName + "_1.bmp"));
            stream.write(firstPhoto);
            stream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            stream = new FileOutputStream((fileName + "_2.bmp"));
            stream.write(secondPhoto);
            stream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.imagesArr="["+fileName + "_1.bmp,"+fileName + "_2.bmp"+"]";

    }

    @ApiModelProperty(value = "Entry id", required = true)
    public Integer getId() {
        return id;
    }

    @ApiModelProperty(value = "User email", required = true)
    public String getUserEmail() {
        return userEmail;
    }

    @ApiModelProperty(value = "Entry category", required = true)
    public String getCategory() {
        return category;
    }

    @ApiModelProperty(value = "Entry latitude", required = true)
    public String getLatitude() {
        return latitude;
    }

    @ApiModelProperty(value = "Entry longitude", required = true)
    public String getLongitude() {
        return longitude;
    }

    @ApiModelProperty(value = "Entry date", required = true)
    public String getDate() {
        return date;
    }

    @ApiModelProperty(value = "Entry description", required = true)
    public String getDescription() {
        return description;
    }

    @ApiModelProperty(value = "Entry status", required = true)
    public String getStatus() {
        return status;
    }

    @ApiModelProperty(value = "Entry score", required = true)
    public Integer getScore() {
        return score;
    }

    @ApiModelProperty(value = "Entry image array", required = true)
    public String getImagesArr() {
        return imagesArr;
    }

    public void setStatus(String status)
    {
        this.status=status;
    }


}
