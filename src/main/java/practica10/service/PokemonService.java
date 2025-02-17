package practica10.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import practica10.exceptions.ExcepcionGestorPokedex;
import practica10.repository.PokemonDao;
import practica10.repository.model.Pokemon;
import practica10.service.dto.PokemonDto;
import practica10.utils.LocalDataTypeAdapter;
import practica10.utils.UtilidadesPokemon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PokemonService {
    private UtilidadesPokemon utilidadesPokemon;
    private Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,new LocalDataTypeAdapter()).create();

    private PokemonDao pokemonDao;

    public PokemonService(PokemonDao pokemonDao) {
        this.pokemonDao = pokemonDao;
    }

    public List<Pokemon> getAll() throws Exception {
        return pokemonDao.getAll();
    }

    public Pokemon getById(Integer id) throws Exception {
        return pokemonDao.getById(id);
    }

    public Integer create(Pokemon pokemon) throws Exception {
        return pokemonDao.create(pokemon);
    }

    public Pokemon update(Pokemon pokemon) throws Exception {
        return pokemonDao.update(pokemon);
    }

    public void delete(Integer id) throws Exception {
        pokemonDao.delete(id);
    }

    public void deleteAll() throws Exception {
        pokemonDao.deleteAll();
    }

    public boolean esLegendario(Integer id) throws Exception {
        return pokemonDao.esPokemonLegendario(id);
    }

    public List<Pokemon> obtenerPokemonsConMayorAlturaQue(BigDecimal altura) throws Exception {
        return pokemonDao.obtenerPokemonsConMayorAlturaQue(altura);
    }

    public List<Pokemon> obtenerPokemonsConMenorAlturaQue(BigDecimal altura) throws Exception {
        return pokemonDao.obtenerPokemonsConMenorAlturaQue(altura);
    }

    public List<Pokemon> obtenerPokemonsConMayorPesoQue(BigDecimal peso) throws Exception {
        return pokemonDao.obtenerPokemonsConMayorPesoQue(peso);
    }

    public List<Pokemon> obtenerPokemonsConMenorPesoQue(BigDecimal peso) throws Exception {
        return pokemonDao.obtenerPokemonsConMenorPesoQue(peso);
    }

    public List<Pokemon> obtenerPokemonsConMayorAlturaPeso(BigDecimal altura, BigDecimal peso) throws Exception {
        return pokemonDao.obtenerPokemonsConMayorAlturaPeso(altura, peso);
    }

    public List<Pokemon> obtenerPokemonsConMenorAlturaPeso(BigDecimal altura, BigDecimal peso) throws Exception {
        return pokemonDao.obtenerPokemonsConMenorAlturaPeso(altura, peso);
    }

    public List<Pokemon> obtenerPokemonPorLetraInicial(String letraInicial) throws ExcepcionGestorPokedex {
        return pokemonDao.obtenerPokemonPorLetraInicial(letraInicial);
    }
    public Integer recuentoGeneracionPokemon(int generacion) throws ExcepcionGestorPokedex{
        return pokemonDao.recuentoGeneracionPokemon(generacion);
    }



    public PokemonDto toDto (Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();

        pokemonDto.setNumeroPokedex(pokemon.getNumeroPokedex());
        pokemonDto.setNombrePokemon(pokemon.getNombre());
        pokemonDto.setFechaCaptura(pokemon.getFechaCaptura());
        pokemonDto.setDescripcion(pokemon.getDescripcion());

        return pokemonDto;
    }

    public List<PokemonDto> toDtos (List<Pokemon> pokemons){
        List<PokemonDto> pokemonDtosList = new ArrayList<>();

        for (Pokemon p :pokemons){
            PokemonDto pokemonDto = new PokemonDto();

            pokemonDto.setNumeroPokedex(p.getNumeroPokedex());
            pokemonDto.setNombrePokemon(p.getNombre());
            pokemonDto.setFechaCaptura(p.getFechaCaptura());
            pokemonDto.setDescripcion(p.getDescripcion());

            pokemonDtosList.add(pokemonDto);

        }
        return pokemonDtosList;
    }

    public String getPokemonDtoAsJson() throws Exception{
        List <Pokemon> pokemonList = pokemonDao.getAll();
        List<PokemonDto> pokemonDtoList = toDtos(pokemonList);

        return gson.toJson(pokemonDtoList);
    }

    public String serializePokemon(Pokemon pokemon) {
        return gson.toJson(pokemon);
    }

    public Pokemon deserializePokemon(String json) {
        return gson.fromJson(json, Pokemon.class);
    }
}
