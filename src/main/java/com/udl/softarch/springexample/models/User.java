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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "your name, can not be blank")
    @Size(max = 256)
    private String name;

    @NotBlank(message = "your email")
    @Size(max = 256)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SongCollection> songcollection = new ArrayList<>();

    public User(){}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SongCollection> getSongcollection() {
        return songcollection;
    }
    public void addSongCollection(SongCollection songCollection){
        songcollection.add(songCollection);
    }
    public void deleteSongCollection(SongCollection s){
        songcollection.remove(s);
    }
    public void setSongsCollection(List<SongCollection> songs) {
        this.songcollection = songs;
    }
}
