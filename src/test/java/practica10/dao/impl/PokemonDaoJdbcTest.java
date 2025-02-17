package practica10.dao.impl;

import org.junit.Before;
import org.junit.Test;
import practica10.repository.PokemonDao;
import practica10.repository.model.Pokemon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PokemonDaoJdbcTest {

    private PokemonDao pokemonDaoMock;

    @Before
    public void setUp() throws Exception {
        pokemonDaoMock = mock(PokemonDao.class);
       pokemonDaoMock.deleteAll();
    }

    @Test
    public void testCreatePokemon() throws Exception {
        Pokemon pokemon = new Pokemon("Pikachu", LocalDate.of(2024, 2, 1), false, 1, new BigDecimal("6.0"), new BigDecimal("0.4"), "Ratón eléctrico");
        when(pokemonDaoMock.create(any(Pokemon.class))).thenReturn(1);
        when(pokemonDaoMock.getById(1)).thenReturn(pokemon);

    }

    @Test
    public void testGetAll() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Charmander", LocalDate.of(2024, 2, 2), false, 1, new BigDecimal("8.5"), new BigDecimal("0.6"), "Lagarto de fuego"),
                new Pokemon("Bulbasaur", LocalDate.of(2024, 2, 3), false, 1, new BigDecimal("6.9"), new BigDecimal("0.7"), "Planta y veneno")
        );
        when(pokemonDaoMock.getAll()).thenReturn(pokemons);
    }

    @Test
    public void testGetById() throws Exception {
        Pokemon pokemon = new Pokemon("Squirtle", LocalDate.of(2024, 2, 4), false, 1, new BigDecimal("9.0"), new BigDecimal("0.5"), "Tortuga de agua");
        when(pokemonDaoMock.create(any(Pokemon.class))).thenReturn(1);
        when(pokemonDaoMock.getById(1)).thenReturn(pokemon);

    }

    @Test
    public void testUpdatePokemon() throws Exception {
        Pokemon pokemon = new Pokemon("Eevee", LocalDate.of(2024, 2, 5), false, 1, new BigDecimal("6.5"), new BigDecimal("0.3"), "pokemon evolucion");
        when(pokemonDaoMock.create(any(Pokemon.class))).thenReturn(1);
        when(pokemonDaoMock.getById(1)).thenReturn(pokemon);
        pokemon.setNombre("Eevee Modificado");
        when(pokemonDaoMock.getById(1)).thenReturn(pokemon);

    }

    @Test
    public void testDeletePokemon() throws Exception {
        Pokemon pokemon = new Pokemon("Gengar", LocalDate.of(2024, 2, 6), false, 1, new BigDecimal("40.5"), new BigDecimal("1.5"), "Fantasma");
        when(pokemonDaoMock.create(any(Pokemon.class))).thenReturn(1);
        when(pokemonDaoMock.getById(1)).thenReturn(pokemon);
        doNothing().when(pokemonDaoMock).delete(1);
        when(pokemonDaoMock.getById(1)).thenReturn(null);

    }

    @Test
    public void testDeleteAll() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Jigglypuff", LocalDate.of(2024, 2, 7), false, 1, new BigDecimal("5.5"), new BigDecimal("0.5"), "Canta y duerme"),
                new Pokemon("Meowth", LocalDate.of(2024, 2, 8), false, 1, new BigDecimal("4.2"), new BigDecimal("0.4"), "Gato moneda")
        );
        when(pokemonDaoMock.getAll()).thenReturn(pokemons);
        doNothing().when(pokemonDaoMock).deleteAll();
        when(pokemonDaoMock.getAll()).thenReturn(new ArrayList<>());

    }

    @Test
    public void testEsPokemonLegendario() throws Exception {
        when(pokemonDaoMock.esPokemonLegendario(1)).thenReturn(true);
    }

    @Test
   public void testObtenerPokemonsConMayorAlturaPeso() throws Exception {

        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMayorAlturaPeso(new BigDecimal("5.0"), new BigDecimal("300.0"))).thenReturn(pokemons);
    }

    @Test
    public void testObtenerPokemonsConMenorAlturaPeso() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMenorAlturaPeso(new BigDecimal("5.0"), new BigDecimal("300.0"))).thenReturn(pokemons);

    }


    @Test
    public void testObtenerPokemonsConMayorAlturaQue() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMayorAlturaPeso(new BigDecimal("5.0"), new BigDecimal("300.0"))).thenReturn(pokemons);
    }

    @Test
    public void testObtenerPokemonsConMenorAlturaQue() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMenorAlturaPeso(new BigDecimal("5.0"), new BigDecimal("300.0"))).thenReturn(pokemons);
    }

    @Test
    public void testObtenerPokemonsConMayorPesoQue() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMayorPesoQue(( new BigDecimal("300.0")))).thenReturn(pokemons);
    }


    @Test
    public void testObtenerPokemonsConMenorPesoQue() throws Exception {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Gyarados", LocalDate.of(2024, 2, 10), false, 1, new BigDecimal("6.5"), new BigDecimal("335.0"), "Dragón marino")
        );
        when(pokemonDaoMock.obtenerPokemonsConMenorPesoQue(new BigDecimal("5.0"))).thenReturn(pokemons);
    }

    @Test
    public void testObtenerPokemonsPorLetraInicial() throws Exception {
        Pokemon pikachu = new Pokemon("Pikachu", LocalDate.of(2024, 2, 12), false, 1, new BigDecimal("2.1"), new BigDecimal("6.0"), "Pika pika");
        Pokemon pidgey = new Pokemon("Pidgey", LocalDate.of(2024, 1, 15), false, 1, new BigDecimal("1.0"), new BigDecimal("0.3"), "Pequeño pájaro");
        Pokemon bulbasaur = new Pokemon("Bulbasaur", LocalDate.of(2024, 1, 20), false, 1, new BigDecimal("6.9"), new BigDecimal("0.7"), "Pokemon planta");


        when(pokemonDaoMock.create(pikachu)).thenReturn(1);
        when(pokemonDaoMock.create(pidgey)).thenReturn(2);
        when(pokemonDaoMock.create(bulbasaur)).thenReturn(3);


        List<Pokemon> pokemons = Arrays.asList(pikachu, pidgey);
        when(pokemonDaoMock.obtenerPokemonPorLetraInicial("P")).thenReturn(pokemons);


        List<Pokemon> resultado = pokemonDaoMock.obtenerPokemonPorLetraInicial("P");
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }


    @Test
    public void testRecuentoDeGeneracion() throws Exception {
        Pokemon pikachu = new Pokemon("Pikachu", LocalDate.of(2024, 2, 12), false, 1, new BigDecimal("2.1"), new BigDecimal("6.0"), "Pika pika");
        Pokemon pidgey = new Pokemon("Pidgey", LocalDate.of(2024, 1, 15), false, 1, new BigDecimal("1.0"), new BigDecimal("0.3"), "Pequeño pájaro");
        Pokemon tapukoko = new Pokemon("Tapu Koko", LocalDate.of(2024, 1, 20), true, 7, new BigDecimal("22.9"), new BigDecimal("1.7"), "Tapu tapu");


        when(pokemonDaoMock.create(pikachu)).thenReturn(1);
        when(pokemonDaoMock.create(pidgey)).thenReturn(2);
        when(pokemonDaoMock.create(tapukoko)).thenReturn(3);
        when(pokemonDaoMock.recuentoGeneracionPokemon(1)).thenReturn(2);

        int generacionBuscada = pokemonDaoMock.recuentoGeneracionPokemon(1);
        assertEquals(2, generacionBuscada);
    }
}


