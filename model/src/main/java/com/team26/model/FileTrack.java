package com.team26.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileTrack implements TrackDAO {
    private File file;

    private static final Logger logger = Logger.getLogger(FileTrack.class.getName());

    private int count;

    public FileTrack(File file) {
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
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file, true));
                count = 0;
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка чтения содержимого файла жанров", e);
        }
    }

    public void add(Track newTrack) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            count++;
            objectOutputStream.writeObject(newTrack);
            objectOutputStream.writeObject(count);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    private boolean isExisting(Track track) {
        return false;
    }

    private void update(Track oldTrack, Track newTrack) {
        oldTrack.setName(newTrack.getName());
        oldTrack.setArtists(newTrack.getArtists());
        oldTrack.setAlbum(newTrack.getAlbum());
        oldTrack.setDuration(newTrack.getDuration());
        oldTrack.setGenresId(newTrack.getGenresId());
    }

    public void update(UUID id, Track newTrack) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            List<Track> tracks = getAll();
            for (Track track : tracks) {
                if (track.getId().equals(id)) {
                    update(track, newTrack);
                }
            }
            for (int i = 0; i < count; i++) {
                objectOutputStream.writeObject(tracks.get(i));
                objectOutputStream.writeObject(count);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    public void delete(UUID id) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            List<Track> tracks = getAll();
            for (int i = 0; i < tracks.size(); i++) {
                Track track = tracks.get(i);
                if (track.getId().equals(id)) {
                    count--;
                    tracks.remove(i);
                    break;
                }
            }
            for (int i = 0; i < count; i++) {
                objectOutputStream.writeObject(tracks.get(i));
                objectOutputStream.writeObject(count);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока вывода", e);
        }
    }

    public Track getTrack(UUID id) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Track track;
            for (int i = 0; i < count; i++) {
                track = (Track) objectInputStream.readObject();
                if (track.getId() == id) {
                    return track;
                }
                objectInputStream.readObject();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка чтения содержимого файла треков", e);
        }
        return null;
    }

    public List<Track> getAll() {
        List<Track> trackList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            for (int i = 0; i < count; i++) {
                trackList.add((Track) objectInputStream.readObject());
                objectInputStream.readObject();
            }
            return trackList;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Ошибка потока ввода", e);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Ошибка чтения содержимого файла треков", e);
        }
        return trackList;
    }
}
