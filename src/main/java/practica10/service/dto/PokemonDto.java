package practica10.service.dto;

import java.time.LocalDate;

public class PokemonDto {

    private int numeroPokedex;
    private String nombrePokemon;
    private LocalDate fechaCaptura;
    private String descripcion;

    public PokemonDto() {
    }

    public PokemonDto(int numeroPokedex, String nombrePokemon, LocalDate fechaCaptura, String descripcion) {
        this.numeroPokedex = numeroPokedex;
        this.nombrePokemon = nombrePokemon;
        this.fechaCaptura = fechaCaptura;
        this.descripcion = descripcion;
    }

    public int getNumeroPokedex() {
        return numeroPokedex;
    }

    public void setNumeroPokedex(int numeroPokedex) {
        this.numeroPokedex = numeroPokedex;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public LocalDate getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(LocalDate fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
