package com.team26.model;

import java.util.List;
import java.util.UUID;

public interface GenreDAO {

    void add(Genre newGenre);
    void update(UUID id, String newName);
    void delete(UUID id, FileTrack toDelete);
    Genre getGenre(UUID id);
    List<Genre> getAll();

}
