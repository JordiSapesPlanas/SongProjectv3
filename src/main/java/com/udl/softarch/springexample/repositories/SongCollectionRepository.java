package com.udl.softarch.springexample.repositories;

import com.udl.softarch.springexample.controllers.SongController;
import com.udl.softarch.springexample.models.Song;
import com.udl.softarch.springexample.models.SongCollection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by debian-jordi on 23/03/15.
 */
public interface SongCollectionRepository extends PagingAndSortingRepository<SongCollection ,Long>{
    List<SongCollection> findByName(@Param("name") String name);
    List<SongCollection> findByEmail(@Param("email")String email);
}
