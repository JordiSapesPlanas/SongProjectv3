package com.udl.softarch.springexample.models;



import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by debian-jordi on 23/03/15.
 */
@Entity
public class SongCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "not blank")
    @Size(max = 256)
    private String email;
    @NotBlank(message = "not blank")
    @Size(max = 256)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true) //Eagl
    private List<Song> songs = new ArrayList<Song>();

    public SongCollection(String email, String name) {
        this.email = email;
        this.name = name;
        //this.songs = new ArrayList<>();
    }

    
    public SongCollection(){
        this.email = null;
        this.name = null;
        //this.songs = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
