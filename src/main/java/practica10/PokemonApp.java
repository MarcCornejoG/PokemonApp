package practica10;


import practica10.repository.PokemonDao;
import practica10.repository.impl.PokemonJdbcDao;
import practica10.repository.model.Pokemon;
import practica10.service.PokemonService;
import practica10.utils.UtilidadesPokemon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class PokemonApp {
    PokemonService pokemonService;
    PokemonDao pokemonDao;
    private  UtilidadesPokemon utilidadesPokemon = new UtilidadesPokemon();
    public static void main(String[] args) {
        PokemonApp pokemonApp = new PokemonApp();
        pokemonApp.cfgComponentes();
        pokemonApp.run();
    }



    private void cfgComponentes() {
        pokemonDao = new PokemonJdbcDao();
        pokemonService = new PokemonService(pokemonDao);
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        cfgComponentes();
        try {
            menuOpciones(scanner);
        } catch (Exception e) {
            System.out.println("Error al realizar la operativa");
            System.out.println(e);
        } finally {
            scanner.close();
        }
    }

    private void menuOpciones(Scanner scanner) throws Exception {
        int opcion;
        do {
            System.out.println("1. Agregar pokémon");
            System.out.println("2. Consultar pokémons");
            System.out.println("3. Obtener pokémon");
            System.out.println("4. Actualizar pokémon");
            System.out.println("5. Eliminar pokémon");
            System.out.println("6. Borrar todos los pokémons");
            System.out.println("7. Filtrar pokémons con MENOR peso");
            System.out.println("8. Filtrar pokémons con MAYOR peso");
            System.out.println("9. Filtrar pokémons con MENOR altura");
            System.out.println("10. Filtrar pokémons con MAYOR altura");
            System.out.println("11. Filtrar pokémons con MENOR peso y MENOR altura");
            System.out.println("12. Filtrar pokémons con MAYOR peso y MAYOR altura");
            System.out.println("13. Obtener pokémons mediante letra inicial");
            System.out.println("14. Obtener recuento de generaciones capturadas");
            System.out.println("15. Obtener JSON");
            System.out.println("99. Salir del programa");
            System.out.print("Introduzca una opción: ");
            opcion = scanner.nextInt();

            scanner.nextLine();
            Pokemon pokemon = null;
            Integer id = 0;
            List<Pokemon> pokemonList = new ArrayList<>();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese los datos del nuevo pokemon");
                    pokemon = getDatosPokemon(scanner);
                    pokemonService.create(pokemon);
                    break;
                case 2:
                    System.out.println("Pokemons registrados en la pokedex:");
                    List<Pokemon> pokemons = pokemonService.getAll();
                    recorrerLista(pokemons);
                    break;
                case 3:
                    System.out.println("Ingresa el número de Pokedex para para obtener al pokémon:");
                    id = utilidadesPokemon.obtenerNumeroValido(scanner);

                    Pokemon pokemonExisteParaObtener = pokemonService.getById(id);
                    if (pokemonExisteParaObtener != null) {
                        System.out.println(pokemonService.getById(id));
                    }else{
                        System.out.println("Ingresa un pokémon válido");
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el numero de la pokedex del pokemon cuya información quieres actualizar:");
                    id = utilidadesPokemon.obtenerNumeroValido(scanner);

                    Pokemon pokemonExiste = pokemonService.getById(id);
                    if (pokemonExiste != null) {
                        System.out.println("Pokemon encontrado: " + pokemonExiste);
                        System.out.println("Ingrese los nuevos datos del pokemon:");

                        Pokemon nuevoPokemon = updateDatosPokemon(scanner, id);
                        pokemonService.update(nuevoPokemon);
                    } else {
                        System.out.println("Ingresa un pokémon válido");
                    }
                    break;
                case 5:
                    System.out.println("Ingrese el numero de pokedex del pokemon que desea eliminar:");
                    id = utilidadesPokemon.obtenerNumeroValido(scanner);
                    Pokemon pokemonExisteParaEliminar = pokemonService.getById(id);
                    if (pokemonExisteParaEliminar != null) {
                        System.out.println(pokemonService.getById(id));
                    }else{
                        System.out.println("Ingresa un pokémon válido");
                    }
                    pokemonService.delete(id);
                    break;

                case 6:
                    pokemonService.deleteAll();
                    System.out.println("Pokemons eliminados");
                    break;

                case 7:
                    BigDecimal pesoMenor = utilidadesPokemon.leerBigDecimal("Escriba el peso para filtrar los pokemons:");
                    pokemonList = pokemonService.obtenerPokemonsConMenorPesoQue(pesoMenor);

                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con peso menor a " + pesoMenor);
                    } else {
                        recorrerLista(pokemonList);
                    }

                    break;

                case 8:
                    BigDecimal pesoMayor = utilidadesPokemon.leerBigDecimal("Escriba el peso para filtrar los pokemons:");
                    pokemonList = pokemonService.obtenerPokemonsConMayorPesoQue(pesoMayor);
                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con peso mayor a " + pesoMayor);
                    } else {
                        recorrerLista(pokemonList);
                    }
                        break;

                case 9:
                    BigDecimal alturaMenor = utilidadesPokemon.leerBigDecimal("Escriba la altura para filtrar los pokemons:");
                    pokemonList = pokemonService.obtenerPokemonsConMenorAlturaQue(alturaMenor);
                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con altura menor a " + alturaMenor);
                    } else {
                        recorrerLista(pokemonList);
                    }
                    break;

                case 10:
                     BigDecimal alturaMayor =  utilidadesPokemon.leerBigDecimal("Escriba la altura para filtrar los pokemons:");
                    pokemonList = pokemonService.obtenerPokemonsConMayorAlturaQue(alturaMayor);
                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con altura mayor a " + alturaMayor);
                    } else {
                        recorrerLista(pokemonList);
                    }
                    break;

                case 11:
                    BigDecimal alturaBuscadoMenor = utilidadesPokemon.leerBigDecimal("Escriba el peso para filtrar los pokemons:");
                    BigDecimal pesoBuscadoMenor = utilidadesPokemon.leerBigDecimal("Escriba la altura para filtrar los pokemons:");

                    pokemonList = pokemonService.obtenerPokemonsConMenorAlturaPeso(pesoBuscadoMenor,alturaBuscadoMenor);
                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con peso menor a " + pesoBuscadoMenor + " y altura menor a " + alturaBuscadoMenor);
                    } else {
                        recorrerLista(pokemonList);
                    }
                    break;

                case 12:
                    BigDecimal alturaBuscadoMayor = utilidadesPokemon.leerBigDecimal("Escriba el peso para filtrar los pokemons:");
                    BigDecimal pesoBuscadoMayor = utilidadesPokemon.leerBigDecimal("Escriba la altura para filtrar los pokemons:");

                    pokemonList = pokemonService.obtenerPokemonsConMayorAlturaPeso(pesoBuscadoMayor,alturaBuscadoMayor);
                    if (pokemonList.isEmpty()) {
                        System.out.println("No se encontraron pokemon con peso mayor a " + pesoBuscadoMayor + " y altura mayor a " + alturaBuscadoMayor);
                    } else {
                        recorrerLista(pokemonList);
                    }
                    break;
                case 13:
                    System.out.print("Escribe la letra por la que quieres buscar al Pokémon:");
                    String letraInicial = scanner.nextLine();

                    pokemonList = pokemonService.obtenerPokemonPorLetraInicial(letraInicial);

                    if (pokemonList.isEmpty()){
                        System.out.println("No se encontraron Pokémon con la letra inicial " + letraInicial);
                    }else{
                        recorrerLista(pokemonList);
                    }
                    break;

                case 14:
                    int generacionBuscada;

                    do{
                        System.out.print("Escribe saber cuantos pokémons tienes de la generacion buscada :");
                        generacionBuscada = scanner.nextInt();
                        scanner.nextLine();

                        if(generacionBuscada<1 || generacionBuscada>9){
                            System.out.println("Generacion no valida");
                        }

                    }while(generacionBuscada<1 || generacionBuscada>9);

                    int recuentoGeneracion = pokemonService.recuentoGeneracionPokemon(generacionBuscada);
                    System.out.println("De la generacion " + generacionBuscada + " hay un total de:" + recuentoGeneracion + " pokémon");

                    break;

                case 15:
                    getJson();
                    break;

                        case 99:
                            System.out.println("Saliendo del programa...");
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    }
            }
            while (opcion != 99) ;
        }

    private void recorrerLista(List<Pokemon> pokemonList) {
        for (Pokemon p : pokemonList) {
            System.out.println(p);
        }
    }

    private Pokemon getDatosPokemon(Scanner scanner) {

        System.out.print("Ingrese el nombre del pokémon:");
        String nombrePokemon = scanner.nextLine();
        while(!utilidadesPokemon.validarNombre(nombrePokemon)){
            System.out.println("Nombre no valido. Introduzca un nombre valido");
            nombrePokemon = scanner.nextLine();
        }
        System.out.print("Ingrese la fecha de captura del pokémon (yyyy-MM-dd):");
        String fechaCaptura = scanner.nextLine();
        while(!utilidadesPokemon.validarFecha(fechaCaptura)){
            System.out.println("Fecha con el formato incorrecto. Introduzca una fecha valida");
            fechaCaptura = scanner.nextLine();
        }
        System.out.print("Es legendario? (true/false)");
        String esLegendario = scanner.nextLine();
        while (!utilidadesPokemon.validarEsLegendario(esLegendario)){
            System.out.println("Respuesta incorrecta. Introduzca una respuesta correcta");
            esLegendario = scanner.nextLine();
        }
        System.out.print("A que generación pertenece (1/2/3/4/5/6/7/8/9):");
        String generacion = scanner.nextLine();
        while(!utilidadesPokemon.validarGeneracion(generacion)){
            System.out.println("Generacion incorrecta. Introduzca una generacion correcta");
            generacion = scanner.nextLine();
        }
        BigDecimal peso = utilidadesPokemon.leerBigDecimal("Ingrese el peso del Pokémon:");
        BigDecimal altura = utilidadesPokemon.leerBigDecimal("Ingrese la altura del Pokémon:");

        System.out.println("Ingrese la descripcion del pokémon:");
        String descripcionPokemon = scanner.nextLine();

        Pokemon pokemon = new Pokemon(nombrePokemon, utilidadesPokemon.convertirStringALocalDate(fechaCaptura),
                Boolean.parseBoolean(esLegendario),Integer.parseInt(generacion),peso,altura ,descripcionPokemon);
        return pokemon;
    }

    private Pokemon updateDatosPokemon(Scanner scanner, int numeroPokedex) {
        System.out.print("Ingrese el nombre del pokémon:");
        String nombrePokemon = scanner.nextLine();
        while(!utilidadesPokemon.validarNombre(nombrePokemon)){
            System.out.println("Nombre no valido. Introduzca un nombre valido");
            nombrePokemon = scanner.nextLine();
        }
        System.out.print("Ingrese la fecha de captura del pokémon (yyyy-MM-dd):");
        String fechaCaptura = scanner.nextLine();
        while(!utilidadesPokemon.validarFecha(fechaCaptura)){
            System.out.println("Fecha con el formato incorrecto. Introduzca una fecha valida");
            fechaCaptura = scanner.nextLine();
        }
        System.out.print("Es legendario? (true/false)");
        String esLegendario = scanner.nextLine();
        while (!utilidadesPokemon.validarEsLegendario(esLegendario)){
            System.out.println("Respuesta incorrecta. Introduzca una respuesta correcta");
            esLegendario = scanner.nextLine();
        }
        System.out.print("A que generación pertenece (1/2/3/4...:");
        String generacion = scanner.nextLine();
        while(!utilidadesPokemon.validarGeneracion(generacion)){
            System.out.println("Generacion incorrecta. Introduzca una generacion correcta");
            generacion = scanner.nextLine();
        }
        System.out.print("Ingrese el peso del pokémon:");
        BigDecimal peso = scanner.nextBigDecimal();
        scanner.nextLine();
        while(!utilidadesPokemon.validarPeso(peso)){
            System.out.println("Peso no valido. Introduzca un peso valido");
            peso = scanner.nextBigDecimal();
            scanner.nextLine();
        }
        System.out.print("Ingrese la altura del pokémon:");
        BigDecimal altura = scanner.nextBigDecimal();
        scanner.nextLine();
        while(!utilidadesPokemon.validarAltura(altura)){
            System.out.println("Altura no valida. Introduzca una altura valida");
            altura = scanner.nextBigDecimal();
            scanner.nextLine();
        }
        System.out.println("Ingrese la descripcion del pokémon:");
        String descripcionPokemon = scanner.nextLine();

        Pokemon pokemon = new Pokemon(numeroPokedex,nombrePokemon, utilidadesPokemon.convertirStringALocalDate(fechaCaptura),
                Boolean.parseBoolean(esLegendario),Integer.parseInt(generacion),peso,altura,descripcionPokemon);
        return pokemon;
    }

    private void getJson(){
        try{
            String json = pokemonService.getPokemonDtoAsJson();
            System.out.println("JSON");
            System.out.println(json);
            System.out.println();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
