package com.udl.softarch.springexample.repositories;


import com.udl.softarch.springexample.models.Song;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by davidkaste on 23/02/15.
 */
public interface SongRepository extends PagingAndSortingRepository<Song, Long> {
    List<Song> findByName(@Param("name") String name);
    List<Song> findByBand(@Param("band") String band);
}
