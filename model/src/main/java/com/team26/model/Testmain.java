package com.team26.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Testmain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        GenreDAO genreDAO = new FileGenre(new File("genres"));
        /*genreDAO.add(new Genre(01, "Pop"));
        genreDAO.add(new Genre(02, "Rock"));
        genreDAO.add(new Genre(03, "Rap"));
        genreDAO.add(new Genre(04, "Hip-Hop"));
        genreDAO.add(new Genre(05, "R&B"));*/

        TrackDAO trackDAO = new FileTrack(new File("tracks"));
        /*trackDAO.add(new Track(01, "dream", new LinkedList<>(Arrays.asList(new String[] {"Dima"})),
                "star II", 3, 15, new LinkedList<>(Arrays.asList(new Integer[]{1}))));
        trackDAO.add(new Track(03, "song", new LinkedList<>(Arrays.asList(new String[] {"Songer1, Songer2"})),
                "Album", 3, 00, new LinkedList<>(Arrays.asList(new Integer[] {1, 3}))));*/

        /*Track track = trackDAO.getTrack(1);
        trackDAO.update(track.getId(), new Track(track.getId(), "new name of song", track.getArtists(),
                track.getAlbum(), track.getMinutes(), track.getSeconds(), track.getGenresId()));*/

        List<Track> tracks = trackDAO.getAll();
        for (Track t : tracks) {
            System.out.println(t.getName() + "|" + t.getAlbum());
        }

    }
}
