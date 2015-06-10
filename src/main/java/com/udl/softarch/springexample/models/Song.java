package com.udl.softarch.springexample.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by davidkaste on 23/02/15.
 */

@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "This field cannot be blank")
    @Size(max = 256, message = "Name maximum length is {max} characters long")
    private String name;

    @Size(max = 256, message = "Band name, max length is {max}")
    private String band;

    @Size(max = 256, message = "Name maximum length is {max} characters long")
    private String releaseCountry;

    @Size(max = 256, message = "Name maximum length is {max} characters long")
    private String releaseDate;

    @Size(max = 256, message = "Name maximum length is {max} characters long")
    private String album;

    public Song() {}

    public Song(String name, String band, String album, String releaseCountry, String releaseDate) {
        this.name = name;
        this.band = band;
        this.releaseCountry = releaseCountry;
        this.releaseDate = releaseDate;
        this.album = album;

    }

    public Long getId() {
        return Id;
    }

    public String getBand() {
        return band;
    }

    public String getReleaseCountry() {
        return releaseCountry;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getAlbum() {
        return album;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBand(String band) {
        this.band = band;
    }


}
