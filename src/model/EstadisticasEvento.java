package model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EstadisticasEvento {
    private String rendimiento;
    private Evento rankingGeneral;
    private Categoria topCategoria;
    private Carrera topCarrera;
    private Competidor podium;

    public String getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(String rendimiento) {
        this.rendimiento = Objects.requireNonNull(rendimiento, "El rendimiento no puede ser nulo");
    }

    public Evento getRankingGeneral() {
        return rankingGeneral;
    }

    public void setRankingGeneral(Evento rankingGeneral) {
        this.rankingGeneral = Objects.requireNonNull(rankingGeneral, "El evento del ranking no puede ser nulo");
    }

    public Categoria getTopCategoria() {
        return topCategoria;
    }

    public void setTopCategoria(Categoria topCategoria) {
        this.topCategoria = Objects.requireNonNull(topCategoria, "La categoría destacada no puede ser nula");
    }

    public Carrera getTopCarrera() {
        return topCarrera;
    }

    public void setTopCarrera(Carrera topCarrera) {
        this.topCarrera = Objects.requireNonNull(topCarrera, "La carrera destacada no puede ser nula");
    }

    public Competidor getPodium() {
        return podium;
    }

    public void setPodium(Competidor podium) {
        this.podium = Objects.requireNonNull(podium, "El competidor del pódium no puede ser nulo");
    }

    public String generarReporte(List<Competidor> competidores) {
        Objects.requireNonNull(competidores, "La lista de competidores no puede ser nula");
        String resumenCompetidores = competidores.stream()
                .map(competidor -> competidor.getNombre() + " (" + competidor.getNumeroCompetidor() + ")")
                .collect(Collectors.joining(", "));

        return "Rendimiento: " + rendimiento +
                " | Top categoría: " + (topCategoria != null ? topCategoria.getNombre() : "N/A") +
                " | Top carrera: " + (topCarrera != null ? topCarrera.getNombre() : "N/A") +
                " | Competidores destacados: " + resumenCompetidores;
    }
}
