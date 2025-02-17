package practica10.repository;

import practica10.exceptions.ExcepcionGestorPokedex;
import practica10.repository.model.Pokemon;

import java.math.BigDecimal;
import java.util.List;


public interface PokemonDao extends GenericDao<Pokemon, Integer> {

    boolean esPokemonLegendario(Integer id) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMayorAlturaQue(BigDecimal altura) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMenorAlturaQue(BigDecimal altura) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMayorPesoQue(BigDecimal peso) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMenorPesoQue(BigDecimal peso) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMayorAlturaPeso(BigDecimal altura, BigDecimal peso) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonsConMenorAlturaPeso(BigDecimal altura, BigDecimal peso) throws ExcepcionGestorPokedex;

    List<Pokemon> obtenerPokemonPorLetraInicial(String letraInicial) throws ExcepcionGestorPokedex;

    Integer recuentoGeneracionPokemon(int generacion) throws ExcepcionGestorPokedex;
}
