package com.team26.model.data;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository {
    private static final Path DEFAULT_FILE_PATH = Paths.get("");

    public static final Repository INSTANCE = new Repository();

    private Repository() {
    }

    public void write(Path filePath, MusicLibraryDTO musicLibraryDTO) {
    }

    private MusicLibraryDTO read(Path filePath) {
        return new MusicLibraryDTO();
    }

    public MusicLibraryDTO getLibrary() {
        return read(DEFAULT_FILE_PATH);

    }


}
