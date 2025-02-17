package practica10.service;


import org.junit.Before;
import org.junit.Test;
import practica10.repository.PokemonDao;
import practica10.repository.model.Pokemon;
import practica10.service.dto.PokemonDto;
import practica10.utils.UtilidadesPokemon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class PokemonServiceTest {

    private PokemonDao pokemonDaoMock;
    private PokemonService pokemonService;
    private UtilidadesPokemon utilidadesPokemon = new UtilidadesPokemon();

    @Before
    public void setUp(){
        pokemonDaoMock = mock(PokemonDao.class);
        pokemonService = new PokemonService(pokemonDaoMock);
    }

    @Test
    public void testGetAllPokemons() throws Exception {
         List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Pikachu", LocalDate.now(), false, 25, new BigDecimal("0.4"), new BigDecimal("6.0"), "Ratón eléctrico"),
                new Pokemon("Charizard", LocalDate.now(), false, 6, new BigDecimal("1.7"), new BigDecimal("90.5"), "Lagarto de fuego")
        );
        when(pokemonDaoMock.getAll()).thenReturn(pokemons);

         List<Pokemon> resultado = pokemonService.getAll();

         assertEquals(2, resultado.size());
        assertEquals("Pikachu", resultado.get(0).getNombre());
        assertEquals("Charizard", resultado.get(1).getNombre());
        verify(pokemonDaoMock, times(1)).getAll();
    }


    @Test
    public void testCreatePokemon() throws Exception {
        Pokemon pokemon = new Pokemon("Mew", LocalDate.now(), true, 151, new BigDecimal("0.4"), new BigDecimal("4.0"), "Pokémon psíquico");
        when(pokemonDaoMock.create(pokemon)).thenReturn(151);

        Integer resultado = pokemonService.create(pokemon);

        assertEquals(151, resultado.intValue());
        verify(pokemonDaoMock, times(1)).create(pokemon);
    }

    @Test
    public void testUpdatePokemon() throws Exception {
        Pokemon pokemon = new Pokemon("Mewtwo", LocalDate.now(), true, 150, new BigDecimal("2.0"), new BigDecimal("122.0"), "Pokémon genético");
        when(pokemonDaoMock.update(pokemon)).thenReturn(pokemon);

        Pokemon resultado = pokemonService.update(pokemon);

        assertNotNull(resultado);
        assertEquals("Mewtwo", resultado.getNombre());
        verify(pokemonDaoMock, times(1)).update(pokemon);
    }

    @Test
    public void testDeletePokemon() throws Exception {
        int id = 150;
        doNothing().when(pokemonDaoMock).delete(id);

        pokemonService.delete(id);

        verify(pokemonDaoMock, times(1)).delete(id);
    }

    @Test
    public void testDeleteAllPokemons() throws Exception {
        doNothing().when(pokemonDaoMock).deleteAll();

        pokemonService.deleteAll();

        verify(pokemonDaoMock, times(1)).deleteAll();
    }

    @Test
    public void testEsLegendario() throws Exception {
        int id = 150;
        when(pokemonDaoMock.esPokemonLegendario(id)).thenReturn(true);

        boolean resultado = pokemonService.esLegendario(id);

        assertTrue(resultado);
        verify(pokemonDaoMock, times(1)).esPokemonLegendario(id);
    }

    @Test
    public void testObtenerPokemonsConMayorAlturaQue() throws Exception {
        BigDecimal altura = new BigDecimal("1.0");
        List<Pokemon> pokemons = Collections.singletonList(new Pokemon("Gyarados", LocalDate.now(), false, 130, new BigDecimal("6.5"), new BigDecimal("235.0"), "Dragón marino"));
        when(pokemonDaoMock.obtenerPokemonsConMayorAlturaQue(altura)).thenReturn(pokemons);

        List<Pokemon> resultado = pokemonService.obtenerPokemonsConMayorAlturaQue(altura);

        assertEquals(1, resultado.size());
        assertEquals("Gyarados", resultado.get(0).getNombre());
        verify(pokemonDaoMock, times(1)).obtenerPokemonsConMayorAlturaQue(altura);
    }

    @Test
    public void testToDto() {
        Pokemon pokemon = new Pokemon("Pikachu", LocalDate.now(), false, 25, new BigDecimal("0.4"), new BigDecimal("6.0"), "Ratón eléctrico");
        PokemonDto pokemonDto = pokemonService.toDto(pokemon);

        assertEquals(pokemon.getNumeroPokedex(), pokemonDto.getNumeroPokedex());
        assertEquals(pokemon.getNombre(), pokemonDto.getNombrePokemon());
        assertEquals(pokemon.getFechaCaptura(), pokemonDto.getFechaCaptura());
        assertEquals(pokemon.getDescripcion(), pokemonDto.getDescripcion());
    }

    @Test
    public void testToDtos() {
        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Pikachu", LocalDate.now(), false, 25, new BigDecimal("0.4"), new BigDecimal("6.0"), "Ratón eléctrico"),
                new Pokemon("Charizard", LocalDate.now(), false, 6, new BigDecimal("1.7"), new BigDecimal("90.5"), "Lagarto de fuego")
        );
        List<PokemonDto> pokemonDtos = pokemonService.toDtos(pokemons);

        assertEquals(pokemons.size(), pokemonDtos.size());
        assertEquals(pokemons.get(0).getNombre(), pokemonDtos.get(0).getNombrePokemon());
        assertEquals(pokemons.get(1).getNombre(), pokemonDtos.get(1).getNombrePokemon());
    }

    @Test
    public void testGetPokemonDtoAsJson() throws Exception {

        List<Pokemon> pokemons = Arrays.asList(
                new Pokemon("Pikachu", LocalDate.of(1996, 7, 8), false, 25, new BigDecimal("0.4"), new BigDecimal("6.0"), "Ratón eléctrico"),
                new Pokemon("Charizard", LocalDate.of(1998, 5, 12), false, 6, new BigDecimal("1.7"), new BigDecimal("90.5"), "Lagarto de fuego")
        );


        when(pokemonDaoMock.getAll()).thenReturn(pokemons);


        String resultadoJson = pokemonService.getPokemonDtoAsJson();


        assertNotNull(resultadoJson);
        assertTrue(resultadoJson.contains("\"nombrePokemon\": \"Pikachu\""));
        assertTrue(resultadoJson.contains("\"fechaCaptura\": \"1996-07-08\""));
        assertTrue(resultadoJson.contains("\"nombrePokemon\": \"Charizard\""));
        assertTrue(resultadoJson.contains("\"fechaCaptura\": \"1998-05-12\""));


        verify(pokemonDaoMock, times(1)).getAll();
    }



}





