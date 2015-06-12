package com.udl.softarch.springexample.services;

import com.udl.softarch.springexample.models.Song;

import java.util.List;

/**
 * Created by ubuntujordi on 11/06/15.
 */
public interface SongCollectionSongService {
    void addSongToSongCollection(Song song, Long idSongCollection);
    Iterable<Song> getSongsFromSongCollection(Long idSongCollection);
    Song updateSongFromSongCollection(Song s, Long idSong, Long idSongCollection);
    void deleteSongFromSongCollection(Long idSong, Long idSongCollection);
    Song getSongFromSongCollection(Long s, Long idSongCollection);
}
