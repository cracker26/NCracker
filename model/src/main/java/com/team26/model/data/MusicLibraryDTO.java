package com.team26.model.data;

import com.team26.model.Track;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class MusicLibraryDTO {
    private Map<UUID, Track> tracks = Collections.emptyMap();

    public Map<UUID, Track> getTracks() {
        return tracks;
    }

    public void setTracks(Map<UUID, Track> tracks) {
        this.tracks = tracks;
    }

}
