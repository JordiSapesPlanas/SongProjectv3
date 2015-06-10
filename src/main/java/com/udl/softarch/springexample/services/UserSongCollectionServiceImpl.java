package com.udl.softarch.springexample.services;

import com.udl.softarch.springexample.models.Song;
import com.udl.softarch.springexample.models.SongCollection;
import com.udl.softarch.springexample.models.User;
import com.udl.softarch.springexample.repositories.SongCollectionRepository;
import com.udl.softarch.springexample.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by debian-jordi on 23/03/15.
 */
@Service
public class UserSongCollectionServiceImpl implements UserSongCollectionService {
    @Autowired
    SongCollectionRepository songCollectionRepository;

    @Autowired
    UserRepository userRepository;
    @Transactional(readOnly = true)

    @Override
    public User getUserAndSongCollection(Long userId) {
        User user = userRepository.findOne(userId);
        return user;
    }

    @Override
    public SongCollection addSongCollectionToUser(SongCollection songCollection) {
        User user = userRepository.findUserByEmail(songCollection.getEmail());
        if(user==null){
            String email = songCollection.getEmail();
            String name = email.substring(0, email.indexOf('@'));
            user = new User(name, email);
        }
        songCollectionRepository.save(songCollection);
        user.addSongCollection(songCollection);
        userRepository.save(user);
        return songCollection;
    }

    @Override
    public SongCollection updateSongCollectionFromUser(SongCollection songCollection, Long songcollectionId) {
        SongCollection oldSongCollection = songCollectionRepository.findOne(songcollectionId);
        oldSongCollection.setName(oldSongCollection.getName());
        oldSongCollection.setSongs(oldSongCollection.getSongs());
        if (!songCollection.getEmail().equals(oldSongCollection.getEmail())){
            throw new RuntimeException("email not equals");
        }
        return songCollectionRepository.save(songCollection);
    }

    @Override
    public void removeSongCollectionFromUser(Long songColllectionId) {

        SongCollection s = songCollectionRepository.findOne(songColllectionId);
        User u = userRepository.findUserByEmail(s.getEmail());
        if(u != null) {
            u.deleteSongCollection(s);
            userRepository.save(u);
        }
        songCollectionRepository.delete(s);
    }
}
