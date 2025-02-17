package practica10.repository.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pokemon {
    private int numeroPokedex;
    private String nombre;
    private LocalDate fechaCaptura;
    private boolean esLegendario;
    private int generacion;
    private BigDecimal peso;
    private BigDecimal altura;
    private String descripcion;

    public Pokemon() {
    }

    public Pokemon(int numeroPokedex, String nombre, LocalDate fechaCaptura, boolean esLegendario, int generacion, BigDecimal peso, BigDecimal altura, String descripcion) {
        this.numeroPokedex = numeroPokedex;
        this.nombre = nombre;
        this.fechaCaptura = fechaCaptura;
        this.esLegendario = esLegendario;
        this.generacion = generacion;
        this.peso = peso;
        this.altura = altura;
        this.descripcion = descripcion;
    }


    public Pokemon(String nombre, LocalDate fechaCaptura, boolean esLegendario, int generacion, BigDecimal peso, BigDecimal altura, String descripcion) {
        this.nombre = nombre;
        this.fechaCaptura = fechaCaptura;
        this.esLegendario = esLegendario;
        this.generacion = generacion;
        this.peso = peso;
        this.altura = altura;
        this.descripcion = descripcion;
    }

    public int getNumeroPokedex() {
        return numeroPokedex;
    }

    public void setNumeroPokedex(int numeroPokedex) {
        this.numeroPokedex = numeroPokedex;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(LocalDate fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public boolean isEsLegendario() {
        return esLegendario;
    }

    public void setEsLegendario(boolean esLegendario) {
        this.esLegendario = esLegendario;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return
                "#" + numeroPokedex +
                " " + nombre +
                " -- Capturado:" + fechaCaptura +
                " -- Legendario:" + esLegendario +
                " -- Generacion:" + generacion +
                " -- Peso:" + peso +
                " -- Altura:" + altura +
                " -- Descripcion:" + descripcion ;
    }
}
