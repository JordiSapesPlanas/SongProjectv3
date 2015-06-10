package com.udl.softarch.springexample.services;

import com.udl.softarch.springexample.models.Song;
import com.udl.softarch.springexample.models.SongCollection;
import com.udl.softarch.springexample.models.User;

/**
 * Created by debian-jordi on 23/03/15.
 */
public interface UserSongCollectionService {
    User getUserAndSongCollection(Long userId);
    SongCollection addSongCollectionToUser(SongCollection songCollection);
    SongCollection updateSongCollectionFromUser(SongCollection updateSongCollection, Long songId);
    void removeSongCollectionFromUser(Long songId);
}
