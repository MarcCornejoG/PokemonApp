package practica10.repository.impl;

import practica10.exceptions.ExcepcionGestorPokedex;
import practica10.exceptions.PokemonDuplicado;
import practica10.repository.PokemonDao;
import practica10.repository.model.Pokemon;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PokemonJdbcDao implements PokemonDao {

    private static final String INSERT_POKEMON = "INSERT INTO POKEMON (nombre_pokemon,fecha_captura,es_legendario,generacion_pokemon,peso_pokemon,altura_pokemon,descripcion_pokemon) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_POKEMON = "UPDATE pokemon SET nombre_pokemon=?, fecha_captura=?,es_legendario=?,generacion_pokemon=?,peso_pokemon=?,altura_pokemon=?,descripcion_pokemon=? WHERE numero_pokedex = ?";
    private static final String DELETE_BY_ID = "DELETE from pokemon where numero_pokedex = ?";
    private static final String DELETE_ALL = "DELETE from pokemon where numero_pokedex >= 1";
    private static final String SELECT_POKEMON_BY_ID = "Select * from pokemon where numero_pokedex = ?";
    private static final String SELECT_POKEMONS = "Select * from pokemon";

    private static final String MENOR_PESO = "Select * from pokemon WHERE peso_pokemon < ?";
    private static final String MAYOR_PESO = "Select * from pokemon WHERE peso_pokemon > ?";
    private static final String MENOR_ALTURA = "Select * from pokemon WHERE altura_pokemon < ?";
    private static final String MAYOR_ALTURA = "Select * from pokemon WHERE altura_pokemon > ?";
    private static final String ALTURA_PESO_MAYOR = "Select * from pokemon where altura_pokemon > ? AND peso_pokemon > ?";
    private static final String ALTURA_PESO_MENOR = "Select * from pokemon where altura_pokemon < ? AND peso_pokemon < ?";
    private static final String SELECT_POKEMON_BY_LETTER = "Select * from pokemon where nombre_pokemon LIKE ?";
    private static final String COUNT_POKEMON_GENERACION = "Select COUNT(*) from pokemon where generacion_pokemon = ?";

    @Override
    public List<Pokemon> getAll() throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();

        try (Connection conn = DriverHelper.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_POKEMONS)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Pokemon pokemon = mapResultSet(rs);

                    pokemons.add(pokemon);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }

    @Override
    public Pokemon getById(Integer id) throws ExcepcionGestorPokedex {
        Pokemon pokemon = null;
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_POKEMON_BY_ID)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pokemon = mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error al obtener al pokemon por el" + id);
        }
        return pokemon;
    }

    @Override
    public Integer create(Pokemon pokemon) throws ExcepcionGestorPokedex, PokemonDuplicado {
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_POKEMON, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pokemon.getNombre());
            ps.setDate(2, Date.valueOf(pokemon.getFechaCaptura()));
            ps.setBoolean(3, pokemon.isEsLegendario());
            ps.setInt(4, pokemon.getGeneracion());
            ps.setBigDecimal(5, pokemon.getPeso());
            ps.setBigDecimal(6, pokemon.getAltura());
            ps.setString(7, pokemon.getDescripcion());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                throw new ExcepcionGestorPokedex("No se pudo añadir el pokemon.");
            }

            try (ResultSet numeroAIPokedex = ps.getGeneratedKeys()) {
                if (numeroAIPokedex.next()) {
                    int numeroPokedexGenerado = numeroAIPokedex.getInt(1);
                    pokemon.setNumeroPokedex(numeroPokedexGenerado);
                    return numeroPokedexGenerado;
                } else {
                    throw new ExcepcionGestorPokedex("No se pudo recuperar el numero pokedex generado");
                }
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error creando al pokemon");
        }
    }

    @Override
    public Pokemon update(Pokemon pokemon) throws ExcepcionGestorPokedex {

        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_POKEMON)) {
            ps.setString(1, pokemon.getNombre());
            ps.setDate(2, Date.valueOf(pokemon.getFechaCaptura()));
            ps.setBoolean(3, pokemon.isEsLegendario());
            ps.setInt(4, pokemon.getGeneracion());
            ps.setBigDecimal(5, pokemon.getPeso());
            ps.setBigDecimal(6, pokemon.getAltura());
            ps.setString(7, pokemon.getDescripcion());
            ps.setInt(8, pokemon.getNumeroPokedex());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Pokémon con el número " + pokemon.getNumeroPokedex() + " actualizado correctamente.");
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error actualizando el pokémon.");
        }
        return pokemon;
    }


    @Override
    public void delete(Integer id) throws ExcepcionGestorPokedex {
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BY_ID)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Se ha eliminado el pokémon con número de pokédex " + id + " correctamente.");
            } else {
                System.out.println("No se encontró el pokémon con número de pokédex " + id + ".");
            }
        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error eliminando al pokémon.");
        }
    }

    @Override
    public void deleteAll() throws ExcepcionGestorPokedex {
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_ALL)) {

            ps.executeUpdate();

        } catch (Exception e) {
            throw new ExcepcionGestorPokedex("Error al eliminar los pokémons");
        }
    }


    @Override
    public boolean esPokemonLegendario(Integer id) throws ExcepcionGestorPokedex {
        Pokemon pokemon = getById(id);
        return pokemon.isEsLegendario();
    }

    @Override
    public List<Pokemon> obtenerPokemonsConMayorAlturaQue(BigDecimal altura) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(MAYOR_ALTURA)) {
            ps.setBigDecimal(1, altura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con una altura mayor a " + altura);
        }
        return pokemons;
    }

    @Override
    public List<Pokemon> obtenerPokemonsConMenorAlturaQue(BigDecimal altura) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(MENOR_ALTURA)) {
            ps.setBigDecimal(1, altura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con una altura inferior a " + altura);
        }
        return pokemons;
    }

    @Override
    public List<Pokemon> obtenerPokemonsConMayorPesoQue(BigDecimal peso) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(MAYOR_PESO)) {
            ps.setBigDecimal(1, peso);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con un peso mayor a " + peso);
        }
        return pokemons;
    }

    @Override
    public List<Pokemon> obtenerPokemonsConMenorPesoQue(BigDecimal peso) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(MENOR_PESO)) {
            ps.setBigDecimal(1, peso);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con un peso menor a " + peso);
        }
        return pokemons;
    }


    @Override
    public List<Pokemon> obtenerPokemonsConMayorAlturaPeso(BigDecimal altura, BigDecimal peso) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(ALTURA_PESO_MAYOR)) {
            ps.setBigDecimal(1, altura);
            ps.setBigDecimal(2, peso);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con una altura mayor a " + altura + " y un peso mayor a " + peso);
        }
        return pokemons;
    }

    @Override
    public List<Pokemon> obtenerPokemonsConMenorAlturaPeso(BigDecimal altura, BigDecimal peso) throws ExcepcionGestorPokedex {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(ALTURA_PESO_MENOR)) {
            ps.setBigDecimal(1, altura);
            ps.setBigDecimal(2, peso);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons con una altura inferior a " + altura + " y un peso inferior a " + peso);
        }
        return pokemons;
    }

    public List<Pokemon> obtenerPokemonPorLetraInicial(String letraInicial) throws ExcepcionGestorPokedex{
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_POKEMON_BY_LETTER)) {
            ps.setString(1,letraInicial + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pokemon pokemon = mapResultSet(rs);
                pokemons.add(pokemon);
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons que empiezan por la letra " +letraInicial);
        }
        return pokemons;
    }

    public Integer recuentoGeneracionPokemon(int generacion) throws ExcepcionGestorPokedex{
        List<Pokemon> pokemons = new ArrayList<>();
        try (Connection conn = DriverHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_POKEMON_GENERACION)) {
           ps.setInt(1,generacion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
              return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new ExcepcionGestorPokedex("Error recuperando los pokemons de" + generacion + " generacion");
        }
        return 0;
    }

    private Pokemon mapResultSet(ResultSet rs) throws SQLException {
        int numeroPokedex = rs.getInt("numero_pokedex");
        String nombre = rs.getString("nombre_pokemon");
        java.sql.Date fechaCaptura = rs.getDate("fecha_captura");
        boolean esLegendario = rs.getBoolean("es_legendario");
        int generacion = rs.getInt("generacion_pokemon");
        BigDecimal peso = rs.getBigDecimal("peso_pokemon");
        BigDecimal altura = rs.getBigDecimal("altura_pokemon");
        String descripcion = rs.getString("descripcion_pokemon");

        Pokemon pokemon = new Pokemon();
        pokemon.setNumeroPokedex(numeroPokedex);
        pokemon.setNombre(nombre);
        pokemon.setFechaCaptura(fechaCaptura.toLocalDate());
        pokemon.setEsLegendario(esLegendario);
        pokemon.setGeneracion(generacion);
        pokemon.setPeso(peso);
        pokemon.setAltura(altura);
        pokemon.setDescripcion(descripcion);

        return pokemon;

    }
}
