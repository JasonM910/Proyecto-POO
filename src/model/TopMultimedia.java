package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TopMultimedia {
    private final List<Multimedia> imagenes = new ArrayList<>();
    private final List<Multimedia> videos = new ArrayList<>();
    private final List<Multimedia> podcasts = new ArrayList<>();

    public void agregarMultimedia(Multimedia recurso) {
        Objects.requireNonNull(recurso, "El recurso multimedia no puede ser nulo");
        switch (recurso.getTipo().toLowerCase()) {
            case "imagen" -> imagenes.add(recurso);
            case "video" -> videos.add(recurso);
            case "podcast" -> podcasts.add(recurso);
            default -> throw new IllegalArgumentException("Tipo de multimedia no soportado: " + recurso.getTipo());
        }
    }

    public List<Multimedia> getImagenes() {
        return Collections.unmodifiableList(imagenes);
    }

    public List<Multimedia> getVideos() {
        return Collections.unmodifiableList(videos);
    }

    public List<Multimedia> getPodcasts() {
        return Collections.unmodifiableList(podcasts);
    }
}
