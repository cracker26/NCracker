package com.team26.model;

import com.team26.model.data.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileGenre implements GenreDAO {

    private File file;
    private int count;

    private static final Logger logger = Logger.getLogger(FileGenre.class.getName());

    public FileGenre(File file) {
        try {
            logger.addHandler(new FileHandler());
            this.file = file;
            if (file.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                Integer countFromFile = 0;
                while (true) {
                    try {
                        objectInputStream.readObject();
                        countFromFile = (Integer) objectInputStream.readObject();
                    } catch (EOFException e) {
                        break;
                    }
                }
                count = countFromFile;
            } else {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                count = 0;
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода/вывода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка чтения содержимого файла", e);
        }
    }

    @Override
    public void add(Genre newGenre) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            count++;
            objectOutputStream.writeObject(newGenre);
            objectOutputStream.writeObject(count);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    private void update(Genre genre, String newName) {
        genre.setName(newName);
    }

    @Override
    public void update(UUID id, String newName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            List<Genre> genres = getAll();
            for (Genre genre : genres) {
                if (genre.getId().equals(id)) {
                    update(genre, newName);
                }
            }
            for (int i = 0; i < count; i++) {
                objectOutputStream.writeObject(genres.get(i));
                objectOutputStream.writeObject(count);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    @Override
    public void delete(UUID id, FileTrack trackFile) {
        List<Genre> genreList = getAll();
        for (int i = 0; i < genreList.size(); i++) {
            if (genreList.get(i).getId().equals(id)) {
                genreList.remove(i);
                count--;
            }
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < count; i++) {
                objectOutputStream.writeObject(genreList.get(i));
                objectOutputStream.writeObject(count);
            }
            List<Track> tracks = trackFile.getAll();
            boolean isDeleted = false;
            for (int i = 0; i < tracks.size(); i++) {
                Track track = tracks.get(i);
                List<UUID> genresOfTrack = track.getGenresId();
                for (int j = 0; j < genresOfTrack.size(); j++) {
                    if (genresOfTrack.get(j).equals(id)) {
                        genresOfTrack.remove(j);
                        isDeleted = true;
                    }
                }
                if (isDeleted) {
                    track.setGenresId(genresOfTrack);
                    trackFile.update(track.getId(), track);
                    isDeleted = false;
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    @Override
    public Genre getGenre(UUID id) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Genre genre;
            for (int i = 0; i < count; i++) {
                genre = (Genre) objectInputStream.readObject();
                if (genre.getId().equals(id)) {
                    return genre;
                }
                objectInputStream.readObject();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка при чтении чтения содержимого файла треков", e);
        }
        return null;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            for (int i = 0; i < count; i++) {
                genreList.add((Genre) objectInputStream.readObject());
                objectInputStream.readObject();
            }
            return genreList;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка чтения содержимого файла треков", e);
        }
        return genreList;
    }
}
