package com.udl.softarch.springexample.services;

import com.udl.softarch.springexample.models.Song;
import com.udl.softarch.springexample.models.SongCollection;
import com.udl.softarch.springexample.repositories.SongCollectionRepository;
import com.udl.softarch.springexample.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


/**
 * Created by ubuntujordi on 11/06/15.
 */
@Service
public class SongCollectionSongServiceImpl implements SongCollectionSongService{
    @Autowired
    SongRepository songRepository;
    @Autowired
    SongCollectionRepository songCollectionRepository;

    @Transactional
    @Override
    public void addSongToSongCollection(Song song, Long idSongCollection) {
        SongCollection s = songCollectionRepository.findOne(idSongCollection);
        Song song1 = songRepository.save(song);
        s.getSongs().add(song1);
        songCollectionRepository.save(s);
        System.out.println(2);

    }
    @Transactional(readOnly = true)
    @Override
    public Iterable<Song> getSongsFromSongCollection(Long idSongCollection) {
        SongCollection s = songCollectionRepository.findOne(idSongCollection);
        for(Song song: s.getSongs()){
            System.out.print("--------------");
            song.getId();
        }

        return s.getSongs();
    }

    @Transactional
    @Override
    public Song updateSongFromSongCollection(Song s, Long idSong, Long idSongCollection) {
        SongCollection songCollection =songCollectionRepository.findOne(idSongCollection);
        Song s1 = null;
        List <Song> songs = songCollection.getSongs();
        for (Song song: songs){
            if(song.getId() == idSong){
                song.setAlbum(s.getAlbum());
                song.setBand(s.getBand());
                song.setName(s.getName());
                song.setReleaseCountry(s.getReleaseCountry());
                song.setReleaseDate(s.getReleaseDate());
                songRepository.save(song);
                s1 = song;
            }

        }

        songCollectionRepository.save(songCollection);
        return s1;

    }
    @Transactional
    @Override
    public void deleteSongFromSongCollection(Long idSong, Long idSongCollection) {

        SongCollection songCollection =songCollectionRepository.findOne(idSongCollection);
        int index= -1;
        int i = 0;
        List <Song> songs = songCollection.getSongs();
        for (Song song: songs){
            if(song.getId() == idSong){
                index = i;

            }
            i++;
        }
        if(index != -1){
            songCollection.getSongs().remove(index);
            songRepository.delete(idSong);
            songCollectionRepository.save(songCollection);
        }


    }
    @Transactional
    @Override
    public Song getSongFromSongCollection(Long idSong, Long idSongCollection){
        SongCollection songCollection =songCollectionRepository.findOne(idSongCollection);
        for (Song song: songCollection.getSongs()){
            if(song.getId() == idSong){
                return song;
            }
        }
        return null;
    }

}
