package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TopSugerencia {
    private final List<String> listaSugerencias = new ArrayList<>();
    private String categoria;

    public TopSugerencia(String categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría no puede ser nula");
    }

    public List<String> getListaSugerencias() {
        return Collections.unmodifiableList(listaSugerencias);
    }

    public void agregarSugerencia(String sugerencia) {
        listaSugerencias.add(Objects.requireNonNull(sugerencia, "La sugerencia no puede ser nula"));
    }
}
